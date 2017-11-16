package com.pay.aile.bill.service.mail.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pay.aile.bill.service.mail.analyze.banktemplate.BaseBankTemplate;
import com.pay.aile.bill.service.mail.analyze.constant.Constant;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.exception.AnalyzeBillException;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.JedisClusterUtils;
import com.pay.aile.bill.utils.SpringContextUtil;

/**
 *
 * @author Charlie
 * @description 统一处理每个银行分析器共同的业务
 * @param <T>
 *            每个银行卡种的父类
 */
public abstract class AbstractBankMailAnalyzer<T extends BaseBankTemplate> implements BankMailAnalyzer {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 当前银行卡种解析模板缓存 key:区分不同卡的标识 value:对应的模板
     */
    protected Map<CardTypeEnum, Class<? extends T>> templateCache;
    /**
     * 当前银行包含的卡种解析模板
     */
    @Autowired
    protected List<T> templates;

    /**
     * 子类不要重写此方法
     */
    @Override
    public void analyze(AnalyzeParamsModel apm) throws AnalyzeBillException {
        long startTime = System.currentTimeMillis();
        String email = apm.getEmail();
        String bankCode = apm.getBankCode();
        T template = null;
        // 根据用户邮箱和银行编码从redis中获取缓存的对应的模板
        template = getTemplateFromCache(email, bankCode);
        if (template != null) {
            try {
                template.analyze(apm);
            } catch (Exception e) {
                logger.error("默认模板执行错误!" + e.getMessage(), e);

            }
        }
        if (!apm.success()) {
            // 没有确定的模板或者缓存模板执行没有成功
            List<Exception> analyzeErrors = new ArrayList<Exception>();
            Collections.sort(templates);
            for (int i = 0; i < templates.size(); i++) {
                if (template == templates.get(i)) {
                    break;
                }
                template = templates.get(i);
                try {
                    template.analyze(apm);
                } catch (Exception e) {
                    analyzeErrors.add(e);
                }
                if (apm.success()) {
                    // 执行成功,设置默认模板，结束解析
                    setTemplateToCache(email, bankCode, template);
                    break;
                }
            }
            if (!apm.success()) {
                analyzeErrors.forEach(ee -> {
                    logger.error("解析错误,bankCode={},email={},msg={}", apm.getBankCode(), apm.getEmail(), ee.getMessage(),
                            ee);
                });
                throw new AnalyzeBillException(
                        String.format("解析错误,bankCode=%s,email=%s", apm.getBankCode(), apm.getEmail()));
            }
        }
        if (apm.success()) {
            // 解析成功，处理解析结果
            template.handleResult(apm);
        }
        logger.debug("analyze end,useTime={}", System.currentTimeMillis() - startTime);
    }

    /**
     *
     * @return 根据用户邮箱和银行编码从缓存中获取对应的模板
     */
    private T getTemplateFromCache(String email, String bankCode) {
        Object o = JedisClusterUtils.hashGet(Constant.redisTemplateCache + bankCode, email);
        if (o == null) {
            return null;
        } else {
            String className = o.toString();
            Class<T> t = null;
            try {
                t = (Class<T>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.error("get class error!" + e.getMessage(), e);
            }
            if (t != null) {
                return SpringContextUtil.getBean(t);
            } else {
                return null;
            }
        }
    }

    /**
     *
     * @param email
     * @param bankCode
     * @param template
     *            将模板存入缓存
     */
    private void setTemplateToCache(String email, String bankCode, T template) {
        JedisClusterUtils.hashSet(Constant.redisTemplateCache + bankCode, email, template.getClass().getName());
    }

}

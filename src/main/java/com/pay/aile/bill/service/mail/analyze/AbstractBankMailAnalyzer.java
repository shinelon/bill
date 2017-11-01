package com.pay.aile.bill.service.mail.analyze;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.pay.aile.bill.service.mail.analyze.banktemplate.BaseBankTemplate;
import com.pay.aile.bill.utils.SpringContextUtil;

/**
 * 
 * @author Charlie
 * @description 统一处理每个银行分析器共同的业务
 * @param <T> 每个银行卡种的父类
 */
public abstract class AbstractBankMailAnalyzer<T extends BaseBankTemplate>
        implements BankMailAnalyzer, InitializingBean {

    /**
     * 当前银行包含的卡种解析模板
     */
    @Autowired
    protected List<T> templates;

    /**
     * 卡种
     */
    protected String cardType;
    /**
     * 当前银行卡种解析模板缓存
     * key:区分不同卡的标识
     * value:对应的模板
     */
    protected Map<String, Class<? extends T>> templateCache;

    /**
     * 子类不要重写此方法
     */
    @Override
    public void analyze(String content) {
        preAnalyze(content);
        String cardType = "";
        T template = null;
        if (templateCache != null && !templateCache.isEmpty()) {
            Class<? extends T> clazz = templateCache.get(cardType);
            //从applicationContext中获取对应的template
            template = SpringContextUtil.getBean(clazz);
        }
        if (template != null) {
            template.analyze(content);
        } else {
            Exception analyzeError = null;
            Collections.sort(templates);
            for (int i = 0; i < templates.size(); i++) {
                template = templates.get(i);
                try {
                    analyzeError = null;
                    template.analyze(content);
                } catch (Exception e) {
                    analyzeError = e;
                }
                if (analyzeError == null) {
                    //执行成功,返回
                    break;
                } else {
                    analyzeError.printStackTrace();
                }
            }
        }

    }

    /**
     * 解析之前，由子类实现进行特殊处理 
     * 比如可以从content中找出能判断卡种的标识
     * @param content
     */
    protected void preAnalyze(String content) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initTemplateCache();
    }

    /**
     * 子类通过重写此方法初始化银行不同卡种的处理模板
     */
    protected void initTemplateCache() {
    }
}

package com.pay.aile.bill.service.mail.analyze;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.pay.aile.bill.service.mail.analyze.banktemplate.BaseBankTemplate;
import com.pay.aile.bill.utils.SpringContextUtil;

public abstract class AbstractBankMailAnalyzer<T extends BaseBankTemplate>
        implements BankMailAnalyzer, InitializingBean {

    /**
     * 
     */
    @Autowired
    protected List<T> templates;

    /**
     * 卡种
     */
    protected String cardType;
    /**
     * 同银行不同卡的模板缓存
     * key:区分不同卡的关键字
     * value:对应的模板
     */
    protected Map<String, Class<? extends T>> templateCache;

    /**
     * 子类不要重写此方法
     */
    @Override
    public void analyze(List<String> content) {
        preAnalyze(content);
        String cardType = "";
        T template = null;
        if (!templateCache.isEmpty()) {
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
                }
            }
        }

    }

    /**
     * 解析之前，由子类实现进行特殊处理 
     * 比如可以从content中找出能判断卡种的关键字
     * @param content
     */
    protected void preAnalyze(List<String> content) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initTemplateCache();
    }

    protected void initTemplateCache() {
    }
}

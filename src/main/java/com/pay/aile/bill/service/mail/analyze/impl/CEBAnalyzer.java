package com.pay.aile.bill.service.mail.analyze.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.AbstractCEBTemplate;
import com.pay.aile.bill.service.mail.analyze.banktemplate.CEBTemplate;
import com.pay.aile.bill.utils.SpringContextUtil;

/**
 * 
 * @author Charlie
 * @description 
 */
@Service
public class CEBAnalyzer implements BankMailAnalyzer {

    @Autowired
    private List<AbstractCEBTemplate> cmbTemplates;

    /**
     * 同银行不同卡的模板缓存
     * key:区分不同卡的关键字
     * value:对应的模板
     */
    private Map<String, Class<? extends AbstractCEBTemplate>> templateCache;

    public CEBAnalyzer() {
        templateCache = new HashMap<String, Class<? extends AbstractCEBTemplate>>();
        templateCache.put("金卡", CEBTemplate.class);
    }

    @Override
    public boolean support(String name) {
        return true;
    }

    @Override
    public void analyze(List<String> content) {
        //TODO 从content中找出能判断卡种的关键字
        String cardType = "";
        AbstractCEBTemplate template = null;
        if (!templateCache.isEmpty()) {
            Class<? extends AbstractCEBTemplate> clazz = templateCache
                    .get(cardType);
            //从applicationContext中获取对应的template
            template = SpringContextUtil.getBean(clazz);
        }
        if (template != null) {
            template.analyze(content);

        } else {
            Exception analyzeError = null;
            Collections.sort(cmbTemplates);
            for (int i = 0; i < cmbTemplates.size(); i++) {
                template = cmbTemplates.get(i);
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

}
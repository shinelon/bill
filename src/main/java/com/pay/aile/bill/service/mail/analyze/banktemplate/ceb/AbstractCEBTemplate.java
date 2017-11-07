package com.pay.aile.bill.service.mail.analyze.banktemplate.ceb;

import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.banktemplate.BaseBankTemplate;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 * 
 * @author Charlie
 * @description 光大银行抽象解析模板
 */
public abstract class AbstractCEBTemplate extends BaseBankTemplate {

    @Override
    protected void beforeAnalyze(AnalyzeParamsModel apm) {
        String content = apm.getContent();
        if (StringUtils.hasText(content)) {
            content = content.replaceAll("([\\u4e00-\\u9fa5]+) +(\\()", "$1$2");//去掉中文与(之间空格
            content = content.replaceAll("(\\(\\S+) +(\\S*\\))", "$1$2");//去掉()中的空格
            content = content.replaceAll("(存入)", " ");
        }
    }

}

package com.pay.aile.bill.service.mail.analyze.banktemplate.icbc;

import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.banktemplate.BaseBankTemplate;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 *
 * @author ji
 * @description 中国工商银行
 *
 */
public class AbstractICBCTemplate extends BaseBankTemplate {
    @Override
    protected void beforeAnalyze(AnalyzeParamsModel apm) {
        String content = apm.getContent();
        if (StringUtils.hasText(content)) {
            content = content.replaceAll("\\(存入\\)|\\(支出\\)", "");
            apm.setContent(content);
        }
    }
}

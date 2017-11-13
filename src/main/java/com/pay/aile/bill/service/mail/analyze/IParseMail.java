package com.pay.aile.bill.service.mail.analyze;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.service.mail.analyze.model.CreditFileModel;

/**
 *
 * @author Charlie
 * @description 账单解析任务的入口
 */
public interface IParseMail {

    public void execute();

    public void execute(CreditEmail creditEmail);

    /**
     *
     * @Title: executeParseFile
     * @Description:解析单个文件
     * @param creditFile
     * @return void 返回类型 @throws
     */
    public void executeParseFile(CreditFileModel creditFile);
}

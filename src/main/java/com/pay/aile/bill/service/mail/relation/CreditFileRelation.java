package com.pay.aile.bill.service.mail.relation;

import java.util.List;

import com.pay.aile.bill.entity.CreditFile;

/***
 * FileRelation.java
 *
 * @author shinelon
 *
 * @date 2017年11月3日
 *
 */
public interface CreditFileRelation {
    /***
     * 保存邮件文件关系
     *
     * @param creditFile
     */
    void saveCreditFile(CreditFile creditFile);

    /***
     * 查询邮件关系
     *
     * @param emailAddr
     * @return
     */
    List<CreditFile> selectCreditFiles(String emailAddr);

    /***
     * 更新邮件关系
     * 
     * @param creditFile
     * @return
     */
    Integer updateCreditFile(CreditFile creditFile);
}

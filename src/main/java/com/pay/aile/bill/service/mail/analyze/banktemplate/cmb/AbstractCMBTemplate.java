package com.pay.aile.bill.service.mail.analyze.banktemplate.cmb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.service.mail.analyze.banktemplate.BaseBankTemplate;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 * 
 * @author Charlie
 * @description 招商银行解析模板
 */
public abstract class AbstractCMBTemplate extends BaseBankTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    protected void handleResultInternal(AnalyzeParamsModel apm) {

        // 解析成功，保存账单到数据库
        CreditBill bill = apm.getResult().getBill();
        List<CreditBillDetail> billDetails = apm.getResult().getDetail();
        Long billId = null;
        if (bill != null) {
            bill.setEmailId(apm.getEmailId());
            bill.setCardtypeId(apm.getCardtypeId());
            bill.setSentDate(apm.getSentDate());
            bill.setBankCode(apm.getBankCode());
            billId = creditBillService.saveOrUpdateCreditBill(bill);
            billId = bill.getId();
        }

        if (billDetails != null && !billDetails.isEmpty()) {
            for (CreditBillDetail creditBillDetail : billDetails) {
                try {
                    if (billId == null) {
                        bill = new CreditBill();
                        bill.setSentDate(creditBillDetail.getTransactionDate());
                        bill.setEmailId(apm.getEmailId());
                        bill.setBankCode(apm.getBankCode());
                        CreditBill saveBill = creditBillService
                                .findCreditBillByTransDate(bill);
                        if (saveBill != null) {
                            billId = saveBill.getId();
                        }else{
                            
                        }
                    }
                    creditBillDetail.setBillId(billId);
                    creditBillDetailService
                            .saveCreditBillDetail(creditBillDetail);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
    
}

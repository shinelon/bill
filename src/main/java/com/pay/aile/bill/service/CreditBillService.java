package com.pay.aile.bill.service;

import com.pay.aile.bill.entity.CreditBill;

/**
 * 
 * @author Charlie
 * @description
 */
public interface CreditBillService {

    public Long saveOrUpdateCreditBill(CreditBill bill);

    public CreditBill findCreditBillByTransDate(CreditBill bill);
}

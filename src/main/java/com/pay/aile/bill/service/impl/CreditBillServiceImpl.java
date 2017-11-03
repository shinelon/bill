package com.pay.aile.bill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.mapper.CreditBillMapper;
import com.pay.aile.bill.service.CreditBillService;

/**
 * 
 * @author Charlie
 * @description
 */
@Service
public class CreditBillServiceImpl implements CreditBillService {

    @Autowired
    private CreditBillMapper creditBillMapper;

    @Transactional
    @Override
    public Long saveCreditBill(CreditBill bill) {
        return creditBillMapper.insertCreditBill(bill);
    }

}

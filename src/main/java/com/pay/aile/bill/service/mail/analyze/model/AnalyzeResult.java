package com.pay.aile.bill.service.mail.analyze.model;

import java.util.ArrayList;
import java.util.List;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;

/**
 * 
 * @author Charlie
 * @description 存储解析结果
 */
public class AnalyzeResult {

    private CreditBill bill;
    private List<CreditBillDetail> detail;

    public AnalyzeResult() {
        bill = new CreditBill();
        detail = new ArrayList<CreditBillDetail>();
    }

    public CreditBill getBill() {
        return bill;
    }

    public void setBill(CreditBill bill) {
        this.bill = bill;
    }

    public List<CreditBillDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<CreditBillDetail> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "AnalyzeResult [bill=" + bill + ", detail=" + detail + "]";
    }

}

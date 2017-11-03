package com.pay.aile.bill.service;

import java.util.List;

import com.pay.aile.bill.entity.CreditFile;

/**
 * 
 * @author Charlie
 * @description
 */
public interface CreditFileService {

    List<CreditFile> findUnAnalyzedList();
}

package com.pay.aile.bill.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Charlie
 * @description
 */
public interface CreditFileService {

    List<Map<String, Object>> findUnAnalyzedList();
}

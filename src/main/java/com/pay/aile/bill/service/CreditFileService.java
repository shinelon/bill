package com.pay.aile.bill.service;

import java.util.List;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.service.mail.analyze.model.CreditFileModel;

/**
 *
 * @author Charlie
 * @description
 */
public interface CreditFileService {

	List<CreditFileModel> findUnAnalyzedList();

	List<CreditFileModel> findUnAnalyzedListByEmail(CreditEmail eamil);

	Integer updateProcessResult(int result, Long id);
}

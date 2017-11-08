package com.pay.aile.bill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.mapper.CreditFileMapper;
import com.pay.aile.bill.service.CreditFileService;
import com.pay.aile.bill.service.mail.analyze.model.CreditFileModel;

/**
 *
 * @author Charlie
 * @description
 */
@Service
public class CreditFileServiceImpl implements CreditFileService {

	@Autowired
	private CreditFileMapper creditFileMapper;

	@Override
	public List<CreditFileModel> findUnAnalyzedList() {
		return creditFileMapper.selectUnAnalyzedList();
	}

	@Override
	public List<CreditFileModel> findUnAnalyzedListByEmail(CreditEmail eamil) {
		return creditFileMapper.selectUnAnalyzedListByEmail(eamil.getEmail());
	}

	@Transactional
	@Override
	public Integer updateProcessResult(int result, Long id) {
		return creditFileMapper.updateProcessResult(result, id);
	}

}

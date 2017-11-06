package com.pay.aile.bill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditCardtype;
import com.pay.aile.bill.mapper.CreditCardtypeMapper;
import com.pay.aile.bill.service.CreditCardtypeService;

@Service
public class CreditCardTypeServiceImpl implements CreditCardtypeService {
	@Autowired
	private CreditCardtypeMapper creditCardtypeMapper;

	@Override
	public CreditCardtype saveOrUpdate(CreditCardtype creditCardtype) {
		if (creditCardtype.getId() != null) {
			creditCardtypeMapper.updateById(creditCardtype);
		} else {
			creditCardtypeMapper.insert(creditCardtype);
		}
		return creditCardtype;
	}
}

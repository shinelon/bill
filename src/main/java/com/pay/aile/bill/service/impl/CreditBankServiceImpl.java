package com.pay.aile.bill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.pay.aile.bill.entity.CreditBank;
import com.pay.aile.bill.mapper.CreditBankMapper;
import com.pay.aile.bill.service.CreditBankService;

@Service
public class CreditBankServiceImpl implements CreditBankService {
	@Autowired
	private CreditBankMapper creditBankMapper;

	public List<CreditBank> getAllList(CreditBank bank) {
		Wrapper wapper = new EntityWrapper(bank);
		return creditBankMapper.selectList(wapper);
	}

}

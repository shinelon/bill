package com.pay.aile.bill.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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

	/**
	 * 根据给定的交易日期查询此次交易所属的账单 交易日期 < 账单的的接收日期 <= (交易日期+一个月)
	 */
	@Override
	public CreditBill findCreditBillByTransDate(CreditBill bill) {
		Wrapper<CreditBill> wrapper = Condition.create();
		Date transDate = bill.getSentDate();
		Calendar d = Calendar.getInstance();
		d.setTime(transDate);
		d.add(Calendar.MONTH, 1);
		Date nextMonth = d.getTime();
		wrapper.addFilter(" receive_date > {0} and receive_date <= {1}", transDate, nextMonth);
		wrapper.addFilter(" email_id={0}", bill.getEmailId());
		wrapper.addFilter(" bank_code={0}", bill.getBankCode());
		wrapper.orderBy("receive_date", true);
		List<CreditBill> list = creditBillMapper.selectList(wrapper);
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<CreditBill> getBillList(CreditBill bill) {
		Wrapper<CreditBill> wrapper = new EntityWrapper<CreditBill>();
		return creditBillMapper.selectList(wrapper);
	}

	@Transactional
	@Override
	public Long saveCreditBill(CreditBill bill) {
		return creditBillMapper.insertCreditBill(bill);
	}

	@Transactional
	@Override
	public Long saveOrUpdateCreditBill(CreditBill bill) {
		// CreditBill billParam = new CreditBill();
		// billParam.setId(bill.getId());
		// billParam.setDueDate(bill.getDueDate());
		// billParam.setEmailId(bill.getEmailId());
		// billParam = creditBillMapper.selectOne(billParam);
		if (bill.getId() == null) {
			creditBillMapper.insertCreditBill(bill);
		} else {

			creditBillMapper.updateById(bill);

		}
		return bill.getId();
	}

}

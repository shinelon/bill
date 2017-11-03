package com.pay.aile.bill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditFile;
import com.pay.aile.bill.mapper.CreditFileMapper;
import com.pay.aile.bill.service.CreditFileService;

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
    public List<CreditFile> findUnAnalyzedList() {
        return creditFileMapper.selectUnAnalyzedList();
    }

}

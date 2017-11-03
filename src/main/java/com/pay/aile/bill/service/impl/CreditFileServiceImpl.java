package com.pay.aile.bill.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Map<String, Object>> findUnAnalyzedList() {
        return creditFileMapper.selectUnAnalyzedList();
    }

    @Transactional
    @Override
    public Integer updateProcessResult(int result, Long id) {
        return creditFileMapper.updateProcessResult(result, id);
    }

}

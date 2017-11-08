package com.pay.aile.bill.service.mail.relation.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.pay.aile.bill.entity.CreditFile;
import com.pay.aile.bill.enums.CommonStatus;
import com.pay.aile.bill.mapper.CreditFileMapper;
import com.pay.aile.bill.service.mail.relation.CreditFileRelation;

/***
 * FileRelationImpl.java
 *
 * @author shinelon
 *
 * @date 2017年11月3日
 *
 */
@Service
public class CreditFileRelationImpl implements CreditFileRelation {
    private static final Logger logger = LoggerFactory.getLogger(CreditFileRelationImpl.class);

    @Autowired
    private CreditFileMapper creditFileMapper;

    @Override
    public void saveCreditFile(CreditFile creditFile) {
        List<CreditFile> list = creditFileMapper
                .selectList(new EntityWrapper<CreditFile>().eq("file_name", creditFile.getFileName()));
        if (list.size() < 1) {
            creditFileMapper.insert(creditFile);
        }

    }

    @Override
    public void saveNotExitsCreditFile(List<CreditFile> creditFileList) {
        List<String> fileNames = creditFileList.stream().map(e -> e.getFileName()).collect(Collectors.toList());
        List<CreditFile> exitslist = creditFileMapper
                .selectList(new EntityWrapper<CreditFile>().in("file_name", fileNames));
        List<String> exitsfileNames = exitslist.stream().map(e -> e.getFileName()).collect(Collectors.toList());
        List<CreditFile> insertList = creditFileList.stream().filter(e -> !exitsfileNames.contains(e.getFileName()))
                .collect(Collectors.toList());
        creditFileMapper.batchInsert(insertList);
    }

    @Override
    public List<CreditFile> selectCreditFiles(String emailAddr) {
        String sqlWhere = "email_id = (SELECT id FROM credit_email WHERE STATUS = {0} AND email = {1})";
        return creditFileMapper
                .selectList(new EntityWrapper<CreditFile>().where(sqlWhere, CommonStatus.AVAILABLE.value, emailAddr));
    }

    @Override
    public Integer updateCreditFile(CreditFile creditFile) {
        return creditFileMapper.updateById(creditFile);
    }
}

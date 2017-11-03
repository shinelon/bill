package com.pay.aile.bill.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pay.aile.bill.entity.CreditFile;

/**
 * <p>
  * 邮箱文件 Mapper 接口
 * </p>
 *
 * @author yaoqiang.sun
 * @since 2017-11-02
 */
public interface CreditFileMapper extends BaseMapper<CreditFile> {
    List<Map<String, Object>> selectUnAnalyzedList();

    Integer updateProcessResult(@Param("result") int result,
            @Param("id") Long id);
}
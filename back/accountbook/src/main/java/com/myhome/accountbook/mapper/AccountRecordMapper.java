package com.myhome.accountbook.mapper;

import com.myhome.accountbook.entity.AccountRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 * 账单 Mapper 接口
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
public interface AccountRecordMapper extends BaseMapper<AccountRecord> {
    void updateEventTime(@Param("time") Date time, @Param("id") String id);
}

package com.myhome.accountbook.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myhome.accountbook.entity.AccountRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myhome.accountbook.entity.vo.AccountRecordVo;
import com.myhome.accountbook.entity.vo.RecordQueryVo;

import java.util.Date;

/**
 * <p>
 * 账单 服务类
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
public interface AccountRecordService extends IService<AccountRecord> {

    AccountRecordVo[] recordQuery(Page<AccountRecord> page, RecordQueryVo queryVo);

    void updateEventTime(String id, Date time);
}

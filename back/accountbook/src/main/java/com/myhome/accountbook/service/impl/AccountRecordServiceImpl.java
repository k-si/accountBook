package com.myhome.accountbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myhome.accountbook.entity.AccountRecord;
import com.myhome.accountbook.entity.vo.AccountRecordVo;
import com.myhome.accountbook.entity.vo.RecordQueryVo;
import com.myhome.accountbook.mapper.AccountRecordMapper;
import com.myhome.accountbook.service.AccountRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myhome.accountbook.service.AccountUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 账单 服务实现类
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
@Service
public class AccountRecordServiceImpl extends ServiceImpl<AccountRecordMapper, AccountRecord> implements AccountRecordService {

    @Override
    public AccountRecordVo[] recordQuery(Page<AccountRecord> page, RecordQueryVo queryVo) {
        QueryWrapper<AccountRecord> wrapper = new QueryWrapper<>();

        String userId = queryVo.getUserId();
        String labelId = queryVo.getLabelId();

        BigDecimal low = queryVo.getLow();
        BigDecimal high = queryVo.getHigh();
        String begin = queryVo.getBegin();
        String end = queryVo.getEnd();

        if (!StringUtils.isEmpty(userId)) wrapper.eq("user_id", userId);
        if (!StringUtils.isEmpty(labelId)) wrapper.eq("label_id", labelId);
        if (!StringUtils.isEmpty(low)) wrapper.ge("change", low);
        if (!StringUtils.isEmpty(high)) wrapper.le("change", high);
        if (!StringUtils.isEmpty(begin)) wrapper.ge("gmt_create", begin);
        if (!StringUtils.isEmpty(end)) wrapper.le("gmt_create", end);

        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(page, wrapper);

        // 把po对象转为vo对象
        List<AccountRecord> records = page.getRecords();
        int len = records.size();
        AccountRecordVo[] res = new AccountRecordVo[len];
        for (int i = 0; i < len; ++i) {
            AccountRecordVo vo = new AccountRecordVo();
            AccountRecord record = records.get(i);
            vo.setId(record.getId());
            vo.setEvent(record.getEvent());
            vo.setLabel(record.getLabel());
            vo.setChange(record.getChange());
            vo.setGmtCreate(record.getGmtCreate());
            vo.setStatus(record.getStatus());
            res[i] = vo;
        }

        return res;
    }

    @Override
    public void updateEventTime(String id, Date time) {
        baseMapper.updateEventTime(time, id);
    }
}

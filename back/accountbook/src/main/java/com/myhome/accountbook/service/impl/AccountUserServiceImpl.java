package com.myhome.accountbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myhome.accountbook.common.RecordStatusCode;
import com.myhome.accountbook.entity.AccountUser;
import com.myhome.accountbook.entity.vo.UserQueryVo;
import com.myhome.accountbook.mapper.AccountUserMapper;
import com.myhome.accountbook.service.AccountUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.scene.control.TextFormatter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * <p>
 * 账单使用人 服务实现类
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
@Service
public class AccountUserServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser> implements AccountUserService {

    @Override
    public void userQuery(Page<AccountUser> page, UserQueryVo queryVo) {
        QueryWrapper<AccountUser> wrapper = new QueryWrapper<>();

        String name = queryVo.getName();
        String begin = queryVo.getBegin();
        String end = queryVo.getEnd();

        if (!StringUtils.isEmpty(name)) wrapper.like("name", name);
        if (!StringUtils.isEmpty(begin)) wrapper.ge("gmt_create", begin);
        if (!StringUtils.isEmpty(end)) wrapper.le("gmt_modified", end);

        wrapper.orderByAsc("sort"); // 根据sort字段降序
        baseMapper.selectPage(page, wrapper);
    }

    @Override
    public void updateBalance(String userId, String status, BigDecimal change) {
        if (RecordStatusCode.EXPEND.equals(status)) {
            change = change.negate();
        }
        baseMapper.updateBalance(change, userId);
    }
}

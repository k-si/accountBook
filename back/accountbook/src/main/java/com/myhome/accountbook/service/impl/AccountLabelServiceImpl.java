package com.myhome.accountbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myhome.accountbook.entity.AccountLabel;
import com.myhome.accountbook.mapper.AccountLabelMapper;
import com.myhome.accountbook.service.AccountLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流水标签 服务实现类
 * </p>
 *
 * @author orange
 * @since 2021-04-17
 */
@Service
public class AccountLabelServiceImpl extends ServiceImpl<AccountLabelMapper, AccountLabel> implements AccountLabelService {

    @Override
    public AccountLabel getByName(String name) {
        QueryWrapper<AccountLabel> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return baseMapper.selectOne(wrapper);
    }
}

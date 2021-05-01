package com.myhome.accountbook.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myhome.accountbook.entity.AccountUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myhome.accountbook.entity.vo.UserQueryVo;

import java.math.BigDecimal;

/**
 * <p>
 * 账单使用人 服务类
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
public interface AccountUserService extends IService<AccountUser> {

    void userQuery(Page<AccountUser> page, UserQueryVo queryVo);

    void updateBalance(String userId, String status, BigDecimal change);
}

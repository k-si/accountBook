package com.myhome.accountbook.service;

import com.myhome.accountbook.entity.AccountLabel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 流水标签 服务类
 * </p>
 *
 * @author orange
 * @since 2021-04-17
 */
public interface AccountLabelService extends IService<AccountLabel> {

    AccountLabel getByName(String name);
}

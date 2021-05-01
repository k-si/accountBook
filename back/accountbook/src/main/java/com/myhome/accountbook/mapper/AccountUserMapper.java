package com.myhome.accountbook.mapper;

import com.myhome.accountbook.entity.AccountUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 账单使用人 Mapper 接口
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
public interface AccountUserMapper extends BaseMapper<AccountUser> {

    void updateBalance(@Param("change") BigDecimal change, @Param("id") String id);
}

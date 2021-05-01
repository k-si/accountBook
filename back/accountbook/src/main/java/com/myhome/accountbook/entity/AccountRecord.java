package com.myhome.accountbook.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 账单
 * </p>
 *
 * @author orange
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AccountRecord对象", description="账单")
public class AccountRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "事件人id")
    private String userId;

    @ApiModelProperty(value = "标签id")
    private String labelId;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "事件")
    @TableField(value="`event`") // 解决sql语句关键字冲突
    private String event;

    @ApiModelProperty(value = "流水金额")
    @TableField(value="`change`") // 解决sql语句关键字冲突
    private BigDecimal change;

    @ApiModelProperty(value = "流水状态")
    private String status;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}

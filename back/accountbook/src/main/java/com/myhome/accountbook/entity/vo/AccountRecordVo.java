package com.myhome.accountbook.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String event;

    private String label;

    private String status;

    private BigDecimal change;

    private Date gmtCreate;

}

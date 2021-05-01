package com.myhome.accountbook.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;

@Data
public class RecordQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String labelId;

    private BigDecimal low;

    private BigDecimal high;

    private String begin;

    private String end;
}

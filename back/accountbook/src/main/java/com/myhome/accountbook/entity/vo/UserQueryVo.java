package com.myhome.accountbook.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String begin;

    private String end;
}

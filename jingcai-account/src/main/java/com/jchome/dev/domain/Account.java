package com.lqh.dev.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Account {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    private String userId;
    private String name;
    private BigDecimal balance;
    private Date createTime;
    private Date updateTime;
    private Integer version;
}

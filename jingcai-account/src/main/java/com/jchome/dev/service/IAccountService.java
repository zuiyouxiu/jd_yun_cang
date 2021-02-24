package com.lqh.dev.service;

import com.lqh.dev.domain.Account;

import java.util.List;

public interface IAccountService {

    List<Account> findAll();

    Account selectOneByName(String name);

    Long save(Account account);

    void transfer(Account account);

    void print();
}

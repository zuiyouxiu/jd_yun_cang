package com.lqh.dev.controller;

import com.lqh.dev.domain.Account;
import com.lqh.dev.feign.CallRemoteAccount2Service;
import com.lqh.dev.model.ResponseResult;
import com.lqh.dev.service.IAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private IAccountService accountService;

    @Resource
    private CallRemoteAccount2Service account2Service;

    @GetMapping("/find/All")
    public ResponseResult<List<Account>> findList() {
        ResponseResult<List<Account>> response = new ResponseResult<>();
        List<Account> all = accountService.findAll();
        response.setData(all);
        return response;
    }
    @GetMapping("/find/name")
    public ResponseResult<Account> findOneByName(@RequestParam(required = false) String name, ServletRequest request) {
        System.out.println(name);
        if (name == null) {
            Object name1 = request.getAttribute("name");
            name = name1.toString();
        }
        ResponseResult<Account> response = new ResponseResult<>();
        Account account = accountService.selectOneByName(name);
        response.setData(account);
        return response;
    }

    @PostMapping("/save")
    public ResponseResult save(@RequestBody Account account) {
        accountService.save(account);
        ResponseResult<String> result = new ResponseResult<>();
        result.setMsg("account：保存成功");
        return result;
    }

    @PutMapping("/transfer")
    public ResponseResult<Boolean> transfer(@RequestBody Account account) {
        ResponseResult<Boolean> result = new ResponseResult<>();
        boolean flag = false;
        accountService.transfer(account);
        flag = true;
        if (flag) {
            result.setMsg("account转账成功");
        }
        result.setData(flag);
        return result;
    }
}
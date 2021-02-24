package com.lqh.dev.controller;

import com.lqh.dev.listener.CustomerHttpSessionListener;
import com.lqh.dev.model.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {

    @GetMapping("/hello")
    public ResponseResult hello() {
        ResponseResult<String> result = new ResponseResult<>();
        result.setData("hello world");
        return result;
    }

    @GetMapping("/online")
    public String online() {
        return "当前在线 " + CustomerHttpSessionListener.count + " 人";
    }
}

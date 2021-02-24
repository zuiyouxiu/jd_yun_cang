package com.lqh.dev.feign;

import com.lqh.dev.model.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("luck-account2")
public interface CallRemoteAccount2Service {

    @GetMapping("/account2/find/name")
    ResponseResult findOneByName(@RequestParam("name") String name);
}

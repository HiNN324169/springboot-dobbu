package com.nn.controller;

import com.nn.service.ConsumerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Author nn
 * @Date 2020/5/6 18:48
 */
@RestController
@RequestMapping(value = "/dubbo")
public class UserController {

    @Autowired
    private ConsumerServiceImpl consumerServiceImpl;

    @RequestMapping("/getAllUser")
    public Object getAllUser(){
        return consumerServiceImpl.queryAllUser();
    }
}

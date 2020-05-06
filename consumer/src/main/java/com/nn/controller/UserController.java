package com.nn.controller;

import com.nn.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
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

    @Reference
    private UserService userService;

    @RequestMapping("/getAllUser")
    public Object getAllUser(){
        return userService.queryAllUser();
    }
}

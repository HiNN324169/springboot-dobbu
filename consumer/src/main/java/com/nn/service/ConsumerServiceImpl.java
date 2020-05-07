package com.nn.service;

import com.nn.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ConsumerService
 * @Author nn
 * @Date 2020/5/7 12:14
 */

@Service
public class ConsumerServiceImpl implements UserService {

//    @org.apache.dubbo.config.annotation.Reference
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> queryAllUser() {
        return userService.queryAllUser();
    }
}

package com.nn.service.impl;

import com.nn.pojo.User;
import com.nn.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Author nn
 * @Date 2020/5/6 17:55
 */
//@org.apache.dubbo.config.annotation.Service
    @Service
public class UserServiceImpl implements UserService {

    private static List<User> userList = new ArrayList<>();

    static {
        userList.add(new User(1,"张三","男"));
        userList.add(new User(2,"李四","男"));
        userList.add(new User(3,"小花","女"));
    }

    @Override
    public List<User> queryAllUser() {
        return userList;
    }
}

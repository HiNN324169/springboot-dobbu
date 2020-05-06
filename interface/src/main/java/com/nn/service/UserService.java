package com.nn.service;

import com.nn.pojo.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Author nn
 * @Date 2020/5/6 17:51
 */
public interface UserService {

    /**
     *  查询所有用户
     * @return
     */
    public List<User> queryAllUser();
}

package com.hhj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhj.domain.User;
import com.hhj.mapper.UserMapper;
import com.hhj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    // 取出mapper
    @Autowired
    UserMapper userMapper;
    @Override
    public Integer selectCount(Wrapper<User> queryWrapper) {
        Integer integer = userMapper.selectCount(null);
        return integer;
    }

    @Override
    public List<User> selectList(Wrapper<User> queryWrapper) {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @Override
    public IPage<User> selectUserPage(Page<User> page) {
        IPage<User> userIPage = userMapper.selectUserPage(page);
        return userIPage;
    }

    @Override
    public User getUserById(int id) {
        User user = userMapper.selectById(id);
        return user;

    }


}

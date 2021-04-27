package com.hhj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhj.domain.User;


import java.util.List;

public interface UserService {
    // 这个Wrapper是查询条件,没有条件写null
    Integer selectCount(Wrapper<User> queryWrapper);

    List<User> selectList(Wrapper<User> queryWrapper);

    IPage<User> selectUserPage(Page<User> page);

    User getUserById(int id);
}

package com.hhj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhj.domain.User;
import org.apache.ibatis.annotations.Mapper;


import java.io.Serializable;
import java.util.List;

// Mybati plus的mapper接口
@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 直接重写该方法即可调用
    @Override
    Integer selectCount(Wrapper<User> queryWrapper);

    @Override
    List<User> selectList(Wrapper<User> queryWrapper);

    IPage<User> selectUserPage(Page<User> page);

    @Override
    User selectById(Serializable id);
}

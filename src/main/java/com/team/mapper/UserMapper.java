package com.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

package com.team.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team.domain.User;
import com.team.dto.Result;

public interface IUserService extends IService<User> {

    Result insert(String userName, String email);

}

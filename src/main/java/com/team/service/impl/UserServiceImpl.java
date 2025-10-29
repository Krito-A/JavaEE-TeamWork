package com.team.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.domain.RabbitMqConstant;
import com.team.domain.User;
import com.team.dto.Result;
import com.team.mapper.UserMapper;
import com.team.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result insert(String userName, String email) {
        // 基本空值校验
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(email)){
            return Result.fail("表单数据不能为空");
        }
        // Email格式校验：包含@且以.com结尾
        if(!isValidEmail(email)) {
            return Result.fail("邮箱格式不正确，必须包含@且以.com结尾");
        }
        // 判断user是否已经存在
        if(count(new QueryWrapper<User>().eq("email", email)) > 0) {
            return Result.fail("该邮箱已存在");
        }
        // 不存在，保存
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        save(user);
        // TODO：MQ发送消息（消息体：user），消费端消费消息，给对应邮箱发欢迎邮件
        rabbitTemplate.convertAndSend(
                RabbitMqConstant.EMAIL_SEND_QUEUE,  // 目标队列
                user                                 // 消息体（User对象）
        );
        return Result.ok(user);
    }

    /**
     * 校验邮箱格式：包含@且以.com结尾
     * @param email 待校验的邮箱地址
     * @return 格式是否正确
     */
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.com$");
    }


}

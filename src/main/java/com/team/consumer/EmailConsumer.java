package com.team.consumer;

import com.team.domain.RabbitMqConstant;
import com.team.domain.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    // 注入邮件发送工具
    @Autowired
    @Qualifier("mailSender")
    private JavaMailSender mailSender;

    // 从配置文件获取发送方邮箱（即配置的QQ邮箱）
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 监听邮件队列：接收消息并发送QQ邮件
     * @param user 队列中的User消息（包含username+email）
     */
    @RabbitListener(queues = RabbitMqConstant.EMAIL_SEND_QUEUE)
    public void receiveMessageAndSendEmail(User user) {
        try {
            // 1. 构建简单邮件（文本类型，满足需求）
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);                // 发送方邮箱
            mailMessage.setTo(user.getEmail());            // 接收方邮箱（用户邮箱）
            mailMessage.setSubject("欢迎登录通知");         // 邮件主题
            // 邮件内容（按需求格式拼接）
            String content = user.getUserName() + "，欢迎您～JavaEE221小组在此向您问候！";
            mailMessage.setText(content);

            // 2. 发送邮件
            mailSender.send(mailMessage);
            System.out.println("邮件发送成功！收件人：" + user.getEmail());

        } catch (Exception e) {
            System.err.println("邮件发送失败！收件人：" + user.getEmail() + "，错误信息：" + e.getMessage());
        }
    }
}

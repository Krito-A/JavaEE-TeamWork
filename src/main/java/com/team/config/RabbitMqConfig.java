package com.team.config;

import com.team.domain.RabbitMqConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // 声明邮件发送队列（ durable=true：队列持久化，避免服务重启丢失）
    @Bean
    public Queue emailSendQueue() {
        return new Queue(RabbitMqConstant.EMAIL_SEND_QUEUE, true);
    }
}

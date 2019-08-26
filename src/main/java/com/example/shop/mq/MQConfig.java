package com.example.shop.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {
    public static final String QUEUE = "queue";
    public static final String ORDER_QUEUE = "order.queue";

    @Bean
    public Queue orderQueue(){
        return new Queue(ORDER_QUEUE, true);
    }

    /**
     * Direct 模式
     */
    @Bean
    public Queue queueMQ(){
        return new Queue(MQConfig.QUEUE, true);
    }
}

package com.example.shop.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* 延时队列生产者
* @author liu
* @date 15:44 2019/8/27
**/
@Component
public class DelayedSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param msg
     */
    public void send(Integer msg) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("发送时间：" + sf.format(new Date()) + "发送内容" + msg);

        rabbitTemplate.convertAndSend(DelayedConfig.EXCHANGE_NAME, DelayedConfig.QUEUE_NAME, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", 30000);
                return message;
            }
        });
    }
}

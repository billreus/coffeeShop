package com.example.shop.mq;

import com.example.shop.model.CartEntity;
import com.example.shop.model.StockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(CartEntity msg){
        logger.info("准备发送mq "+msg.getGoodsId());
        amqpTemplate.convertAndSend(MQConfig.ORDER_QUEUE, msg);
    }
}

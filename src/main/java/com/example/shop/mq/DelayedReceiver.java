package com.example.shop.mq;

import com.example.shop.mapper.OrderMapper;
import com.example.shop.util.OrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 延时队列消费者
 * @author Liu
 */
@Component
@RabbitListener(queues = "delayed.goods.order")
public class DelayedReceiver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 订单接口
     */
    @Resource
    private OrderMapper orderMapper;

    @RabbitHandler
    public void process(Integer msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer state = orderMapper.selectById(msg).getOrderStatus();
        logger.info("接收时间:" + sdf.format(new Date()) + " 内容 " + msg + " 状态 " + state);
        if( state == OrderUtil.STATUS_CREATE){
            orderMapper.updateStatus(msg, OrderUtil.STATUS_CANCEL);
            logger.info("状态更改");
        }else {
            logger.info("状态未改");
        }

    }
}

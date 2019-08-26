package com.example.shop.mq;

import com.example.shop.mapper.StockMapper;
import com.example.shop.model.CartEntity;
import com.example.shop.model.StockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MQReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private StockMapper stockMapper;

    @RabbitListener(queues =  MQConfig.ORDER_QUEUE)
    public String receive(CartEntity message){
        logger.info("收到MQ库存 " + message.getGoodsId());
        Integer reduceStock = message.getNumber();
        StockEntity stockEntity = stockMapper.selectByGoodsId(message.getGoodsId());
        Integer stock = stockEntity.getStock();
        Integer saleCount = stockEntity.getSaleCount();
        if(reduceStock > stock){
            return "error";
        }
        Integer updateStock = stock - reduceStock;
        Integer updateSaleCount = saleCount + reduceStock;
        stockEntity.setStock(updateStock);
        stockEntity.setSaleCount(updateSaleCount);
        stockMapper.updateByGoodsId(stockEntity.getStock(), stockEntity.getSaleCount(), message.getGoodsId());
        return "success";
    }
}

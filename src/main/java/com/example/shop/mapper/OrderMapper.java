package com.example.shop.mapper;

import com.example.shop.model.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单mapper接口
 */
@Mapper
public interface OrderMapper {
    /**
     * 通过userId查询订单
     * @param userId
     * @return
     */
    List<OrderEntity> selectByUserId(Integer userId);

    /**
     * 通过userId和状态查询订单
     * @param userId
     * @param orderStatus
     * @return
     */
    List<OrderEntity> selectByUserIdAndStatus(Integer userId, Integer orderStatus);
    /**
     * 通过id查询订单
     * @param id
     * @return
     */
    OrderEntity selectById(Integer id);

    int updateStatus(@Param("id")Integer id, @Param("orderStatus") Integer orderStatus);

    /**
     * 添加订单
     * @param orderEntity
     * @return
     */
    Integer insert(OrderEntity orderEntity);

    /**
     * 删除订单
     * @param id
     * @return
     */
    int delete(Integer id);

    long count();

    List<OrderEntity> findAllList(String userId, String orderSn, Integer orderStatusArray);
}

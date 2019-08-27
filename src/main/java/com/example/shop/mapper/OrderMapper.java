package com.example.shop.mapper;

import com.example.shop.model.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 订单表接口
* @author liu
* @date 14:11 2019/8/27
* @param
* @return
**/
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
    /**
     * 更新订单状态
     * @param id
     * @param orderStatus
     * @return
     */
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
    /**
     * 订单总数
     * @return
     */
    long count();
    /**
     * 订单列表动态查询
     * @param userId
     * @param orderSn
     * @param orderStatusArray
     * @param pages
     * @param limit
     * @return
     */
    List<OrderEntity> findAllList(String userId, String orderSn, Integer orderStatusArray, Integer pages, Integer limit);
}

package com.example.shop.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
/**
 * 后台统计接口
 * @author liu
 * @date 14:14 2019/8/27
 * @param
 * @return
 **/
@Mapper
public interface AdminStatMapper {
    /**
     * 统计用户新建时间和数量
     * @return
     */
    List<Map> statUser();

    /**
     * 统计订单销量数量
     * @return
     */
    List<Map> statOrder();

    /**
     * 统计商品订单，数量，总额
     * @return
     */
    List<Map> statGoods();

}

package com.example.shop.mapper;

import com.example.shop.model.CartEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    /**
     * 统计购物车中商品数量
     * @param userId
     * @return
     */
    List<CartEntity> selectByUserId(Integer userId);

    /**
     * 通过用户id和是否选中查询
     * @param userId
     * @param checked
     * @return
     */
    List<CartEntity> selectByUserIdAndChecked(Integer userId, int checked);

    /**
     * 通过id查询购物车商品
     * @param id
     * @return
     */
    CartEntity selectById(Integer id);

    /**
     * 通过id更新购物车商品数量
     * @param cartEntity
     * @return
     */
    CartEntity updateById(CartEntity cartEntity);

    /**
     * 通过用户和商品id查询
     * @param userId
     * @param goodsId
     * @return
     */
    CartEntity selectByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);
    /**
     * 通过用户id和商品id删除购物车
     * @param userId
     * @param goodsId
     * @return
     */
    int deleteByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    /**
     * 通过用户和商品id更新选中状态
     * @param userId
     * @param goodsId
     * @param checked
     * @return
     */
    int updateCheckById(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId, @Param("checked") boolean checked);

    /**
     * 增加购物车
     * @param cartEntity
     * @return
     */
    int insertCart(CartEntity cartEntity);

    /**
     * 删除购物车
     * @param userId
     * @param checked
     * @return
     */
    int delete(@Param("userId") Integer userId,@Param("checked") boolean checked);
}

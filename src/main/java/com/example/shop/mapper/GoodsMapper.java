package com.example.shop.mapper;

import com.example.shop.model.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 商品表接口
* @author liu
* @date 13:43 2019/8/27
* @param
* @return
**/
@Mapper
public interface GoodsMapper {

    /**
     * 商品上架总数
     * @return
     */
    long findSaleCount();

    /**
     * 通过种类id搜索商品
     * @param categoryId
     * @return
     */
    List<GoodsEntity> selectByCategoryId(Integer categoryId);

    /**
     * 通过id搜索商品
     * @param Id
     * @return
     */
    GoodsEntity selectById(Integer Id);

    /**
     * 动态商品id，商品名搜索
     * @param goodsId
     * @param name
     * @param pages
     * @param limit
     * @return
     */
    List<GoodsEntity> findAllList(String goodsId, String name, Integer pages, Integer limit);

    /**
     * 通过销量搜索指定个数
     * @param limit
     * @param order
     * @return
     */
    List<GoodsEntity> findList(Integer limit, String order);

    /**
     * 通过商品id搜索
     * @param goodsId
     * @return
     */
    List<GoodsEntity> selectByGoodsId(String goodsId);

    /**
     * 通过id删除
     * @param goodsId
     * @return
     */
    int deleted(Integer goodsId);

    /**
     * 更新商品
     * @param goodsEntity
     * @return
     */
    int update(GoodsEntity goodsEntity);

    /**
     * 插入新商品
     * @param goodsEntity
     * @return
     */
    int insert(GoodsEntity goodsEntity);
}

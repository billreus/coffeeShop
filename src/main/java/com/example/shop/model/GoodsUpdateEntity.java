package com.example.shop.model;

/**
* 商品后台更新实体类
* @author liu
* @date 15:41 2019/8/27
**/
public class GoodsUpdateEntity {
    /**
     * 商品参数
     */
    AttributeEntity[] attributes;
    /**
     * 商品
     */
    GoodsEntity goods;
    /**
     * 库存
     */
    StockEntity[] products;

    public GoodsEntity getGoods() {
        return goods;
    }

    public void setGoods(GoodsEntity goods) {
        this.goods = goods;
    }

    public AttributeEntity[] getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributeEntity[] attribute) {
        this.attributes = attribute;
    }

    public StockEntity[] getProducts() {
        return products;
    }

    public void setProducts(StockEntity[] products) {
        this.products = products;
    }
}

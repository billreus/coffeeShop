package com.example.shop.model;

/**
 * 商品后台更新实体类
 */
public class GoodsUpdateEntity {
    AttributeEntity[] attributes;
    GoodsEntity goods;
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

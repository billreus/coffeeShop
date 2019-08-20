package com.example.shop.model;

public class CartStockEntity {
    /**
     * 购物车实体类
     */
    private CartEntity cartEntity;
    /**
     * 库存
     */
    private int stock;

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

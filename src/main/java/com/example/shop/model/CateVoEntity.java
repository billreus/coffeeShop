package com.example.shop.model;

import java.util.List;

/**
 * 分类前端二级显示实体类
 */
public class CateVoEntity {
    /**
     * id
     */
    private Integer value = null;
    /**
     * 类名
     */
    private String label = null;
    /**
     * 子类
     */
    private List children = null;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }
}

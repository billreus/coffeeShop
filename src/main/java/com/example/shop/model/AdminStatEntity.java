package com.example.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 后台统计实体类
* @author liu
* @date 14:15 2019/8/27
**/
public class AdminStatEntity {
    /**
     * 纵轴
     */
    private String[] columns = new String[0];
    /**
     * 横轴
     */
    private List<Map> rows = new ArrayList<>();

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public List<Map> getRows() {
        return rows;
    }

    public void setRows(List<Map> rows) {
        this.rows = rows;
    }
}

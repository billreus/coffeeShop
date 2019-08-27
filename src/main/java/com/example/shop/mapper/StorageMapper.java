package com.example.shop.mapper;

import com.example.shop.model.StorageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 存储表接口
* @author liu
* @date 15:34 2019/8/27
**/
@Mapper
public interface StorageMapper {
    /**
     * 通过key查询
     * @param key
     * @return
     */
    StorageEntity selectByKey(String key);

    /**
     * 添加
     * @param storageEntity
     * @return
     */
    int insert(StorageEntity storageEntity);
}

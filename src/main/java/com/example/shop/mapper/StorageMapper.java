package com.example.shop.mapper;

import com.example.shop.model.StorageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 存储
 */
@Mapper
public interface StorageMapper {

    StorageEntity selectByKey(String key);

    int insert(StorageEntity storageEntity);
}

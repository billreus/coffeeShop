package com.example.shop.mapper;

import com.example.shop.model.StorageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StorageMapper {

    StorageEntity selectByKey(String key);

    int insert(StorageEntity storageEntity);
}

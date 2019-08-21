package com.example.shop.mapper;

import com.example.shop.model.AddressEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地址mapper层
 */
@Mapper
public interface AddressMapper {

    /**
     * 通过userId查询
     * @param userId
     * @return
     */
    List<AddressEntity> selectByUserId(Integer userId);

    AddressEntity selectByUserIdAndId(Integer userId, Integer id);

    AddressEntity selectByUserIdDefault(Integer userId);
    /**
     * 更新默认地址
     * @param userId
     * @return
     */
    int updateDefaultByUserId(Integer userId);
    int updateDefault0ByUserId(Integer userId);
    /**
     * 添加地址
     * @param addressEntity
     * @return
     */
    int insert(AddressEntity addressEntity);

    /**
     * 删除地址
     * @param id
     * @return
     */
    int delete(Integer id);

    List<AddressEntity> findAllList(String userId, String name, Integer limit, Integer pages);

    int update(AddressEntity addressEntity);

    long findAllCount();
}

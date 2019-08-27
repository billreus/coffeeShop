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

    /**
     * 通过id和用户id查询
     * @param userId
     * @param id
     * @return
     */
    AddressEntity selectByUserIdAndId(Integer userId, Integer id);

    /**
     * 通过勾选默认和用户id查询
     * @param userId
     * @return
     */
    AddressEntity selectByUserIdDefault(Integer userId);
    /**
     * 更新默认地址
     * @param userId
     * @return
     */
    int updateDefaultByUserId(Integer userId);

    /**
     * 默认地址清零
     * @param userId
     * @return
     */
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

    /**
     * 所有地址（分页）
     * @param userId
     * @param name
     * @param limit
     * @param pages
     * @return
     */
    List<AddressEntity> findAllList(String userId, String name, Integer limit, Integer pages);

    /**
     * 更新地址
     * @param addressEntity
     * @return
     */
    int update(AddressEntity addressEntity);

    /**
     * 地址总数
     * @return
     */
    long findAllCount();
}

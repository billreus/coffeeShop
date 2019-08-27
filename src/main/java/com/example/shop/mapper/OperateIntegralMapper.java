package com.example.shop.mapper;

import com.example.shop.model.OperateIntegralEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 积分规则表接口
* @author liu
* @date 14:05 2019/8/27
* @param
* @return
**/
@Mapper
public interface OperateIntegralMapper {

    /**
     * 通过等级查询
     * @param level
     * @return
     */
    OperateIntegralEntity selectByLevel(Integer level);

    /**
     * 分页查看所有规则
     * @param pages
     * @param limit
     * @return
     */
    List<OperateIntegralEntity> findAllList(Integer pages, Integer limit);

    /**
     * 删除规则
     * @param operateIntegralEntity
     * @return
     */
    int delete(OperateIntegralEntity operateIntegralEntity);

    /**
     * 更新规则
     * @param operateIntegralEntity
     * @return
     */
    int update(OperateIntegralEntity operateIntegralEntity);

    /**
     * 新建规则
     * @param operateIntegralEntity
     * @return
     */
    int insert(OperateIntegralEntity operateIntegralEntity);

    /**
     * 规则总数
     * @return
     */
    long count();
}

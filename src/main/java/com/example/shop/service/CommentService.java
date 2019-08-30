package com.example.shop.service;

import com.example.shop.mapper.CommentMapper;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.CommentEntity;
import com.example.shop.model.UserEntity;
import com.example.shop.model.UserInfoEntity;
import com.example.shop.util.ShopUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品评论
 * @author liu
 * @date 14:44 2019/8/27
 **/
@Service
public class CommentService {

    /**
     * 评论表接口
     */
    @Resource
    private CommentMapper commentMapper;
    /**
     * 用户表接口
     */
    @Resource
    private UserMapper userMapper;

    /**
     * 商品评论
     * @param type
     * @param valueId
     * @param showType
     * @param page
     * @param limit
     * @return
     */
    public Object list(Integer type, Integer valueId, Integer showType, Integer page, Integer limit){
        List<CommentEntity> commentEntityList = commentMapper.selectByGoodsId(valueId);
        List<Map<String, Object>> commentVoList = new ArrayList<>(commentEntityList.size());
        for (CommentEntity comment : commentEntityList) {
            Map<String, Object> commentVo = new HashMap<>();
            commentVo.put("addTime", comment.getAddTime());
            commentVo.put("content", comment.getContent());
            UserEntity userInfo = userMapper.selectById(comment.getUserId());
            commentVo.put("userInfo", userInfo);
            commentVoList.add(commentVo);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", commentVoList);
        return ShopUtil.ok(data);
    }
}

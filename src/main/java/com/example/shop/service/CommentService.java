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

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    public Object list(Integer type, Integer valueId, Integer showType, Integer page, Integer limit){
        List<CommentEntity> commentEntityList = commentMapper.selectByGoodsId(valueId);
        List<Map<String, Object>> commentVoList = new ArrayList<>(commentEntityList.size());
        for (CommentEntity comment : commentEntityList) {
            Map<String, Object> commentVo = new HashMap<>();
            commentVo.put("addTime", comment.getAddTime());
            commentVo.put("content", comment.getContent());
            //commentVo.put("picList", comment.getPicUrls());

            UserEntity userInfo = userMapper.selectById(comment.getUserId());
            commentVo.put("userInfo", userInfo);

            //String reply = commentMapper.s(comment.getId());
            //commentVo.put("reply", reply);

            commentVoList.add(commentVo);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", commentVoList);
        return ShopUtil.getJSONString(0, "success",data);
    }
}

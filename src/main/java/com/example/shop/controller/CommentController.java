package com.example.shop.controller;

import com.example.shop.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* 商品评论
* @author liu
* @date 14:44 2019/8/27
* @param
* @return
**/
@RestController
@RequestMapping("/wx/comment")
public class CommentController {

    /**
     * 评论显示接口
     */
    @Resource
    private CommentService commentService;

    /**
     * 评论详情
     * @param type
     * @param valueId
     * @param showType
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    public Object list(Integer type, Integer valueId, Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit){
        return commentService.list(type, valueId, showType, page, limit);
    }
}

package com.example.shop.controller;

import com.example.shop.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wx/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("list")
    public Object list(Integer type, Integer valueId, Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit){
        return commentService.list(type, valueId, showType, page, limit);
    }
}

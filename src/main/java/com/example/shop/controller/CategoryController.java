package com.example.shop.controller;

import com.example.shop.service.CategoryService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
* 分类
* @author liu
* @date 14:41 2019/8/27
**/
@RestController
@RequestMapping("/wx/catalog")
public class CategoryController {

    /**
     * 分类业务
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 分类显示
     * @param id
     * @return
     */
    @GetMapping("index")
    public String index(Integer id){
        Map<String, Object> data = categoryService.index(id);
        return ShopUtil.getJSONString(0, "成功", data);
    }

    /**
     * 二级类详情
     * @param id
     * @return
     */
    @GetMapping("current")
    public Object current(@NotNull Integer id){

        Map<String, Object> data = categoryService.current(id);
        if(data == null){
            return ShopUtil.getJSONString(402, "参数错误");
        }else{
            return ShopUtil.getJSONString(0, "成功", data);

        }
    }
}

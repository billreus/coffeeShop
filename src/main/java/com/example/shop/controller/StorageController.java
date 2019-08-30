package com.example.shop.controller;

import com.example.shop.model.StorageEntity;
import com.example.shop.service.StorageService;
import com.example.shop.util.ShopUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * 图片存储
 * @author liu
 * @date 15:15 2019/8/27
 **/
@RestController
@RequestMapping("admin/storage")
public class StorageController {
    /**
     * 图片存储表接口
     */
    @Resource
    StorageService storageService;

    /**
     * 存储文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/create")
    public Map create(@RequestParam("file")MultipartFile file)throws IOException{
        String originalFilename = file.getOriginalFilename();
        StorageEntity storageEntity = storageService.create(file.getInputStream(), file.getSize(),
                file.getContentType(), originalFilename);
        return ShopUtil.ok(storageEntity);
    }
}

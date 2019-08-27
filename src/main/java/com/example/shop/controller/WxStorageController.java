package com.example.shop.controller;

import com.example.shop.mapper.StorageMapper;
import com.example.shop.model.StorageEntity;
import com.example.shop.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

/**
* 前端获取图片
* @author liu
* @date 15:33 2019/8/27
* @param
* @return
**/
@RestController
@RequestMapping("/wx/storage")
public class WxStorageController {
    /**
     * 存储表接口
     */
    @Autowired
    private StorageMapper storageMapper;
    /**
     * 存储操作
     */
    @Autowired
    private StorageService storageService;

    /**
     * 读取图片
     * @param key
     * @return
     */
    @GetMapping("/fetch/{key:.+}")
    public ResponseEntity<Resource> fetch(@PathVariable String key){
        StorageEntity storageEntity = storageMapper.selectByKey(key);
        String type = storageEntity.getType();
        MediaType mediaType = MediaType.parseMediaType(type);
        Resource file = storageService.loadAsResource(key);
        if(file == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }
}

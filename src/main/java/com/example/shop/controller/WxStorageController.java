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


@RestController
@RequestMapping("/wx/storage")
public class WxStorageController {

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private StorageService storageService;

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

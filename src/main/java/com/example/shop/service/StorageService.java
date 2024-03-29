package com.example.shop.service;

import com.example.shop.mapper.StorageMapper;
import com.example.shop.model.StorageEntity;
import com.example.shop.util.CharUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
* 图片存储
* @author liu
* @date 15:15 2019/8/27
**/
@Service
public class StorageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 图片存储操作
     */
    @Autowired
    StorageMapper storageMapper;

    /**
     * 存储图片地址
     */
    private String storage = "C:\\Users\\EDZ\\Downloads\\picShop";
    /**
     * 图片url
     */
    private String address = "http://localhost:8080/wx/storage/fetch/";
    private Path rootLocation = Paths.get(storage);

    /**
     * 创建图片信息
     * @param inputStream
     * @param contentLength
     * @param contentType
     * @param fileName
     * @return
     */
    public StorageEntity create(InputStream inputStream, long contentLength, String contentType, String fileName){
        String key = generateKey(fileName);
        try{
            Files.copy(inputStream, rootLocation.resolve(key), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new RuntimeException("failed"+key, e);
        }
        String url = generateUrl(key);
        StorageEntity storageEntity = new StorageEntity();
        storageEntity.setName(fileName);
        storageEntity.setSize((int)contentLength);
        storageEntity.setType(contentType);
        storageEntity.setKey(key);
        storageEntity.setUrl(url);
        logger.info(String.valueOf(storageEntity));
        storageMapper.insert(storageEntity);
        return storageEntity;
    }

    /**
     * 生成key
     * @param originalFilename
     * @return
     */
    private String generateKey(String originalFilename){
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key = null;
        StorageEntity storageEntity = null;
        do{
            key = CharUtil.getRandomString(20)+suffix;
            storageEntity = storageMapper.selectByKey(key);
        }while (storageEntity != null);
        return key;
    }

    /**
     * 生成url
     * @param keyName
     * @return
     */
    private String generateUrl(String keyName){
        return address + keyName;
    }

    /**
     * 本地存储
     * @param filename
     * @return
     */
    public Resource loadAsResource(String filename){
        try{
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            return  resource;
        } catch (MalformedURLException e){
            logger.error(e.getMessage(), e);
            return null;
        }

    }
}

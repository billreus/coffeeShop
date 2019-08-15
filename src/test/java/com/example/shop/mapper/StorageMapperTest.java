package com.example.shop.mapper;

import com.example.shop.model.StorageEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StorageMapperTest {

    @Autowired
    private StorageMapper storageMapper;

    @Test
    public void insert() {
        StorageEntity storageEntity = new StorageEntity();
        storageEntity.setUrl("adfsadaf");
        storageEntity.setKey("eaf");
        storageEntity.setType("tsts");
        storageEntity.setName("dafa");
        storageEntity.setSize(1532);
        //storageEntity.setAddTime("");
        //storageEntity.setUpdateTime("");
        //storageEntity.setDeleted(false);
        storageMapper.insert(storageEntity);
    }
}

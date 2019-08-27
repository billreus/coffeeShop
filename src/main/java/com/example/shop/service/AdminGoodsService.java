package com.example.shop.service;

import com.example.shop.mapper.AttributeMapper;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.mapper.GoodsMapper;
import com.example.shop.mapper.StockMapper;
import com.example.shop.model.*;
import com.example.shop.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台商品管理
 * @author liu
 * @date 11:28 2019/8/27
 **/
@Service
public class AdminGoodsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * redis中商品缓存key
     */
    private final String key = "stock";
    /**
     * redis接口
     */
    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 商品表接口
     */
    @Resource
    private GoodsMapper goodsMapper;
    /**
     * 商品参数表接口
     */
    @Resource
    private AttributeMapper attributeMapper;
    /**
     * 商品类别表接口
     */
    @Resource
    private CategoryMapper categoryMapper;
    /**
     * 库存表接口
     */
    @Resource
    private StockMapper stockMapper;

    /**
     * 商品列表
     * @param goodsId
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public Map<String, Object> list(String goodsId, String name, Integer page, Integer limit,
                                    String sort, String order){
        long count = goodsMapper.findSaleCount();
        Integer start = (page-1)*limit;
        List<GoodsEntity> goodsEntityList =  goodsMapper.findAllList(name, goodsId,start,limit);

        Map<String, Object> data = new HashMap<>();
        data.put("list", goodsEntityList);
        data.put("total", count);
        data.put("page", page);
        data.put("limit", limit);
        data.put("pages", count/limit);
        return data;
    }

    /**
     * 商品删除
     * @param goodsEntity
     */
    public void delete(GoodsEntity goodsEntity){
        Integer goodsId = goodsEntity.getId();
        goodsMapper.deleted(goodsId);
    }

    /**
     * 商品详情
     * @param id
     * @return
     */
    public Map<String, Object> detail(Integer id){
        GoodsEntity goodsEntity = goodsMapper.selectById(id);
        List<AttributeEntity> attributeEntityList = attributeMapper.selectByGoodsId(id);
        StockEntity stockEntity = stockMapper.selectByGoodsId(id);
        List<StockEntity> stockEntityList = new ArrayList<>();
        stockEntityList.add(stockEntity);

        Integer categoryId = goodsEntity.getCategoryId();
        CategoryEntity categoryEntity = categoryMapper.selectById(categoryId);
        Integer[] categoryIds = new Integer[]{};
        if(categoryEntity != null){
            int parentCategoryId = categoryEntity.getPid();
            categoryIds = new Integer[]{parentCategoryId, categoryId};
        }

        Map<String, Object> data = new HashMap<>();
        data.put("goods", goodsEntity);
        data.put("products",stockEntityList);
        data.put("attributes", attributeEntityList);
        data.put("categoryIds", categoryIds);
        return data;
    }

    public Map<String, Object> list2(){
        List<CategoryEntity> l1List = categoryMapper.selectByLevel("L1");
        List<CateVoEntity> cateVoEntityList = new ArrayList<>(l1List.size());

        for (CategoryEntity l1 : l1List){
            CateVoEntity cateVol1 = new CateVoEntity();
            cateVol1.setValue(l1.getId());
            cateVol1.setLabel(l1.getName());

            List<CategoryEntity> l2List = categoryMapper.selectByPid(l1.getId());
            List<CateVoEntity> children = new ArrayList<>(l2List.size());
            for(CategoryEntity l2 : l2List){
                CateVoEntity cateVol2 = new CateVoEntity();
                cateVol2.setLabel(l2.getName());
                cateVol2.setValue(l2.getId());
                children.add(cateVol2);
            }
            cateVol1.setChildren(children);
            cateVoEntityList.add(cateVol1);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", cateVoEntityList);
        return data;
    }

    /**
     * 商品更新
     * @param goodsUpdateEntity
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(GoodsUpdateEntity goodsUpdateEntity){
        GoodsEntity goods = goodsUpdateEntity.getGoods();
        AttributeEntity[] attributes = goodsUpdateEntity.getAttributes();
        StockEntity[] stock = goodsUpdateEntity.getProducts();
        //更新库存表
        Integer updateStock = stock[0].getStock();
        Integer goodsId = stock[0].getGoodsId();
        Integer saleCount = stock[0].getSaleCount();
        stockMapper.updateByGoodsId(updateStock, saleCount,goodsId);
        //更新redis中的库存
        stock[0].setStock(updateStock);
        stock[0].setSaleCount(saleCount);
        redisTemplate.boundHashOps(key).put(stock[0].getGoodsId(), stock[0]);
        //更新商品表
        goods.setUpdateDate(TimeUtil.createTime());
        goodsMapper.update(goods);
        //更新商品参数
        for(AttributeEntity attributeEntity : attributes){
            Integer id = attributeEntity.getId();
            if(id != 0){
                attributeMapper.update(attributeEntity);
            }else{
                attributeEntity.setGoodsId(goods.getId());
                attributeMapper.insert(attributeEntity);
            }
        }
    }

    /**
     * 新商品上架
     * @param goodsUpdateEntity
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(GoodsUpdateEntity goodsUpdateEntity){
        GoodsEntity goods = goodsUpdateEntity.getGoods();
        AttributeEntity[] attributes = goodsUpdateEntity.getAttributes();
        StockEntity[] stock = goodsUpdateEntity.getProducts();
        goods.setCreateDate(TimeUtil.createTime());
        goodsMapper.insert(goods);

        //添加库存，刷新缓存
        Integer goodsId = goods.getId();
        StockEntity stockEntity  = stock[0];
        stockEntity.setGoodsId(goodsId);
        stockMapper.insert(stockEntity);
        redisTemplate.boundHashOps(key).put(goodsId, stockEntity);

        //添加商品参数
        for(AttributeEntity attributeEntity : attributes){
            attributeEntity.setGoodsId(goodsId);
            attributeMapper.insert(attributeEntity);
        }
    }
}

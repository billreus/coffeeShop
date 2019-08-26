package com.example.shop.service;

import com.example.shop.mapper.*;
import com.example.shop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 */
@Service
public class GoodsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String key = "stock";

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private AttributeMapper attributeMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 商品总数
     * @return
     */
    public long count(){
        return goodsMapper.findSaleCount();
    }

    /**
     * 商品分类页的分类
     * @param id
     * @return
     */
    public Map<String, Object> category(Integer id){
        CategoryEntity currentCategory = categoryMapper.selectById(id);
        CategoryEntity parentCategory = null;
        List<CategoryEntity> childrenCategory = null;

        if(currentCategory.getPid() == 0){
            parentCategory = currentCategory;
            childrenCategory = categoryMapper.selectByPid(currentCategory.getId());
            currentCategory = childrenCategory.size() > 0 ? childrenCategory.get(0) : currentCategory;
        }else{
            parentCategory = categoryMapper.selectById(currentCategory.getPid());
            childrenCategory = categoryMapper.selectByPid(currentCategory.getPid());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("currentCategory", currentCategory);
        data.put("parentCategory", parentCategory);
        data.put("brotherCategory", childrenCategory);
        return data;
    }

    /**
     * 分类页商品列表
     * @param categoryId
     * @param page
     * @param limit
     * @return
     */
    public Map<String, Object> list(Integer categoryId, Integer page, Integer limit){
        List<GoodsEntity> goodsList = goodsMapper.selectByCategoryId(categoryId);
        Map<String, Object> data = new HashMap<>();
        data.put("list", goodsList);
        return data;
    }

    /**
     * 商品详情页
     * @param id 商品id
     * @return
     */
    public Map<String, Object> detail(Integer id){
        GoodsEntity goods = goodsMapper.selectById(id);
        List<AttributeEntity> attribute = attributeMapper.selectByGoodsId(id);

        //StockEntity stock = stockMapper.selectByGoodsId(goods.getId());
        //Integer stockNumber = stock.getStock();
        // 缓存读取库存
        StockEntity stock = (StockEntity)redisTemplate.boundHashOps(key).get(goods.getId());
        if(stock == null){
            stock = stockMapper.selectByGoodsId(goods.getId());
            redisTemplate.boundHashOps(key).put(goods.getId(), stock);
            logger.info("findAll -> 从数据库中读取放入缓存中");
        }else{
            logger.info("findAll -> 从缓存中读取");
        }

        List<CommentEntity> comments = commentMapper.selectByGoodsId(id);
        List<Map<String, Object>> commentsVo = new ArrayList<>(comments.size());
        long commentCount = comments.size();
        for(CommentEntity comment : comments){
            Map<String, Object> map = new HashMap<>();
            map.put("id", comment.getId());
            map.put("addTime", comment.getAddTime());
            map.put("content", comment.getContent());
            UserEntity user = userMapper.selectById(comment.getUserId());
            map.put("nickname", user == null ? "" : user.getNickname());
            map.put("avatar", user == null ? "" : user.getAvatar());
            map.put("picList", comment.getPicUrls());
            commentsVo.add(map);
        }
        Map<String, Object> commentList = new HashMap<>();
        commentList.put("count", commentCount);
        commentList.put("data", commentsVo);

        Map<String, Object> data = new HashMap<>();
        data.put("info", goods);
        data.put("comment", commentList);
        data.put("attribute", attribute);
        data.put("stock", stock.getStock());
        return data;
    }

}

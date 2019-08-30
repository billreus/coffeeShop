package com.example.shop.service;

import com.example.shop.mapper.*;
import com.example.shop.model.*;
import com.example.shop.util.ShopUtil;
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
* @author liu
* @date 14:48 2019/8/27
* @param
* @return
**/
@Service
public class GoodsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * redis缓存key
     */
    private final String key = "stock";
    /**
     * 商品表接口
     */
    @Resource
    private GoodsMapper goodsMapper;
    /**
     * 种类表接口
     */
    @Resource
    private CategoryMapper categoryMapper;
    /**
     * 参数表接口
     */
    @Resource
    private AttributeMapper attributeMapper;
    /**
     * 库存表接口
     */
    @Resource
    private StockMapper stockMapper;
    /**
     * 评论表接口
     */
    @Resource
    private CommentMapper commentMapper;
    /**
     * 用户表接口
     */
    @Resource
    private UserMapper userMapper;
    /**
     * 缓存接口
     */
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
    public Map category(Integer id){
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
        return ShopUtil.ok(data);
    }

    /**
     * 分类页商品列表
     * @param categoryId
     * @param page
     * @param limit
     * @return
     */
    public Map list(Integer categoryId, Integer page, Integer limit){
        List<GoodsEntity> goodsList = goodsMapper.selectByCategoryId(categoryId);
        Map<String, Object> data = new HashMap<>();
        data.put("list", goodsList);
        return ShopUtil.ok(data);
    }

    /**
     * 商品详情页
     * @param id 商品id
     * @return
     */
    public Map detail(Integer id){
        GoodsEntity goods = goodsMapper.selectById(id);
        List<AttributeEntity> attribute = attributeMapper.selectByGoodsId(id);

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
        return ShopUtil.ok(data);
    }
}

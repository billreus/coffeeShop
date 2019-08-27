package com.example.shop.util;

/**
* 维护用户token
* @author liu
* @date 15:48 2019/8/27
**/
public class UserToken {
    /**
     * 生成Token
     * @param id
     * @return
     */
    public static String generateToken(Integer id){
        JwtUtil jwtUtil = new JwtUtil();
        return jwtUtil.createToken(id);
    }

    /**
     * 获取id
     * @param token
     * @return
     */
    public static Integer getUserId(String token){
        JwtUtil jwtUtil = new JwtUtil();
        Integer userId = jwtUtil.verifyTokenAndGetUserId(token);
        if(userId == null || userId == 0){
            return null;
        }
        return userId;
    }
}

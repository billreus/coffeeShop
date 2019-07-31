package com.example.shop.util;

/**
 * 维护用户token
 */
public class UserToken {
    public static String generateToken(Integer id){
        JwtUtil jwtUtil = new JwtUtil();
        return jwtUtil.createToken(id);
    }

    public static Integer getUserId(String token){
        JwtUtil jwtUtil = new JwtUtil();
        Integer userId = jwtUtil.verifyTokenAndGetUserId(token);
        if(userId == null || userId == 0){
            return null;
        }
        return userId;
    }
}

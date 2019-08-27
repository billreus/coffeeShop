package com.example.shop.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.rmi.MarshalledObject;
import java.util.*;

/**
* JSON Web token
* @author liu
* @date 15:46 2019/8/27
**/
public class JwtUtil {
    /**
     * 密钥
     */
    private static final String SECRET = "Shop321Token123";

    /**
     * 签名生成者
     */
    private static final  String ISSUSER = "admin";

    /**
     * 签名主题
     */
    private static final String SUBJECT = "this is shop token";
    /**
     * 签名观众
     */
    static final String AUDIENCE = "APP";

    /**
     * 生成TOKEN
     * @param userId
     * @return
     */
    public String createToken(Integer userId){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String, Object> map = new HashMap<>();
            Date nowDate = new Date();
            // 过期时间：2小时
            Date expireDate = getAfterDate(nowDate,0,0,0,2,0,0);
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            String token = JWT.create()
                    // 设置头部信息 Header
                    .withHeader(map)
                    // 设置 载荷 Payload
                    .withClaim("userId", userId)
                    .withIssuer(ISSUSER)
                    .withSubject(SUBJECT)
                    .withAudience(AUDIENCE)
                    // 生成签名的时间
                    .withIssuedAt(nowDate)
                    // 签名过期的时间
                    .withExpiresAt(expireDate)
                    // 签名 Signature
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 校验token获取id
     * @param token
     * @return
     */
    public Integer verifyTokenAndGetUserId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUSER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("userId");
            return claim.asInt();
        } catch (JWTVerificationException exception){

        }

        return 0;
    }

    /**
     * 时间设置
     * @param date
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public  Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second){
        if(date == null){
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if(year != 0){
            cal.add(Calendar.YEAR, year);
        }
        if(month != 0){
            cal.add(Calendar.MONTH, month);
        }
        if(day != 0){
            cal.add(Calendar.DATE, day);
        }
        if(hour != 0){
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if(minute != 0){
            cal.add(Calendar.MINUTE, minute);
        }
        if(second != 0){
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }
}

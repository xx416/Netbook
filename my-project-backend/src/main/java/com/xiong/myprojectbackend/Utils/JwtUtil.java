package com.xiong.myprojectbackend.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 10371
 * @Description
 * @create 2023/9/26 14:51:18
 */

@Component
public class JwtUtil {
    @Value("${spring.security.jwt.key}")
    String signature;//密钥

    @Value("${spring.security.jwt.expire}")
    Integer expire;//过期时间

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /***
     * 生成token header.payload.signature
     */
    public String createToken(UserDetails details, int id, String username) {
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        builder
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("id",id)
                .withClaim("name",username)
                .withClaim("authorities",details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        String token = builder.withExpiresAt(expireTime())//指定令牌的过期时间
                .sign(Algorithm.HMAC256(signature));//签发算法

        return token;
    }

     public boolean invalidateJwt(String headerToken){
        String token = this.conVerifyToken(headerToken);
        if (token == null) return false;
        Algorithm algorithm = Algorithm.HMAC256(signature);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try{
            DecodedJWT jwt = jwtVerifier.verify(token);
            String id = jwt.getId();
            return deleteToken(id,jwt.getExpiresAt());
        } catch (JWTVerificationException e) {
            return false;
        }
     }

    private boolean deleteToken(String uuid, Date time){
        if (this.isInvalidToken(uuid)) return false;
        Date now = new Date();
        long expire = Math.max(time.getTime() - now.getTime(),0);
        stringRedisTemplate.opsForValue().set(Const.JWT_BLACK_LIST + uuid, "", expire, TimeUnit.MILLISECONDS);
        return true;
    }

    private boolean isInvalidToken(String uuid){
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(Const.JWT_BLACK_LIST + uuid));
    }

    public Date expireTime(){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, expire); //设置过期时间7天
        return instance.getTime();
    }

    /***
     * 验证token
     */
    public String conVerifyToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("Bearer ")){
            return null;
        }
        return headerToken.substring(7);
    }

    /***
     * 获取token信息
     * 这个方法可用可不用
     */
    public DecodedJWT getTokenInfo(String headerToken) {
        String token = this.conVerifyToken(headerToken);
        if (token == null) return null;
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(signature)).build();
        try{
            DecodedJWT verify = jwtVerifier.verify(token);
            if (this.isInvalidToken(verify.getId())) return null;
            Date expiresAt = verify.getExpiresAt();
            return new Date().after(expiresAt) ? null : verify;
        }catch (JWTVerificationException e){
            return null;
        }
    }

    public Integer toId(DecodedJWT jwt){
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("id").asInt();
    }

    public String toName(DecodedJWT jwt){
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("name").asString();
    }

    public UserDetails toUser(DecodedJWT jwt){
        Map<String, Claim> claims = jwt.getClaims();
        return User
                .withUsername(claims.get("name").asString())
                .password("*****")
                .authorities(claims.get("authorities").asArray(String.class))
                .build();
    }

}
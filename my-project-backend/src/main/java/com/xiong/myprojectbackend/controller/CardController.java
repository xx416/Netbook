package com.xiong.myprojectbackend.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiong.myprojectbackend.Utils.JwtUtil;
import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.dto.DbBook;
import com.xiong.myprojectbackend.entity.dto.DbCard;
import com.xiong.myprojectbackend.service.DbBookService;
import com.xiong.myprojectbackend.service.DbCardService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author 10371
 * @Description
 * @create 2023/10/20 17:02:05
 */
@RestController
@RequestMapping("/api/card")
public class CardController {
    @Resource
    DbCardService dbCardService;

    @Resource
    DbBookService dbBookService;

    @Resource
    JwtUtil jwtUtil;

    @RequestMapping("/getCard")
    public RestBean<Object> getCard(HttpServletRequest request) throws Exception {
        DecodedJWT jwt = jwtUtil.getTokenInfo(request.getHeader("Authorization"));//获取JWT信息
        Map<String, Claim> claims = jwt.getClaims();
        int user_id = claims.get("id").asInt();//获取用户id
        DbCard dbCard = dbCardService.getListByUid(user_id);
        String s = dbCard.getCard().replaceAll("=", ":");
        Object parse = JSON.parse(s);
        return RestBean.success(parse);
    }

    @RequestMapping("/getCardList")
    public RestBean<Object> getCardList(@RequestBody String ids){
        List<DbBook> dbBooks = dbBookService.getBookByIds(ids);
        if (dbBooks.size() > 0)return RestBean.success(dbBooks);
        return RestBean.failure(400,"查询失败");
    }


    @RequestMapping("/save")
    public RestBean<Void> save(@RequestBody List<Map<String, Integer>> data){
        //使用ServletRequestAttributes请求上下文获取方法更多
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        DecodedJWT jwt = jwtUtil.getTokenInfo(request.getHeader("Authorization"));//获取JWT信息
        Map<String, Claim> claims = jwt.getClaims();
        int user_id = claims.get("id").asInt();//获取用户id
        String jsonString = JSONArray.toJSONString(data);
        return dbCardService.updateByUid(user_id, jsonString) ? RestBean.success() : RestBean.failure(400,"保存失败");
    }

}

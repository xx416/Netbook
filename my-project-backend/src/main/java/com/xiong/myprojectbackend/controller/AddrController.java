package com.xiong.myprojectbackend.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiong.myprojectbackend.Utils.JwtUtil;
import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.po.DbAddr;
import com.xiong.myprojectbackend.service.DbAddrService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 10371
 * @Description
 * @create 2023/10/20 17:02:05
 */
@RestController
@RequestMapping("/api/addr")
public class AddrController {
    @Resource
    DbAddrService dbAddrService;

    @Resource
    JwtUtil jwtUtil;

    @RequestMapping("/getList")
    public RestBean<Object> getList(){
        List<DbAddr> dbAddrs = dbAddrService.list();
        if (dbAddrs.size() > 0)return RestBean.success(dbAddrs);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getListByUId")//根据当前登录的用户查询用户多少个地址
    public RestBean<Object> getListByUId(HttpServletRequest request){
        DecodedJWT jwt = jwtUtil.getTokenInfo(request.getHeader("Authorization"));
        List<DbAddr> dbAddr = dbAddrService.getListByUid(jwtUtil.toId(jwt));
        if (dbAddr.size() > 0)return RestBean.success(dbAddr);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/save")
    public RestBean<Void> save(@RequestBody DbAddr dbAddr, HttpServletRequest request){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        DecodedJWT jwt = jwtUtil.getTokenInfo(request.getHeader("Authorization"));
        dbAddr.setUserId(jwtUtil.toId(jwt));
        dbAddr.setAddTime(format);
        return dbAddrService.save(dbAddr) ? RestBean.success() : RestBean.failure(400,"保存失败");
    }

    @RequestMapping("/removeById")
    public RestBean<Void> removeById(int id){
        return dbAddrService.removeById(id) ? RestBean.success() : RestBean.failure(400,"删除失败");
    }

    @RequestMapping("/updateAddrById")
    public RestBean<Void> updateAddrById(int id,String addr){
        return dbAddrService.updateAddrById(id,addr) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

}

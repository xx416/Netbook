package com.xiong.myprojectbackend.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiong.myprojectbackend.Utils.JwtUtil;
import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.po.*;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbAddrService;
import com.xiong.myprojectbackend.service.DbOrderService;
import com.xiong.myprojectbackend.service.DbUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 10371
 * @Description
 * @create 2023/10/20 01:52:24
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    DbOrderService dbOrderService;

    @Resource
    DbAddrService dbAddrService;
    @Resource
    DbUserService dbUserService;

    @Resource
    JwtUtil jwtUtil;

    @RequestMapping("/getList")
    public RestBean<Object> getList(){
        List<DbOrder> dbOrders = dbOrderService.list();
        if (dbOrders.size() > 0)return RestBean.success(dbOrders);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getListByUid")
    public RestBean<Object> getListByUid(HttpServletRequest request){
        DecodedJWT jwt = jwtUtil.getTokenInfo(request.getHeader("Authorization"));
        List<DbOrder> dbOrders = dbOrderService.getListByUid(jwtUtil.toId(jwt));
        if (dbOrders.size() > 0)return RestBean.success(dbOrders);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPageList")
    public RestBean<Object> getPageList(SelectPageVO selectPageVO){
        List<DbOrder> dbOrders = dbOrderService.getPageList(selectPageVO);
        if (dbOrders.size() > 0)return RestBean.success(dbOrders);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPagination")
    public RestBean<Object> getPagination(@RequestParam("pageSize") Integer pageSize){
        PaginationVO pagination = dbOrderService.getPagination(pageSize);
        if (pagination.getPageCount() > 0)return RestBean.success(pagination);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getOrderByUser")
    public RestBean<Object> getOrderByUser(@RequestParam("username") String username){
        DbUser user = dbUserService.getOne(new QueryWrapper<DbUser>().eq("username",username).or().eq("email",username).or().eq("user_id", username));
        if (user.getUserId() > 0)return RestBean.success(user);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/confirmOrder")
    public RestBean<Void> confirmOrder(@RequestBody DbOrder dbOrder,HttpServletRequest request) throws JsonProcessingException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        DecodedJWT jwt = jwtUtil.getTokenInfo(request.getHeader("Authorization"));
        QueryWrapper<DbAddr> wrapper = new QueryWrapper<>();
        wrapper.eq("addr",dbOrder.getAddress());
        DbAddr one = dbAddrService.getOne(wrapper);
        //计算总金额
        double total = dbOrder.getNum() * dbOrder.getPrice();
        dbOrder.setTotal(total);
        dbOrder.setOrderTime(format);
        dbOrder.setPhone(one.getPhone());
        dbOrder.setAddressee(one.getAddressee());
        dbOrder.setUserId(jwtUtil.toId(jwt));
        return dbOrderService.confirmOrder(dbOrder) ? RestBean.success() : RestBean.failure(400,"下单成功");
    }

    @RequestMapping("/removeById")
    public RestBean<Void> removeById(@RequestParam("id") int id){
        return dbOrderService.removeById(id) ? RestBean.success() : RestBean.failure(400,"删除失败");
    }

    @RequestMapping("/update")
    public RestBean<Void> update(@RequestBody DbOrder dbOrder){
        return dbOrderService.updateById(dbOrder) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/updateById")
    public RestBean<Void> updateById(int id, String column, String setValue){
        return dbOrderService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/updateByIdInt")
    public RestBean<Void> updateByIdInt(int id, String column, int setValue){
        return dbOrderService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

}
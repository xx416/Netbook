package com.xiong.myprojectbackend.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiong.myprojectbackend.Utils.JwtUtil;
import com.xiong.myprojectbackend.Utils.TimeUtils;
import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.dto.DbTag;
import com.xiong.myprojectbackend.entity.dto.DbUser;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 10371
 * @Description
 * @create 2023/10/21 17:15:28
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    DbUserService dbUserService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    TimeUtils timeUtils;

    @Resource
    JwtUtil jwtUtil;

    @RequestMapping("/getList")
    public RestBean<List<DbUser>> getList(){
        List<DbUser> dbUsers = dbUserService.list();
        if (dbUsers.size() > 0) return RestBean.success(dbUsers);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getListById")
    public RestBean<String> getList(@RequestParam("id") int id){
        DbUser dbUser = dbUserService.getById(id);
        if (dbUser.getUserId() > 0) return RestBean.success(dbUser.getUsername());
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getUserByToken")
    public RestBean<Object> getUserByToken(HttpServletRequest request){
        DecodedJWT jwt = jwtUtil.getTokenInfo(request.getHeader("Authorization"));//获取JWT信息
        DbUser dbUser = dbUserService.getById(jwtUtil.toId(jwt));
        if (dbUser.getUserId() > 0) return RestBean.success(dbUser);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPageList")
    public RestBean<Object> getPageList(SelectPageVO selectPageVO){
        List<DbUser> dbUsers = dbUserService.getPageList(selectPageVO);
        if (dbUsers.size() > 0)return RestBean.success(dbUsers);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPagination")
    public RestBean<Object> getPagination(@RequestParam("pageSize") Integer pageSize){
        PaginationVO pagination = dbUserService.getPagination(pageSize);
        if (pagination.getPageCount() > 0)return RestBean.success(pagination);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/save")
    public RestBean<Void> save(DbUser dbUser){
        dbUser.setRegtime(timeUtils.toDay());
        return dbUserService.save(dbUser) ? RestBean.success() : RestBean.failure(400,"保存失败");
    }

    @RequestMapping("/update")
    public RestBean<Void> update(@RequestBody DbUser dbUser){
        if (dbUser.getPassword().equals(dbUserService.getById(dbUser.getUserId()).getPassword())){//判断密码是否相同不相同就重新加密
            return dbUserService.updateById(dbUser) ? RestBean.success() : RestBean.failure(400,"保存失败");
        }
        dbUser.setPassword(passwordEncoder.encode(dbUser.getPassword()));
        return dbUserService.updateById(dbUser) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/removeById")
    public RestBean<Void> remove(int id){
        return dbUserService.removeById(id) ? RestBean.success() : RestBean.failure(400,"删除失败");
    }

    @RequestMapping("/updateById")
    public RestBean<Void> updateById(@RequestParam("id") int id,@RequestParam("column") String column,@RequestParam("value") String setValue){
        return dbUserService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/updateByIdInt")
    public RestBean<Void> updateByIdInt(@RequestParam("id") int id,@RequestParam("column") String column,@RequestParam("value") int setValue){
        return dbUserService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

}

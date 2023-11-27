package com.xiong.myprojectbackend.controller;

import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.dto.DbNews;
import com.xiong.myprojectbackend.entity.dto.DbTag;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbTagService;
import com.xiong.myprojectbackend.service.impl.DbTagServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 10371
 * @Description
 * @create 2023/10/19 00:49:04
 */
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Resource
    DbTagService dbTagService;

    @RequestMapping("/getList")
    public RestBean<Object> getList(){
        List<DbTag> dbTags = dbTagService.list();
        if (dbTags.size() > 0) return RestBean.success(dbTags);
        return RestBean.failure(400,"请求失败");
    }

    @RequestMapping("/getListById")
    public RestBean<Object> getListById(@RequestBody int id){
        DbTag byId = dbTagService.getById(id);
        if (byId.getTagId() > 0) return RestBean.success(byId);
        return RestBean.failure(400,"请求失败");
    }

    @RequestMapping("/getPageList")
    public RestBean<Object> getPageList(SelectPageVO selectPageVO){
        List<DbTag> dbTags = dbTagService.getPageList(selectPageVO);
        if (dbTags.size() > 0)return RestBean.success(dbTags);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPagination")
    public RestBean<Object> getPagination(@RequestParam("pageSize") Integer pageSize){
        PaginationVO pagination = dbTagService.getPagination(pageSize);
        if (pagination.getPageCount() > 0)return RestBean.success(pagination);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/update")
    public RestBean<Void> update(@RequestBody DbTag dbTag){
        return dbTagService.updateById(dbTag) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/save")
    public RestBean<Void> save(DbTag dbTag){
        return dbTagService.save(dbTag) ? RestBean.success():RestBean.failure(400,"请求失败");
    }

    @RequestMapping("/removeById")
    public RestBean<Void> remove(@RequestParam("id") int id){
        return dbTagService.removeById(id) ? RestBean.success() : RestBean.failure(400,"删除失败");
    }

    @RequestMapping("/updateById")
    public RestBean<Void> updateById(DbTag dbTag){
        return dbTagService.updateById(dbTag) ? RestBean.success():RestBean.failure(400,"请求失败");
    }

}

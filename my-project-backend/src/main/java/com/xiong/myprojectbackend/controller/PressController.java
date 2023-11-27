package com.xiong.myprojectbackend.controller;

import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.dto.DbOrder;
import com.xiong.myprojectbackend.entity.dto.DbPress;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbPressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 10371
 * @Description
 * @create 2023/10/21 17:41:07
 */
@RestController
@RequestMapping("/api/press")
public class PressController {

    @Resource
    DbPressService dbPressService;

    @RequestMapping("/getList")
    public RestBean<List<DbPress>> getList(){
        List<DbPress> list = dbPressService.list();
        if (list.size() > 0) return RestBean.success(list);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPageList")
    public RestBean<Object> getPageList(SelectPageVO selectPageVO){
        List<DbPress> dbPresses = dbPressService.getPageList(selectPageVO);
        if (dbPresses.size() > 0)return RestBean.success(dbPresses);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPagination")
    public RestBean<Object> getPagination(@RequestParam("pageSize") Integer pageSize){
        PaginationVO pagination = dbPressService.getPagination(pageSize);
        if (pagination.getPageCount() > 0)return RestBean.success(pagination);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/update")
    public RestBean<Void> update(@RequestBody DbPress dbPress){
        return dbPressService.updateById(dbPress) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/save")
    public RestBean<Void> save(DbPress dbPress){
        return  dbPressService.save(dbPress) ? RestBean.success() : RestBean.failure(400,"保存失败");
    }

    @RequestMapping("/removeById")
    public RestBean<Void> remove(int id){
        return dbPressService.removeById(id) ? RestBean.success() : RestBean.failure(400,"删除失败");
    }

    @RequestMapping("/updateById")
    public RestBean<Void> updateById(int id,String column,String setValue){
        return dbPressService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/updateByIdInt")
    public RestBean<Void> updateByIdInt(int id,String column,int setValue){
        return dbPressService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }
}

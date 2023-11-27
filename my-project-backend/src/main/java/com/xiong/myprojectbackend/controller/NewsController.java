package com.xiong.myprojectbackend.controller;

import com.xiong.myprojectbackend.Utils.TimeUtils;
import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.dto.DbBook;
import com.xiong.myprojectbackend.entity.dto.DbNews;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbNewsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 10371
 * @Description
 * @create 2023/10/21 17:36:52
 */
@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Resource
    DbNewsService dbNewsService;

    @Resource
    TimeUtils timeUtils;


    @RequestMapping("/getPageList")
    public RestBean<Object> getPageList(SelectPageVO selectPageVO){
        List<DbNews> dbNews = dbNewsService.getPageList(selectPageVO);
        if (dbNews.size() > 0)return RestBean.success(dbNews);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPagination")
    public RestBean<Object> getPagination(@RequestParam("pageSize") Integer pageSize){
        PaginationVO pagination = dbNewsService.getPagination(pageSize);
        if (pagination.getPageCount() > 0)return RestBean.success(pagination);
        return RestBean.failure(400,"查询失败");
    }


    @RequestMapping("/getBookByName")
    public RestBean<Object> getBookByName(@RequestParam("bookName") String bookName){
        List<DbNews> dbNews = dbNewsService.getBookByName(bookName);
        if (dbNews.size() > 0)return RestBean.success(dbNews);
        return RestBean.failure(400,"查询失败");
    }


    @RequestMapping("/getList")
    public RestBean<List<DbNews>> getList(){
        List<DbNews> dbNews = dbNewsService.list();
        if (dbNews.size() > 0) return RestBean.success(dbNews);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/save")
    public RestBean<Void> save(@RequestBody DbNews dbNews){
        dbNews.setBannerAddTime(timeUtils.toDay());
        return dbNewsService.save(dbNews) ? RestBean.success() : RestBean.failure(400,"保存失败");
    }

    @RequestMapping("/removeById")
    public RestBean<Void> remove(@RequestParam("id") int id){
        return dbNewsService.removeById(id) ? RestBean.success() : RestBean.failure(400,"删除失败");
    }

    @RequestMapping("/update")
    public RestBean<Void> update(@RequestBody DbNews dbNews){
        return dbNewsService.updateById(dbNews) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/updateById")
    public RestBean<Void> updateById(@RequestBody int id,String column,String setValue){
        return dbNewsService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/updateByIdInt")
    public RestBean<Void> updateByIdInt(@RequestBody int id,String column,int setValue){
        return dbNewsService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }
}

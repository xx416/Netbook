package com.xiong.myprojectbackend.controller;

import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.dto.DbBook;
import com.xiong.myprojectbackend.entity.dto.DbSyslog;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbSyslogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 10371
 * @Description
 * @create 2023/11/17 20:21:04
 */
@RestController
@RequestMapping("/api/log")
public class SyslogController {

    @Resource
    DbSyslogService dbSyslogService;

    @RequestMapping("/getPageList")
    public RestBean<Object> getPageList(SelectPageVO selectPageVO){
        List<DbSyslog> dbSyslogs = dbSyslogService.getPageList(selectPageVO);
        if (dbSyslogs.size() > 0)return RestBean.success(dbSyslogs);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPagination")
    public RestBean<Object> getPagination(@RequestParam("pageSize") Integer pageSize){
        PaginationVO pagination = dbSyslogService.getPagination(pageSize);
        if (pagination.getPageCount() > 0)return RestBean.success(pagination);
        return RestBean.failure(400,"查询失败");
    }
}

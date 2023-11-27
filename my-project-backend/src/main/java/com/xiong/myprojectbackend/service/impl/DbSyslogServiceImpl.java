package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.myprojectbackend.entity.dto.DbPress;
import com.xiong.myprojectbackend.entity.dto.DbSyslog;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbSyslogService;
import com.xiong.myprojectbackend.mapper.DbSyslogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_syslog】的数据库操作Service实现
* @createDate 2023-10-18 18:47:29
*/
@Service
public class DbSyslogServiceImpl extends ServiceImpl<DbSyslogMapper, DbSyslog> implements DbSyslogService {

    @Resource
    DbSyslogMapper dbSyslogMapper;

    @Override
    public List<DbSyslog> getPageList(SelectPageVO selectPageVO) {
        Page<DbSyslog> producePage = new Page<>(selectPageVO.getCurrentPage(),selectPageVO.getPageSize());
        QueryWrapper<DbSyslog> wrapper = new QueryWrapper<>();
        wrapper.like("username",selectPageVO.getTagName());
        Page<DbSyslog> page = dbSyslogMapper.selectPage(producePage,wrapper);
        System.out.println(producePage == page);
        return page.getRecords();
    }


    @Override
    public PaginationVO getPagination(int pageSize) {
        int count = Math.toIntExact(dbSyslogMapper.selectCount(new QueryWrapper<>()));
        double pageCount = (double) count / pageSize;
        if (pageCount != (int) pageCount){
            pageCount += 1;
        }
        return new PaginationVO(count,count, (int) pageCount);
    }
}





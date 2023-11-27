package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.myprojectbackend.entity.dto.DbNews;
import com.xiong.myprojectbackend.entity.dto.DbTag;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbTagService;
import com.xiong.myprojectbackend.mapper.DbTagMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_tag】的数据库操作Service实现
* @createDate 2023-10-18 18:47:29
*/
@Service
public class DbTagServiceImpl extends ServiceImpl<DbTagMapper, DbTag> implements DbTagService {

    @Resource
    DbTagMapper dbTagMapper;

    @Override
    public List<DbTag> getPageList(SelectPageVO selectPageVO) {
        Page<DbTag> producePage = new Page<>(selectPageVO.getCurrentPage(),selectPageVO.getPageSize());
        QueryWrapper<DbTag> wrapper = new QueryWrapper<>();
        if (!selectPageVO.getTagName().isEmpty()){
            wrapper.like("tag_name",selectPageVO.getTagName());
        }
        Page<DbTag> page = dbTagMapper.selectPage(producePage,wrapper);
        System.out.println(producePage == page);
        return page.getRecords();
    }


    @Override
    public PaginationVO getPagination(int pageSize) {
        int count = Math.toIntExact(dbTagMapper.selectCount(new QueryWrapper<>()));
        double pageCount = (double) count / pageSize;
        if (pageCount != (int) pageCount){
            pageCount += 1;
        }
        return new PaginationVO(count,count, (int) pageCount);
    }
}





package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.myprojectbackend.entity.po.DbNews;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbNewsService;
import com.xiong.myprojectbackend.mapper.DbNewsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_news】的数据库操作Service实现
* @createDate 2023-10-18 18:47:28
*/
@Service
public class DbNewsServiceImpl extends ServiceImpl<DbNewsMapper, DbNews> implements DbNewsService {

    @Resource
    DbNewsMapper dbNewsMapper;

    @Override
    public List<DbNews> getPageList(SelectPageVO selectPageVO) {
        Page<DbNews> producePage = new Page<>(selectPageVO.getCurrentPage(),selectPageVO.getPageSize());
        Page<DbNews> page = dbNewsMapper.selectPage(producePage,new QueryWrapper<>());
        System.out.println(producePage == page);
        return page.getRecords();
    }


    @Override
    public PaginationVO getPagination(int pageSize) {
        int count = Math.toIntExact(dbNewsMapper.selectCount(new QueryWrapper<>()));
        double pageCount = (double) count / pageSize;
        if (pageCount != (int) pageCount){
            pageCount += 1;
        }
        return new PaginationVO(count,count, (int) pageCount);
    }

    @Override
    public List<DbNews> getBookByName(String bookName) {
        QueryWrapper<DbNews> wrapper = new QueryWrapper<>();
        wrapper
                .like("book_name",bookName);
        return dbNewsMapper.selectList(wrapper);
    }

    @Override
    public boolean updateById(int id, String column, String setValue) {
        UpdateWrapper<DbNews> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("news_id",id)
                .set(column,setValue);
        int update = dbNewsMapper.update(new DbNews(), wrapper);
        if (update>0){return true;}
        return false;
    }

    @Override
    public boolean updateById(int id, String column, int setValue) {
        UpdateWrapper<DbNews> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("news_id",id)
                .set(column,setValue);
        int update = dbNewsMapper.update(new DbNews(), wrapper);
        if (update>0){return true;}
        return false;
    }

}





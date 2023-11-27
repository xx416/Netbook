package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.myprojectbackend.entity.po.DbBook;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbBookService;
import com.xiong.myprojectbackend.mapper.DbBookMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_book】的数据库操作Service实现
* @createDate 2023-10-18 18:47:28
*/
@Service
public class DbBookServiceImpl extends ServiceImpl<DbBookMapper, DbBook> implements DbBookService {

    @Resource
    DbBookMapper dbBookMapper;

    @Override
    public List<DbBook> getPageList(SelectPageVO selectPageVO) {
        Page<DbBook> producePage = new Page<>(selectPageVO.getCurrentPage(),selectPageVO.getPageSize());
        QueryWrapper<DbBook> wrapper = new QueryWrapper<>();
        if (!selectPageVO.getTagName().isEmpty()){
            wrapper.like("tag_name",selectPageVO.getTagName());
        }
        Page<DbBook> page = dbBookMapper.selectPage(producePage,wrapper);
        System.out.println(producePage == page);
        return page.getRecords();
    }

    @Override
    public List<DbBook> getTagList(String TagName) {
        QueryWrapper<DbBook> wrapper = new QueryWrapper<>();
        wrapper
                .like("tag_name",TagName);
        return dbBookMapper.selectList(wrapper);
    }

    @Override
    public List<DbBook> getBookByName(String bookName) {
        QueryWrapper<DbBook> wrapper = new QueryWrapper<>();
        wrapper
                .like("book_name",bookName);
        return dbBookMapper.selectList(wrapper);
    }

    @Override
    public PaginationVO getPagination(int pageSize) {
        int count = Math.toIntExact(dbBookMapper.selectCount(new QueryWrapper<>()));
        double pageCount = (double) count / pageSize;
        if (pageCount != (int) pageCount){
            pageCount += 1;
        }
        return new PaginationVO(count,count, (int) pageCount);
    }

    @Override
    public List<DbBook> getStock() {
        QueryWrapper<DbBook> wrapper = new QueryWrapper<>();
        wrapper
                .select("book_id","stock");
        return dbBookMapper.selectList(wrapper);
    }

    @Override
    public List<DbBook> getBookByIds(String ids) {
        String[] list = ids.substring(1,ids.length()-1).split(",");
        QueryWrapper<DbBook> wrapper = new QueryWrapper<>();
        wrapper
                .in("book_id",list);
        return dbBookMapper.selectList(wrapper);
    }

    @Override
    public boolean updateById(int id, String column, String setValue) {
        UpdateWrapper<DbBook> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("book_id",id)
                .set(column,setValue);
        int update = dbBookMapper.update(new DbBook(), wrapper);
        if (update>0){return true;}
        return false;
    }

    @Override
    public boolean updateById(int id, String column, int setValue) {
        UpdateWrapper<DbBook> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("book_id",id)
                .set(column,setValue);
        int update = dbBookMapper.update(new DbBook(), wrapper);
        if (update>0){return true;}
        return false;
    }
}





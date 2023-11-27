package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.myprojectbackend.entity.po.DbPress;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbPressService;
import com.xiong.myprojectbackend.mapper.DbPressMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_press】的数据库操作Service实现
* @createDate 2023-10-18 18:47:29
*/
@Service
public class DbPressServiceImpl extends ServiceImpl<DbPressMapper, DbPress> implements DbPressService {

    @Resource
    DbPressMapper dbPressMapper;

    @Override
    public List<DbPress> getPageList(SelectPageVO selectPageVO) {
        Page<DbPress> producePage = new Page<>(selectPageVO.getCurrentPage(),selectPageVO.getPageSize());
        QueryWrapper<DbPress> wrapper = new QueryWrapper<>();
        if (!selectPageVO.getTagName().isEmpty()){
            wrapper.like("press_name",selectPageVO.getTagName());
        }
        Page<DbPress> page = dbPressMapper.selectPage(producePage,wrapper);
        System.out.println(producePage == page);
        return page.getRecords();
    }


    @Override
    public PaginationVO getPagination(int pageSize) {
        int count = Math.toIntExact(dbPressMapper.selectCount(new QueryWrapper<>()));
        double pageCount = (double) count / pageSize;
        if (pageCount != (int) pageCount){
            pageCount += 1;
        }
        return new PaginationVO(count,count, (int) pageCount);
    }

    @Override
    public boolean updateById(int id, String column, String setValue) {
        UpdateWrapper<DbPress> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("press_id",id)
                .set(column,setValue);
        int update = dbPressMapper.update(new DbPress(), wrapper);
        if (update>0){return true;}
        return false;
    }

    @Override
    public boolean updateById(int id, String column, int setValue) {
        UpdateWrapper<DbPress> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("press_id",id)
                .set(column,setValue);
        int update = dbPressMapper.update(new DbPress(), wrapper);
        if (update>0){return true;}
        return false;
    }
}





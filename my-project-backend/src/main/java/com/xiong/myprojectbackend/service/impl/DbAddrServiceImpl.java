package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.myprojectbackend.entity.po.DbAddr;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.mapper.DbAddrMapper;
import com.xiong.myprojectbackend.service.DbAddrService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_addr】的数据库操作Service实现
* @createDate 2023-10-20 16:54:16
*/
@Service
public class DbAddrServiceImpl extends ServiceImpl<DbAddrMapper, DbAddr> implements DbAddrService {

    @Resource
    DbAddrMapper dbAddrMapper;

    @Override
    public List<DbAddr> getPageList(SelectPageVO selectPageVO) {
        Page<DbAddr> producePage = new Page<>(selectPageVO.getCurrentPage(),selectPageVO.getPageSize());
        QueryWrapper<DbAddr> wrapper = new QueryWrapper<>();
        if (!selectPageVO.getTagName().isEmpty()){
            wrapper.like("addressee",selectPageVO.getTagName());
        }
        Page<DbAddr> page = dbAddrMapper.selectPage(producePage,wrapper);
        System.out.println(producePage == page);
        return page.getRecords();
    }


    @Override
    public PaginationVO getPagination(int pageSize) {
        int count = Math.toIntExact(dbAddrMapper.selectCount(new QueryWrapper<>()));
        double pageCount = (double) count / pageSize;
        if (pageCount != (int) pageCount){
            pageCount += 1;
        }
        return new PaginationVO(count,count, (int) pageCount);
    }

    @Override
    public List<DbAddr>getListByUid(int id) {
        QueryWrapper<DbAddr> wrapper = new QueryWrapper<>();
        wrapper
                .eq("user_id",id);
        return dbAddrMapper.selectList(wrapper);
    }

    @Override
    public Boolean updateAddrById(int id,String addr) {
        UpdateWrapper<DbAddr> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("addr_id",id)
                .set("addr",addr);
        int update = dbAddrMapper.update(new DbAddr(), wrapper);
        if (update > 0)return true;
        return false;
    }
}





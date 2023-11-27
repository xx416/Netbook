package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.myprojectbackend.entity.po.DbCard;
import com.xiong.myprojectbackend.mapper.DbCardMapper;
import com.xiong.myprojectbackend.service.DbCardService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author 10371
* @description 针对表【db_tag】的数据库操作Service实现
* @createDate 2023-10-18 18:47:29
*/
@Service
public class DbCardServiceImpl extends ServiceImpl<DbCardMapper, DbCard> implements DbCardService {

    @Resource
    DbCardMapper dbCardMapper;

    @Override
    public DbCard getListByUid(int uid) {
        QueryWrapper<DbCard> wrapper = new QueryWrapper<>();
        wrapper
                .eq("user_id",uid);
        DbCard dbCard = dbCardMapper.selectOne(wrapper);
        return dbCard;
    }

    @Override
    public boolean updateByUid(int id, String card) {
        UpdateWrapper<DbCard> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("user_id",id)
                .set("card",card);
        int update = dbCardMapper.update(new DbCard(), wrapper);
        return update > 0;
    }
}





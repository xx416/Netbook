package com.xiong.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.myprojectbackend.entity.po.DbCard;

/**
* @author 10371
* @description 针对表【db_tag】的数据库操作Service
* @createDate 2023-10-18 18:47:29
*/
public interface DbCardService extends IService<DbCard> {
    public DbCard getListByUid(int uid);
    public boolean updateByUid(int id, String card);

}

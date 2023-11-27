package com.xiong.myprojectbackend.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.xiong.myprojectbackend.entity.po.DbOrder;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_order】的数据库操作Mapper
* @createDate 2023-10-18 18:47:28
*
*/
public interface DbOrderMapper extends MPJBaseMapper<DbOrder> {
    @Select("SELECT order_id,o.user_id,book_id,num,order_time,o.username,phone,address,ispay,u.username as user FROM db_order o INNER JOIN db_user u on o.user_id = u.user_id")
    public List<DbOrder> getUser();
}





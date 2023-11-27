package com.xiong.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiong.myprojectbackend.entity.dto.DbNews;
import com.xiong.myprojectbackend.entity.dto.DbOrder;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_order】的数据库操作Service
* @createDate 2023-10-18 18:47:28
*/
public interface DbOrderService extends IService<DbOrder> {

    boolean confirmOrder(DbOrder dbOrder) throws JsonProcessingException;

    List<DbOrder> getListByUid(int uid);
    List<DbOrder> getPageList(SelectPageVO selectPageVO);
    PaginationVO getPagination(int pageSize);
    boolean updateById(int id, String column, String setValue);

    boolean updateById(int id, String column, int setValue);
}

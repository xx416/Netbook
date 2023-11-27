package com.xiong.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.myprojectbackend.entity.po.DbNews;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_news】的数据库操作Service
* @createDate 2023-10-18 18:47:28
*/
public interface DbNewsService extends IService<DbNews> {

    List<DbNews> getPageList(SelectPageVO selectPageVO);
    PaginationVO getPagination(int pageSize);
    List<DbNews> getBookByName(String bookName);
    boolean updateById(int id, String column, String setValue);
    boolean updateById(int id, String column, int setValue);
}

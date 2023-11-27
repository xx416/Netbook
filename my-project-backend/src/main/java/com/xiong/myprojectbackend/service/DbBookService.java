package com.xiong.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.myprojectbackend.entity.dto.DbBook;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_book】的数据库操作Service
* @createDate 2023-10-18 18:47:28
*/
public interface DbBookService extends IService<DbBook> {

    List<DbBook> getPageList(SelectPageVO selectPageVO);
    List<DbBook> getTagList(String tagName);
    List<DbBook> getBookByName(String bookName);
    PaginationVO getPagination(int pageSize);
    List<DbBook> getStock();
    List<DbBook> getBookByIds(String ids);
    boolean updateById(int id, String column, String setValue);
    boolean updateById(int id, String column, int setValue);
}

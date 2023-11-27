package com.xiong.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.myprojectbackend.entity.po.DbTag;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_tag】的数据库操作Service
* @createDate 2023-10-18 18:47:29
*/
public interface DbTagService extends IService<DbTag> {

    List<DbTag> getPageList(SelectPageVO selectPageVO);
    PaginationVO getPagination(int pageSize);

}

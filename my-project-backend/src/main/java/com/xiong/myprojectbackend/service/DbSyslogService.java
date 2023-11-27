package com.xiong.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.myprojectbackend.entity.dto.DbPress;
import com.xiong.myprojectbackend.entity.dto.DbSyslog;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_syslog】的数据库操作Service
* @createDate 2023-10-18 18:47:29
*/
public interface DbSyslogService extends IService<DbSyslog> {
    List<DbSyslog> getPageList(SelectPageVO selectPageVO);
    PaginationVO getPagination(int pageSize);
}

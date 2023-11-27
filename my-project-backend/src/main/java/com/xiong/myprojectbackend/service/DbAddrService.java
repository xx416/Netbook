package com.xiong.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.myprojectbackend.entity.dto.DbAddr;
import com.xiong.myprojectbackend.entity.dto.DbOrder;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;

import java.util.List;

/**
* @author 10371
* @description 针对表【db_addr】的数据库操作Service
* @createDate 2023-10-20 16:54:16
*/
public interface DbAddrService extends IService<DbAddr> {
    public List<DbAddr> getListByUid(int id);
    public Boolean updateAddrById(int id,String addr);
    List<DbAddr> getPageList(SelectPageVO selectPageVO);
    PaginationVO getPagination(int pageSize);
}

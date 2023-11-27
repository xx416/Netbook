package com.xiong.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.myprojectbackend.entity.dto.DbNews;
import com.xiong.myprojectbackend.entity.dto.DbUser;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.ConfirmResetVO;
import com.xiong.myprojectbackend.entity.vo.resquest.EmailRegisterVO;
import com.xiong.myprojectbackend.entity.vo.resquest.EmailResetVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


/**
* @author 10371
* @description 针对表【db_user】的数据库操作Service
* @createDate 2023-10-18 18:47:29
*/
public interface DbUserService extends IService<DbUser>, UserDetailsService {
    public DbUser findAccountByNameOrEmail(String data);
    public String registerEmailVerifCode(String type, String email, String ip);
    public String registerEmailAccount(EmailRegisterVO emailVo);
    public String resetConfirm(ConfirmResetVO confirmResetVO);
    public String resetEmailAccountPassword(EmailResetVO emailResetVO);
    List<DbUser> getPageList(SelectPageVO selectPageVO);
    PaginationVO getPagination(int pageSize);
    public boolean updateById(int id, String column, String setValue);
    public boolean updateById(int id, String column, int setValue);

}

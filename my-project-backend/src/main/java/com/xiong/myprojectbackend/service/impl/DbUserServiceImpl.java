package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.myprojectbackend.Utils.Const;
import com.xiong.myprojectbackend.Utils.FlowUtils;
import com.xiong.myprojectbackend.Utils.TimeUtils;
import com.xiong.myprojectbackend.entity.dto.*;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.ConfirmResetVO;
import com.xiong.myprojectbackend.entity.vo.resquest.EmailRegisterVO;
import com.xiong.myprojectbackend.entity.vo.resquest.EmailResetVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.mapper.DbCardMapper;
import com.xiong.myprojectbackend.service.DbUserService;
import com.xiong.myprojectbackend.mapper.DbUserMapper;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* @author 10371
* @description 针对表【db_user】的数据库操作Service实现
* @createDate 2023-10-18 18:47:29
*/
@Service
public class DbUserServiceImpl extends ServiceImpl<DbUserMapper, DbUser> implements DbUserService {

    @Value("${spring.redis.blockTime}")
    Integer blockTime;

    @Resource
    FlowUtils flowUtils;

    @Resource
    TimeUtils timeUtils;

    @Resource
    AmqpTemplate amqpTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    PasswordEncoder encoder;

    @Resource
    DbUserMapper dbUserMapper;

    @Resource
    DbCardMapper dbCardMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DbUser dbUser = this.findAccountByNameOrEmail(username);
        if (dbUser == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(username)
                .password(dbUser.getPassword())
                .roles(dbUser.getRole())
                .build();
    }
    @Override
    public DbUser findAccountByNameOrEmail(String data){
        return this.query()
                .eq("username",data).or()
                .eq("email",data)
                .one();
    }

    @Override
    public String registerEmailVerifCode(String type, String email, String ip) {
        synchronized (ip.intern()){
            if (verifyLimit(ip)){
                Random random = new Random();
                int code = random.nextInt(899999) + 100000;
                Map<String , Object> map = new HashMap<>();
                map.put("email",email);
                map.put("type",type);
                map.put("code",code);
                System.out.println(code);
                amqpTemplate.convertAndSend("emailExchange","emailMQ", map);
                stringRedisTemplate.opsForValue()
                        .set(Const.VERIFY_EMAIL_DATA + email, String.valueOf(code), 3, TimeUnit.MINUTES);
                return null;
            }else{
                return "请求频繁，请稍后再试";
            }
        }
    }

    @Override
    public String registerEmailAccount(EmailRegisterVO emailVo) {
        String email = emailVo.getEmail();
        String username = emailVo.getUsername();
        String code = stringRedisTemplate.opsForValue().get(Const.VERIFY_EMAIL_DATA + email);
        if (code == null) return "请先获验证码";
        if (!code.equals(emailVo.getCode())) return "验证码输入错误，请重新输入";
        if (this.existsAccountByEmail(email)) return "此电子邮件已被其他用户注册";
        if (this.existsAccountByUsername(username)) return "此用户名已被其他用户注册，请更换新的用户名";
        String password = encoder.encode(emailVo.getPassword());
        Random random = new Random();
        int uid = 0;
        boolean tf = true;
        while (tf){//重复循环出没有重复的id
            uid = random.nextInt(1000000000);// 生成0到999999999之间的随机数
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",uid);
            Long aLong = dbUserMapper.selectCount(wrapper);
            if (aLong > 0){
                System.out.println("重新生成uid中.....");
            }else {
                tf = false;
            }
        }
        DbUser dbUser = new DbUser(uid, username, password, 1, email, null, timeUtils.toDay(), "user", 1,"");
        if (this.save(dbUser)){
            dbCardMapper.insert(new DbCard(null,uid,""));
            stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + email);
            return null;
        }else{
            return "内部错误，请联系管理员";
        }
    }

    @Override
    public String resetConfirm(ConfirmResetVO confirmResetVO) {
        String email = confirmResetVO.getEmail();
        String code = stringRedisTemplate.opsForValue().get(Const.VERIFY_EMAIL_DATA + email);
        if (code == null) return "请先获取验证码";
        if (!code.equals(confirmResetVO.getCode())) return "验证码错误，请重新输入";
        return null;
    }

    @Override
    public String resetEmailAccountPassword(EmailResetVO emailResetVO) {
        String verify = this.resetConfirm(new ConfirmResetVO(emailResetVO.getEmail(),emailResetVO.getCode()));
        if (verify != null) return verify;
        String email = emailResetVO.getEmail();
        String password = encoder.encode(emailResetVO.getPassword());
        boolean update = this.update().eq("email",email).set("password",password).update();
        if (update){
            stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + email);
            return null;
        }
        return null;
    }


    @Override
    public List<DbUser> getPageList(SelectPageVO selectPageVO) {
        Page<DbUser> producePage = new Page<>(selectPageVO.getCurrentPage(),selectPageVO.getPageSize());
        QueryWrapper<DbUser> wrapper = new QueryWrapper<>();
        if (!selectPageVO.getTagName().isEmpty()){
            wrapper.like("username",selectPageVO.getTagName());
        }
        Page<DbUser> page = dbUserMapper.selectPage(producePage,wrapper);
        System.out.println(producePage == page);
        return page.getRecords();
    }


    @Override
    public PaginationVO getPagination(int pageSize) {
        int count = Math.toIntExact(dbUserMapper.selectCount(new QueryWrapper<>()));
        double pageCount = (double) count / pageSize;
        if (pageCount != (int) pageCount){
            pageCount += 1;
        }
        return new PaginationVO(count,count, (int) pageCount);
    }

    @Override
    public boolean updateById(int id, String column, String setValue) {
        UpdateWrapper<DbUser> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("user_id",id)
                .set(column,setValue);
        int update = dbUserMapper.update(new DbUser(), wrapper);
        if (update>0){return true;}
        return false;
    }

    @Override
    public boolean updateById(int id, String column, int setValue) {
        UpdateWrapper<DbUser> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("user_id",id)
                .set(column,setValue);
        int update = dbUserMapper.update(new DbUser(), wrapper);
        if (update>0){return true;}
        return false;
    }

    private boolean existsAccountByEmail(String email){
        return this.baseMapper.exists(Wrappers.<DbUser>query().eq("email",email));
    }
    private boolean existsAccountByUsername(String username){
        return this.baseMapper.exists(Wrappers.<DbUser>query().eq("username",username));
    }


    private boolean verifyLimit(String ip){
        String key = Const.VERIFY_EMAIL_LIMIT + ip;
        return flowUtils.limitOnceCheck(key, blockTime);
    }

}





package com.xiong.myprojectbackend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName db_addr
 */
@TableName(value ="db_addr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbAddr implements Serializable {
    /**
     *  地址编号
     */
    @TableId(type = IdType.AUTO)
    private Integer addrId;

    /**
     *  用户编号
     */
    @TableField("user_id")
    private Integer userId;

    /**
     *  地址信息
     */
    @TableField("addr")
    private String addr;

    /**
     *  收件人
     */
    private String addressee;

    /**
     *  手机号
     */
    private String phone;


    /**
     *  添加时间
     */
    @TableField("add_time")
    private String addTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
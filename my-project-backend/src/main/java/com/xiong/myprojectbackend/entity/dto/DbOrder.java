package com.xiong.myprojectbackend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* 
* @TableName db_order
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_order")
public class DbOrder implements Serializable {

    /**
    * 订单编号
    */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("订单编号")
    private Integer orderId;
    /**
     * 图书编号
     */
    @ApiModelProperty("图书编号")
    @TableField("book_id")
    private Integer bookId;
    /**
     * 用户编号
     */
    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Integer userId;
    /**
    * 图书名称
    */
    @NotBlank(message="[图书名称FK]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("图书名称FK")
    @Length(max= 50,message="编码长度不能超过50")
    @TableField("book_name")
    private String bookName;
    /**
    * 数量
    */
    @NotNull(message="[数量]不能为空")
    @ApiModelProperty("数量")
    private Integer num;
    /**
     * 总价
     */
    @ApiModelProperty("总价")
    private Double total;
    /**
     * 商品单价
     */
    @ApiModelProperty("单价")
    private Double price;
    /**
    * 订单生成时间
    */
    @NotNull(message="[订单生成时间]不能为空")
    @ApiModelProperty("订单生成时间")
    @TableField("order_time")
    private String orderTime;
    /**
    * 创建者
    */
    @NotBlank(message="[创建者]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("收货人")
    @Length(max= 30,message="编码长度不能超过30")
    private String addressee;
    /**
    * 手机号
    */
    @NotBlank(message="[手机号]不能为空")
    @Size(max= 13,message="编码长度不能超过13")
    @ApiModelProperty("手机号")
    @Length(max= 13,message="编码长度不能超过13")
    private String phone;

    /**
    * 地址
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("地址")
    @Length(max= 50,message="编码长度不能超过50")
    private String address;

    /**
    * 是否支付 0等待支付 1支付成功 3支付超时
    */
    @NotNull(message="[是否支付 0等待支付 1支付成功 3支付超时]不能为空")
    @ApiModelProperty("是否支付 0等待支付 1支付成功 3支付超时")
    private Integer ispay;

    /**
     * 所属用户(不参与CRUD)
     */
    @TableField(exist = false)
    private String username;

}
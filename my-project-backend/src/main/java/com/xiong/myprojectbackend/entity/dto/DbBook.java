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
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 
* @TableName db_book
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_book")
public class DbBook implements Serializable {

    /**
    * 图书编号
    */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("图书编号")
    private Integer bookId;

    /**
    * 书名
    */
    @NotBlank(message="[书名]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("书名")
    @Length(max= 50,message="编码长度不能超过50")
    @TableField("book_name")
    private String bookName;

    /**
    * 介绍
    */
    @NotBlank(message="[介绍]不能为空")
    @Size(max= 150,message="编码长度不能超过150")
    @ApiModelProperty("介绍")
    @Length(max= 150,message="编码长度不能超过150")
    @TableField("book_info")
    private String bookInfo;

    /**
     * 封面
     */
    @ApiModelProperty("封面")
    @TableField("image")
    private String image;

    /**
     * 单价
     */
    @ApiModelProperty("单价")
    private Double price;

    /**
    * 作者
    */
    @NotBlank(message="[作者]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("作者")
    @Length(max= 30,message="编码长度不能超过30")
    private String author;

    /**
    * 销量
    */
    @NotBlank(message="[销量]不能为空")
    @Size(max= 10,message="编码长度不能超过30")
    @ApiModelProperty("销量")
    @Length(max= 10,message="编码长度不能超过30")
    private Integer sales;

    /**
    * 库存
    */
    @NotBlank(message="[库存]不能为空")
    @Size(max= 10,message="编码长度不能超过30")
    @ApiModelProperty("库存")
    @Length(max= 10,message="编码长度不能超过30")
    private Integer stock;

    /**
    * 标签FK
    */
    @ApiModelProperty("标签")
    @TableField("tag_name")
    private String tagName;

    /**
    * 出版社FK
    */
    @ApiModelProperty("出版社FK")
    @TableField("press_name")
    private String pressName;

    /**
    * 运费
    */
    @Size(max= 10,message="编码长度不能超过10")
    @ApiModelProperty("运费")
    @Length(max= 10,message="编码长度不能超过10")
    private Double transit;

    /**
    * 发布状态
    */
    @ApiModelProperty("发布状态")
    private Boolean state;

    /**
     * 出版时间
     */
    @ApiModelProperty("出版时间")
    @TableField("book_pub_time")
    private String bookPubTime;

    /**
    * 添加时间
    */
    @ApiModelProperty("入库时间")
    @TableField("add_Time")
    private String addTime;

    /**
    * 备注
    */
    @Size(max= 150,message="编码长度不能超过150")
    @ApiModelProperty("备注")
    @Length(max= 150,message="编码长度不能超过150")
    private String remark;

}

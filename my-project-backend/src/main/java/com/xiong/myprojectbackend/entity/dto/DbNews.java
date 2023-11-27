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
import java.util.Date;

/**
* 
* @TableName db_banner
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_news")
public class DbNews implements Serializable {

    /**
    * 推荐信息id
    */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("推荐信息id")
    private Integer bannerId;
    /**
    /**
    * 推荐信息图片
    */
    @NotBlank(message="[推荐信息图片]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("推荐信息图片")
    @Length(max= 255,message="编码长度不能超过255")
    @TableField("banner_src")
    private String bannerSrc;
    /**
    * 推荐状态
    */
    @NotNull(message="[推荐状态]不能为空")
    @ApiModelProperty("推荐状态")
    @TableField("banner_state")
    private Boolean bannerState;
    /**
    * 推荐时间
    */
    @ApiModelProperty("推荐时间")
    @TableField("banner_add_time")
    private String bannerAddTime;
    /**
     * 推荐状态
     */
    @NotNull(message="[图书编号]不能为空")
    @ApiModelProperty("图书编号")
    @TableField("book_name")
    private String bookName;



}

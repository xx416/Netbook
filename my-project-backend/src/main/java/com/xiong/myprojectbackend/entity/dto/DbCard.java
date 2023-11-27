package com.xiong.myprojectbackend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.lang.reflect.Type;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
* 
* @TableName db_card
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_card")
public class DbCard implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    @TableId(type = IdType.AUTO)
    private Integer cardId;
    /**
    * 
    */
    @ApiModelProperty("")
    @TableField("user_id")
    private Integer userId;
    /**
    * 
    */
    @ApiModelProperty("")
    @TableField("card")
    private String card;

}

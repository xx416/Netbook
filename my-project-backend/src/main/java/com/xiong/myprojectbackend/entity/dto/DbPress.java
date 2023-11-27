package com.xiong.myprojectbackend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
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

/**
* 
* @TableName db_press
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_press")
public class DbPress implements Serializable {

    /**
    * 出版社编号
    */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("出版社编号")
    private Integer pressId;
    /**
    * 出版社名称
    */
    @NotBlank(message="[出版社名称]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("出版社名称")
    @Length(max= 100,message="编码长度不能超过100")
    private String pressName;
    /**
    * 备注
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("备注")
    @Length(max= 100,message="编码长度不能超过100")
    private String remark;

}

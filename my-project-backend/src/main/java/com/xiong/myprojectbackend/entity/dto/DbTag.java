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
* @TableName db_tag
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_tag")
public class DbTag implements Serializable {

    /**
    * 标签编号
    */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("标签编号")
    private Integer tagId;
    /**
    * 标签名字
    */
    @NotBlank(message="[标签名字]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("标签名字")
    @Length(max= 50,message="编码长度不能超过50")
    private String tagName;
    /**
    * 备注
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("备注")
    @Length(max= 100,message="编码长度不能超过100")
    private String remark;
}

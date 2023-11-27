package com.xiong.myprojectbackend.entity.po;

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
* @TableName db_user
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_user")
public class DbUser implements Serializable {

    /**
    * 用户编号
    */
    @ApiModelProperty("用户编号")
    @TableId(type = IdType.AUTO)
    private Integer userId;
    /**
    * 用户名
    */
    @NotBlank(message="[用户名]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("用户名")
    @Length(max= 30,message="编码长度不能超过30")
    private String username;
    /**
    * 密码
    */
    @NotBlank(message="[密码]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("密码")
    @Length(max= 30,message="编码长度不能超过30")
    private String password;
    /**
    * 性别
    */
    @ApiModelProperty("性别")
    private Integer sex;
    /**
    * 邮箱
    */
    @NotBlank(message="[邮箱]不能为空")
    @Size(max= 40,message="编码长度不能超过40")
    @ApiModelProperty("邮箱")
    @Length(max= 40,message="编码长度不能超过40")
    private String email;
    /**
    * 头像
    */
    @Size(max= 300,message="编码长度不能超过300")
    @ApiModelProperty("头像")
    @Length(max= 300,message="编码长度不能超过300")
    private String icon;
    /**
    * 注册时间
    */
    @NotNull(message="[注册时间]不能为空")
    @ApiModelProperty("注册时间")
    private String regtime;
    /**
    * 角色
    */
    @NotNull(message="[角色]不能为空")
    @Size(max= 10,message="编码长度不能超过300")
    @ApiModelProperty("角色")
    @Length(max= 10,message="编码长度不能超过300")
    private String role;
    /**
    * 状态
    */
    @NotNull(message="[状态]不能为空")
    @ApiModelProperty("状态")
    private Integer state;
    /**
    * 备注
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("备注")
    @Length(max= 100,message="编码长度不能超过100")
    private String remark;

}

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
* @TableName db_syslog
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_syslog")
public class DbSyslog implements Serializable {

    /**
    * 主键 自动递增
    */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键 自动递增")
    private Integer id;
    /**
    * 操作模块名称
    */
    @NotBlank(message="[操作模块名称]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("操作模块名称")
    @Length(max= 50,message="编码长度不能超过50")
    private String moduleName;
    /**
    * 操作模块动作
    */
    @NotBlank(message="[操作模块动作]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("操作模块动作")
    @Length(max= 20,message="编码长度不能超过20")
    private String actionName;
    /**
     * 操作信息
     */
    @ApiModelProperty("操作信息")
    private String request;
    /**
    * 操作后返回
    */
    @NotBlank(message="[操作信息]不能为空")
    @ApiModelProperty("操作信息")
    private String message;
    /**
    * 操作者
    */
    @NotBlank(message="[操作者]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("操作者")
    @Length(max= 30,message="编码长度不能超过30")
    private String username;
    /**
    * 操作ip
    */
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("操作ip")
    @Length(max= 30,message="编码长度不能超过30")
    private String ip;
    /**
    * 操作时间
    */
    @NotNull(message="[操作时间]不能为空")
    @ApiModelProperty("操作时间")
    private String addTime;

}

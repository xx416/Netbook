package com.xiong.myprojectbackend.entity.vo.resquest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 10371
 * @Description
 * @create 2023/11/8 13:54:03
 */
@Data
@AllArgsConstructor
public class SelectPageVO {
    @ApiModelProperty("显示第几页")
    private Integer currentPage;
    @ApiModelProperty("每页显示条数")
    private Integer pageSize;
    @ApiModelProperty("查询字段")
    private String tagName;
}

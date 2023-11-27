package com.xiong.myprojectbackend.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 10371
 * @Description
 * @create 2023/11/8 15:30:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationVO {
    @ApiModelProperty("总数据量")
    private Integer tableData;
    @ApiModelProperty("总条数")
    private Integer totalCount;
    @ApiModelProperty("总页数")
    private Integer pageCount;

}

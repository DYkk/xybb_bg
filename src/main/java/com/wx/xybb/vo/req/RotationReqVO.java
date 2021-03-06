package com.wx.xybb.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: RotationReqVO
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
@Data
public class RotationReqVO {

    @ApiModelProperty(value = "分页页数")
    private Integer pageNum=1;

    @ApiModelProperty(value = "当前页条数")
    private Integer pageSize=10;
}

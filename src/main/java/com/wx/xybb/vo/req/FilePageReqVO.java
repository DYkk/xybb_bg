package com.wx.xybb.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: FilePageReqVO
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
@Data
public class FilePageReqVO {

    @ApiModelProperty(value = "当前第几页")
    private Integer pageNum=1;
    @ApiModelProperty(value = "当前页数量")
    private Integer pageSize;
}

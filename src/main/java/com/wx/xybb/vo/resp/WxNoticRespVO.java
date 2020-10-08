package com.wx.xybb.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Shawshank King
 * @date 2020-08-15 - 13:24
 */
@Data
public class WxNoticRespVO {
    @ApiModelProperty("公告标题")
    private String title;
    @ApiModelProperty("公告内容")
    private String content;
}

package com.wx.xybb.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Shawshank King
 * @date 2020-07-08 - 14:27
 */

@Data
public class WxLoginRespVO {

    @ApiModelProperty(value = "小程序端token")
    private String token;

    @ApiModelProperty(value = "教务系统cookie")
    private String schoolCookie;

    @ApiModelProperty(value = "学生姓名")
    private String name;
}

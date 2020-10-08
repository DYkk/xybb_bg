package com.wx.xybb.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Shawshank King
 * @date 2020-07-07 - 17:28
 */

@Data
public class WxLoginReqVO {

    @NotBlank(message = "学号不能为空")
    @ApiModelProperty(value = "学号")
    private String studentId;

    @NotBlank(message = "密码不能为空！")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "code获取失败！")
    @ApiModelProperty(value = "小程序code")
    private String code;

    @ApiModelProperty(value = "微信昵称")
    private String nickname;

    @ApiModelProperty(value = "头像地址")
    private String avatarurl;

    @ApiModelProperty(value = "性别")
    private Integer gender;
}

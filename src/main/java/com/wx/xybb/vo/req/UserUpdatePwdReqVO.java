package com.wx.xybb.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: UserUpdatePwdReqVO
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
@Data
public class UserUpdatePwdReqVO {
    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
}

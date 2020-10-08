package com.wx.xybb.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Shawshank King
 * @date 2020-08-12 - 16:08
 */
@Data
public class WxCetRespVO {
    @ApiModelProperty("cet的id")
    private int id;
    @ApiModelProperty("cet的名称")
    private String cardName;
    @ApiModelProperty("cet的证件号")
    private String cardNumber;
}

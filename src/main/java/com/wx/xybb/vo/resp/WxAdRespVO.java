package com.wx.xybb.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Shawshank King
 * @date 2020-08-15 - 11:40
 */
@Data
public class WxAdRespVO {
    @ApiModelProperty("广告图片url")
    private String adImg;
    @ApiModelProperty("跳转链接")
    private String adImgUrl;
    @ApiModelProperty("类型")
    private Integer adType;
    @ApiModelProperty("开关")
    private Integer adSwitch;
}

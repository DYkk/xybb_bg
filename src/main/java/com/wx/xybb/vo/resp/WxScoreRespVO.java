package com.wx.xybb.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Shawshank King
 * @date 2020-08-04 - 8:41
 */

@Data
public class WxScoreRespVO {
    @ApiModelProperty(value = "学校的cookie")
    private String schoolCookie;
    @ApiModelProperty(value = "每一科的分数")
    private Map<String, WxTScoreRespVO> everyScore;
}

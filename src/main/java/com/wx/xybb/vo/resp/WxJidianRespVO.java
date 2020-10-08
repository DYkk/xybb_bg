package com.wx.xybb.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Shawshank King
 * @date 2020-08-09 - 20:57
 */
@Data
public class WxJidianRespVO {
    @ApiModelProperty(value = "平均绩点")
    private Float jpa;
    @ApiModelProperty(value = "平均绩点，班级排名")
    private int jpaRank = 0;
    @ApiModelProperty(value = "平均绩点参与人数")
    private  int jpaPeople = 0;
    @ApiModelProperty(value = "学校的cookie")
    private String schoolCookie;
}

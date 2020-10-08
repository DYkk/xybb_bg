package com.wx.xybb.vo.resp;

import com.wx.xybb.entity.WxExam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-11 - 10:56
 */
@Data
public class WxExamRespVO {
    @ApiModelProperty("教务系统cookie")
    private String schoolCookie;
    @ApiModelProperty("考试信息list")
    private List<WxExam> examList;
}

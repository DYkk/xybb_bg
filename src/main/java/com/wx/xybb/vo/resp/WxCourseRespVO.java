package com.wx.xybb.vo.resp;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Shawshank King
 * @date 2020-08-02 - 17:29
 */
@Data
public class WxCourseRespVO {
    @ApiModelProperty(value = "开学时间")
    private String openData;
    @ApiModelProperty(value = "学生姓名")
    private String studentName;
    @ApiModelProperty(value = "教务系统cookie")
    private String schoolCookie;
    @ApiModelProperty(value = "其他课程")
    private List<Map<String, String>> otherCourse;
    @ApiModelProperty(value = "正常课表")
    private List<WxCourseListRespVO> course;
}

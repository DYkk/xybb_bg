package com.wx.xybb.controller;

import com.alibaba.fastjson.JSONObject;
import com.wx.xybb.constants.Constant;
import com.wx.xybb.service.WxTCourseService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Shawshank King
 * @date 2020-08-13 - 17:39
 */
@RestController
@RequestMapping("/wx")
@Api(tags = "小程序蹭课接口")
public class WxTCourseController {
    @Autowired
    private WxTCourseService wxTCourseService;

    @GetMapping("/selectCourse")
    @ApiOperation(value = "查看是否有学生课表")
    public DataResult<Integer> selectCourse(String nameOrId){
        DataResult result=DataResult.success();
        result.setData(wxTCourseService.selectCourse(nameOrId));
        return result;
    }

    @PostMapping("/getTCourse")
    @ApiOperation(value = "返回此学生课表")
    public DataResult<JSONObject> getTCourse(@RequestBody String nameOrId){
        JSONObject paramsJSONObject = JSONObject.parseObject(nameOrId);
        String nameOrId1 = paramsJSONObject.getString("nameOrId");
        DataResult result=DataResult.success();
        result.setData(wxTCourseService.getTCourse(nameOrId1));
        return result;
    }

    @GetMapping("/isRubLesson")
    @ApiOperation(value = "查看蹭课状态")
    public DataResult<Integer> isRubLesson(HttpServletRequest request){
        DataResult result=DataResult.success();
        String authorization = request.getHeader(Constant.ACCESS_TOKEN);
        String studentId= JwtTokenUtil.getStudentId(authorization);
        result.setData(wxTCourseService.isRubLesson(studentId));
        return result;
    }

    @GetMapping("/setRubLesson")
    @ApiOperation(value = "是否允许蹭课")
    public DataResult setRubLesson(boolean rub,HttpServletRequest request){
        DataResult result=DataResult.success();
        String authorization = request.getHeader(Constant.ACCESS_TOKEN);
        String studentId= JwtTokenUtil.getStudentId(authorization);
        wxTCourseService.setRubLesson(studentId,rub);
        return result;
    }
}

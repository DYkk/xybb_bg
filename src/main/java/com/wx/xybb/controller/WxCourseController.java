package com.wx.xybb.controller;

import com.wx.xybb.constants.Constant;
import com.wx.xybb.service.WxCourseService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.utils.JwtTokenUtil;
import com.wx.xybb.vo.resp.WxCourseRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shawshank King
 * @date 2020-07-31 - 17:01
 */
@RestController
@RequestMapping("/wx")
@Api(tags = "课表查询接口")
public class WxCourseController {
    @Autowired
    private WxCourseService wxCourseService;

    @PostMapping("/getMyCourseByWX")
    @ApiOperation(value = "获取课表接口")
    public DataResult<WxCourseRespVO> getOrUpdateMyCourseByWX(HttpServletRequest request){
        DataResult result=DataResult.success();
        String authorization = request.getHeader(Constant.ACCESS_TOKEN);
        String studentId= JwtTokenUtil.getStudentId(authorization);
        String password= JwtTokenUtil.getPassword(authorization);
        String schoolCookie = request.getHeader("schoolCookie");
        WxCourseRespVO myCourse = wxCourseService.getMyCourse(studentId, password, schoolCookie);
        result.setData(myCourse);
        return result;
    }

    @PostMapping("getQiNiuToken")
    @ApiOperation(value = "获取七牛云token")
    public DataResult<String> getQiNiuToken(){
        DataResult result=DataResult.success();
        result.setData(wxCourseService.getQNToken());
        return result;
    }
}

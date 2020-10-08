package com.wx.xybb.controller;

import com.wx.xybb.constants.Constant;
import com.wx.xybb.service.WxExamService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.utils.JwtTokenUtil;
import com.wx.xybb.vo.resp.WxLoginRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shawshank King
 * @date 2020-08-11 - 16:09
 */

@RestController
@RequestMapping("/wx")
@Api(tags = "小程序获取考试信息接口")
public class WxExamController {
    @Autowired
    private WxExamService wxExamService;

    @PostMapping("/getExam")
    @ApiOperation(value = "小程序获取考试信息接口")
    public DataResult<WxLoginRespVO> login(HttpServletRequest request){
        DataResult result=DataResult.success();
        String authorization = request.getHeader(Constant.ACCESS_TOKEN);
        String studentId= JwtTokenUtil.getStudentId(authorization);
        String password= JwtTokenUtil.getPassword(authorization);
        String schoolCookie = request.getHeader("schoolCookie");
        result.setData(wxExamService.getExam(studentId, password, schoolCookie));
        return result;
    }
}

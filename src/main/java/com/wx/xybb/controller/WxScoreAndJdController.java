package com.wx.xybb.controller;

import com.wx.xybb.constants.Constant;
import com.wx.xybb.service.WxJidianService;
import com.wx.xybb.service.WxScoreService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.utils.JwtTokenUtil;
import com.wx.xybb.vo.resp.WxJidianRespVO;
import com.wx.xybb.vo.resp.WxScoreRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shawshank King
 * @date 2020-08-09 - 21:30
 */
@RestController
@RequestMapping("/wx")
@Api(tags = "成绩和绩点查询接口")
public class WxScoreAndJdController {
    @Autowired
    private WxScoreService wxScoreService;
    @Autowired
    private WxJidianService wxJidianService;

    @PostMapping("/getScoreByWX")
    @ApiOperation(value = "查询成绩接口")
    public DataResult<WxScoreRespVO> getScore(HttpServletRequest request){
        DataResult result=DataResult.success();
        String authorization = request.getHeader(Constant.ACCESS_TOKEN);
        String studentId= JwtTokenUtil.getStudentId(authorization);
        String password= JwtTokenUtil.getPassword(authorization);
        String schoolCookie = request.getHeader("schoolCookie");
        result.setData(wxScoreService.getScore(studentId,password,schoolCookie));
        return result;
    }

    @PostMapping("/getJidianByWX")
    @ApiOperation(value = "查询绩点接口")
    public DataResult<WxJidianRespVO> getJidian(HttpServletRequest request){
        DataResult result=DataResult.success();
        String authorization = request.getHeader(Constant.ACCESS_TOKEN);
        String studentId= JwtTokenUtil.getStudentId(authorization);
        String password= JwtTokenUtil.getPassword(authorization);
        String schoolCookie = request.getHeader("schoolCookie");
        result.setData(wxJidianService.getJidian(studentId, password,schoolCookie));
        return result;
    }
}

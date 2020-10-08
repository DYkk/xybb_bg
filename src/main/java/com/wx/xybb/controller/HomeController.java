package com.wx.xybb.controller;

import com.wx.xybb.aop.annotation.MyLog;
import com.wx.xybb.constants.Constant;
import com.wx.xybb.service.HomeService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.utils.JwtTokenUtil;
import com.wx.xybb.vo.resp.HomeRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: HomeController
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "首页模块",description = "首页相关模块")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @GetMapping("/home")
    @ApiOperation(value = "获取首页数据接口")
    @MyLog(title = "首页模块",action = "获取首页数据接口")
    public DataResult<HomeRespVO> getHome(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String userId= JwtTokenUtil.getUserId(accessToken);
        DataResult result =DataResult.success();
        result.setData(homeService.getHome(userId));
        return result;
    }
}

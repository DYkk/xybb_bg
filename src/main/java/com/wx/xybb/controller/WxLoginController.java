package com.wx.xybb.controller;

import com.wx.xybb.constants.Constant;
import com.wx.xybb.service.WxLoginService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.vo.req.WxLoginReqVO;
import com.wx.xybb.vo.resp.WxLoginRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Shawshank King
 * @date 2020-07-10 - 10:58
 */

@RestController
@RequestMapping("/wx")
@Api(tags = "小程序登录接口")
public class WxLoginController {
    @Autowired
    private WxLoginService wxLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "小程序用户登录接口")
    public DataResult<WxLoginRespVO> login(@RequestBody @Valid WxLoginReqVO wxLoginReqVO){
        DataResult result=DataResult.success();
        result.setData(wxLoginService.login(wxLoginReqVO));
        return result;
    }

    @GetMapping("/loginout")
    @ApiOperation(value = "小程序退出登录接口")
    public DataResult loginout(HttpServletRequest request){
        String authorization = request.getHeader(Constant.ACCESS_TOKEN);
        wxLoginService.logout(authorization);
        return DataResult.success();
    }

}

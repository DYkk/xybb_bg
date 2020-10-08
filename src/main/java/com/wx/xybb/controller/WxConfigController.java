package com.wx.xybb.controller;

import com.wx.xybb.entity.WxBanner;
import com.wx.xybb.service.WxConfigService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.vo.resp.WxNoticRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-15 - 13:36
 */
@RestController
@RequestMapping("/wx")
@Api(tags = "小程序config接口")
public class WxConfigController {
    @Autowired
    private WxConfigService wxConfig;

    @GetMapping("/getSchoolTime")
    @ApiOperation(value = "校历")
    public DataResult<String> getSchoolTime(){
        DataResult result=DataResult.success();
        result.setData(wxConfig.getSchoolTime());
        return result;
    }

    @GetMapping("/getTimes")
    @ApiOperation(value = "作息表")
    public DataResult<String> getTimes(){
        DataResult result=DataResult.success();
        result.setData(wxConfig.getTimes());
        return result;
    }

    @GetMapping("/getNotic")
    @ApiOperation(value = "首页公告")
    public DataResult<WxNoticRespVO> getNotic(){
        DataResult result=DataResult.success();
        result.setData(wxConfig.getNotic());
        return result;
    }

    @GetMapping("/getBanner")
    @ApiOperation(value = "轮播图")
    public DataResult<List<WxBanner>> getBanner(){
        DataResult result=DataResult.success();
        result.setData(wxConfig.getBanner());
        return result;
    }
}

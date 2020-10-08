package com.wx.xybb.controller;

import com.wx.xybb.constants.Constant;
import com.wx.xybb.service.WxCetService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.utils.JwtTokenUtil;
import com.wx.xybb.vo.req.WxLoginReqVO;
import com.wx.xybb.vo.resp.WxCetRespVO;
import com.wx.xybb.vo.resp.WxLoginRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-12 - 16:57
 */
@RestController
@RequestMapping("/wx")
@Api(tags = "小程序准考证号保存接口")
public class WxCetController {
    @Autowired
    private WxCetService wxCetService;

    @PostMapping("/setCet")
    @ApiOperation(value = "添加准考证号")
    public DataResult setCet(String cardName, String cardNumber, HttpServletRequest request){
        DataResult result=DataResult.success();
        String studentId = JwtTokenUtil.getStudentId(request.getHeader(Constant.ACCESS_TOKEN));
        wxCetService.setCet(studentId,cardName,cardNumber);
        return result;
    }

    @PostMapping("/getCet")
    @ApiOperation(value = "获取准考证号")
    public DataResult<List<WxCetRespVO>> getCet(HttpServletRequest request){
        DataResult result=DataResult.success();
        String studentId = JwtTokenUtil.getStudentId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(wxCetService.getCetList(studentId));
        return result;
    }

    @GetMapping("/deleteCet")
    @ApiOperation(value = "删除准考证号")
    public DataResult deleteCet(int id){
        DataResult result=DataResult.success();
        wxCetService.deleteCet(id);
        return result;
    }
}

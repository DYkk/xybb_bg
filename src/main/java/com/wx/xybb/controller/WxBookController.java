package com.wx.xybb.controller;

import com.wx.xybb.service.WxBookService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.vo.resp.WxBookTitleRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-13 - 14:32
 */
@RestController
@RequestMapping("/wx")
@Api(tags = "小程序借阅功能")
public class WxBookController {
    @Autowired
    private WxBookService wxBookService;

    @PostMapping("/getBook")
    @ApiOperation(value = "查阅标题")
    public DataResult<List<WxBookTitleRespVO>> getBook(String book, int pageConunt){
        DataResult result=DataResult.success();
        result.setData(wxBookService.getBook(book,pageConunt));
        return result;
    }

    @GetMapping("/getBookNum")
    @ApiOperation(value = "查阅详情")
    public DataResult getBookNum(String num){
        DataResult result=DataResult.success();
        result.setData(wxBookService.getBookNum(num));
        return result;
    }
}

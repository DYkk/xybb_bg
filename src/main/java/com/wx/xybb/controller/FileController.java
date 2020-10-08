package com.wx.xybb.controller;

import com.wx.xybb.constants.Constant;
import com.wx.xybb.entity.SysFile;
import com.wx.xybb.service.FileService;
import com.wx.xybb.utils.DataResult;
import com.wx.xybb.utils.JwtTokenUtil;
import com.wx.xybb.vo.req.FilePageReqVO;
import com.wx.xybb.vo.resp.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @ClassName: FileController
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "文件操作相关接口")
public class FileController {

    @Autowired
    private FileService fileService;
    @PostMapping("/file")
    @ApiOperation(value = "文件上传接口")
    public DataResult upload(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request){
        String userId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        Integer type=request.getIntHeader(Constant.FILE_TYPE);
        return DataResult.success(fileService.upload(file,userId,type));
    }


    @GetMapping("/file/{fileId}")
    @ApiOperation(value = "文件下载接口")
    public void download(@PathVariable("fileId") String fileId,HttpServletResponse response){
        fileService.download(fileId,response);
    }

    @PostMapping("/files")
    @ApiOperation(value = "分页获取我的文件接口")
    public DataResult<PageVO<SysFile>> pageInfo(@RequestBody FilePageReqVO vo,HttpServletRequest request){
        String userId=JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        DataResult result=DataResult.success();
        result.setData(fileService.pageInfo(vo,userId));
        return result;
    }

}

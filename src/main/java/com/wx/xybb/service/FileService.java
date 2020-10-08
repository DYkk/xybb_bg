package com.wx.xybb.service;

import com.wx.xybb.entity.SysFile;
import com.wx.xybb.vo.req.FilePageReqVO;
import com.wx.xybb.vo.resp.PageVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * @ClassName: FileService
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
public interface FileService {

    String upload(MultipartFile file,String userId,Integer type);

    void download(String fileId,HttpServletResponse response);

    int deleteByFileUrl(String fileUrl);

    PageVO<SysFile> pageInfo(FilePageReqVO vo,String userId);
}

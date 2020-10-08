package com.wx.xybb.service;

import com.wx.xybb.entity.SysLog;
import com.wx.xybb.vo.req.SysLogPageReqVO;
import com.wx.xybb.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: LogService
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
public interface LogService {

    PageVO<SysLog> pageInfo(SysLogPageReqVO vo);

    void deletedLog(List<String> logIds);
}

package com.wx.xybb.service;

import com.wx.xybb.vo.req.WxLoginReqVO;
import com.wx.xybb.vo.resp.WxLoginRespVO;

/**
 * @author Shawshank King
 * @date 2020-07-07 - 17:07
 */
public interface WxLoginService {
    //登录
    WxLoginRespVO login(WxLoginReqVO wxLoginReqVO);
    //退出
    void logout(String token);
}

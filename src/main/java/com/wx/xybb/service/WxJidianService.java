package com.wx.xybb.service;

import com.wx.xybb.vo.resp.WxJidianRespVO;

/**
 * @author Shawshank King
 * @date 2020-08-10 - 10:32
 */
public interface WxJidianService {
    //获取绩点
    WxJidianRespVO getJidian(String studentId, String password, String schoolCookie);
}

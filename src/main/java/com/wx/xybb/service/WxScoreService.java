package com.wx.xybb.service;

import com.wx.xybb.vo.resp.WxScoreRespVO;

/**
 * @author Shawshank King
 * @date 2020-08-01 - 17:18
 */
public interface WxScoreService {
    //获取成绩
    WxScoreRespVO getScore(String studentId, String password, String schoolCookie);
}

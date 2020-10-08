package com.wx.xybb.service;



import com.wx.xybb.vo.resp.WxCourseRespVO;

/**
 * @author Shawshank King
 * @date 2020-07-24 - 16:56
 */
public interface WxCourseService {
    WxCourseRespVO getMyCourse(String studentId, String password, String schoolCookie);
    String getQNToken();
}

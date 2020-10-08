package com.wx.xybb.service;

import com.wx.xybb.vo.resp.WxExamRespVO;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-11 - 11:11
 */
public interface WxExamService {
    WxExamRespVO getExam(String studentId, String password, String schoolCookie);
}

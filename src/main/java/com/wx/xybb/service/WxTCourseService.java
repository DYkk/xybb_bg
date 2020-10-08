package com.wx.xybb.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Shawshank King
 * @date 2020-08-13 - 16:13
 */
public interface WxTCourseService {
    int selectCourse(String nameOrId);
    JSONObject getTCourse(String nameOrId);
    Integer isRubLesson(String studentId);
    void setRubLesson(String studentId,boolean rub);
}

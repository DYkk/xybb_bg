package com.wx.xybb.vo.resp;

import lombok.Data;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-07-27 - 9:29
 */

@Data
public class WxCourseListRespVO {
    private String courseName;//课程名字
    private String coursePlace;//上课地点
    private String courseTeacher;//老师
    private String courseType;//考试类型
    private String courseTime;//哪几周上课
    private String courseLesson;//第几节课上
    private String courseColor;//课程显示颜色
    private List<Integer> courseAllWeeks;//哪几周上课
    private int courseDay;//周几上课
    private int courseStart;//是第几节上课
    private int courseLength;//课程长度，有几节课
    }

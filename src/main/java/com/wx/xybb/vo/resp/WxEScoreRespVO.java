package com.wx.xybb.vo.resp;

import lombok.Data;

/**
 * @author Shawshank King
 * @date 2020-08-04 - 11:31
 */
@Data
public class WxEScoreRespVO {
    private String course;//课程名称
    private String course_type;//课程类型
    private String credits;//课程学分
    private String score;//分数
    private String jidian;//绩点
    private String scoreType;//正考还是补考还是重修
    private String teacher;//老师
    private int scoreRank = 0;//排名
    private int numberPeople = 0;//班级参与人数
}

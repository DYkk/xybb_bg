package com.wx.xybb.entity;

import lombok.Data;

/**
 * @author Shawshank King
 * @date 2020-08-11 - 11:17
 */
@Data
public class WxExam {
    private String name;//名称
    private String place;//考试地点
    private String time;//考试时间
    private String area;//考试校区
    private String seat;//考试座位号
    private String renovate;//重修标记
}

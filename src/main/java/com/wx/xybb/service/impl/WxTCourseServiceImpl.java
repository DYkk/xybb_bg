package com.wx.xybb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wx.xybb.entity.WxSchoolTimetable;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.mapper.WxSchoolTimetableMapper;
import com.wx.xybb.service.WxTCourseService;
import com.wx.xybb.utils.WxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shawshank King
 * @date 2020-08-13 - 16:35
 */
@Service
@Slf4j
public class WxTCourseServiceImpl implements WxTCourseService {

    @Autowired
    private WxSchoolTimetableMapper wxSchoolMapper;

    @Override
    public int selectCourse(String nameOrId) {
        int re = 2;//无课表
        if (WxUtils.isNumericZidai(nameOrId)) {
            WxSchoolTimetable wxSchoolTimetable = wxSchoolMapper.selectCourseById(nameOrId);
            if (wxSchoolTimetable != null) {
                re = 1;//有课表
            }
        } else {
            WxSchoolTimetable wxSchoolTimetable = wxSchoolMapper.selectByName(nameOrId);
            if (wxSchoolTimetable != null) {
                re = 1;//有课表
            }
        }
        return re;
    }

    @Override
    public JSONObject getTCourse(String nameOrId) {
        System.out.println("get"+nameOrId);
        JSONObject json = new JSONObject();
        if (WxUtils.isNumericZidai(nameOrId)) {
            WxSchoolTimetable wxSchoolTimetable = wxSchoolMapper.selectCourseById(nameOrId);
            if (wxSchoolTimetable != null) {
                JSONObject jsonObject = JSONObject.parseObject(wxSchoolTimetable.getTimetable());
                json = jsonObject;
            }
        } else {
            System.out.println(wxSchoolMapper.selectByPrimaryKey(nameOrId));
            WxSchoolTimetable wxSchoolTimetable = wxSchoolMapper.selectByName(nameOrId);
            System.out.println(wxSchoolTimetable);
            if (wxSchoolTimetable != null) {
                JSONObject jsonObject = JSONObject.parseObject(wxSchoolTimetable.getTimetable());
                json = jsonObject;
            }
        }
        return json;
    }

    @Override
    public Integer isRubLesson(String studentId) {
        Integer integer = wxSchoolMapper.selectById(studentId);
        return integer;
    }

    @Override
    public void setRubLesson(String studentId, boolean rub) {
        System.out.println("蹭课开关"+rub);
        WxSchoolTimetable wxSchoolTimetable = new WxSchoolTimetable();
        wxSchoolTimetable.setStudentId(studentId);
        if(rub==false){
            wxSchoolTimetable.setRubLesson(1);
        }else {
            wxSchoolTimetable.setRubLesson(0);
        }
        int i = wxSchoolMapper.updateByPrimaryKeySelective(wxSchoolTimetable);
        System.out.println("数据库返回值"+i);
        if (i != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }
}

package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxSchoolTimetable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WxSchoolTimetableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxSchoolTimetable record);

    int insertSelective(WxSchoolTimetable record);

    WxSchoolTimetable selectByPrimaryKey(@Param("studentId") String studentId);

    int updateByPrimaryKeySelective(WxSchoolTimetable record);

    int updateByPrimaryKey(WxSchoolTimetable record);

    WxSchoolTimetable selectByName(@Param("name") String name);

    WxSchoolTimetable selectCourseById(@Param("studentId") String studentId);

    Integer selectById(@Param("studentId") String studentId);
}
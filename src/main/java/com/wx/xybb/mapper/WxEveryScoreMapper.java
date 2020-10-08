package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxEveryScore;
import com.wx.xybb.entity.WxRankPeople;
import com.wx.xybb.vo.resp.WxCourseTaskRespVO;
import com.wx.xybb.vo.resp.WxIdScoreTaskRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxEveryScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxEveryScore record);

    int insertSelective(WxEveryScore record);

    WxEveryScore selectByPrimaryKey(@Param("studentId") String studentId, @Param("course")String course);

    int updateByPrimaryKeySelective(WxEveryScore record);

    int updateByPrimaryKey(WxEveryScore record);

    WxRankPeople selectByIdAndCourse(@Param("studentId2") String studentId, @Param("course2")String course);

    String selectClass(@Param("studentId3") String studentId);

    List<WxCourseTaskRespVO> groupByCourse();

    List<WxIdScoreTaskRespVO> selectByCourseAndClass(@Param("course") String course, @Param("classGrade") String classGrade);

    int updateByIdSelective(WxEveryScore record);
}
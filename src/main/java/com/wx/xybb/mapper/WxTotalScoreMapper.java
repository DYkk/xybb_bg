package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxTotalScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxTotalScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxTotalScore record);

    int insertSelective(WxTotalScore record);

    WxTotalScore selectByPrimaryKey(@Param("studentId") String studentId);

    int updateByPrimaryKeySelective(WxTotalScore record);

    int updateByPrimaryKey(WxTotalScore record);

    List<String> groupByGrade();

    List<Long> selectByGrade(@Param("grade") String grade);

    int updateById(WxTotalScore record);
}
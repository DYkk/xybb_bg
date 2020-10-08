package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxCet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxCetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxCet record);

    int insertSelective(WxCet record);

    WxCet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxCet record);

    int updateByPrimaryKey(WxCet record);

    List<WxCet> selectByStudentId(@Param("studentId") String studentId);
}
package com.wx.xybb.mapper;

import com.wx.xybb.entity.SysRotationChart;
import com.wx.xybb.vo.req.RotationChartDeleteReqVO;

import java.util.List;

public interface SysRotationChartMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRotationChart record);

    int insertSelective(SysRotationChart record);

    SysRotationChart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRotationChart record);

    int updateByPrimaryKey(SysRotationChart record);

    List<SysRotationChart> selectAll();

    int batchDeleteRotation(List<RotationChartDeleteReqVO> list);
}
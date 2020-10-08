package com.wx.xybb.service;

import com.wx.xybb.entity.SysRotationChart;
import com.wx.xybb.vo.req.RotationChartDeleteReqVO;
import com.wx.xybb.vo.req.RotationChartReqAddVO;
import com.wx.xybb.vo.req.RotationChartUpdateReqVO;
import com.wx.xybb.vo.req.RotationReqVO;
import com.wx.xybb.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: RotationChartService
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
public interface RotationChartService {

    PageVO<SysRotationChart> pageInfo(RotationReqVO vo);
    void addRotationChart(RotationChartReqAddVO vo,String userId);
    void updateRotationChart(String userId, RotationChartUpdateReqVO vo);
    int batchDeleteRotation(List<RotationChartDeleteReqVO> list);
    List<SysRotationChart> selectAll();
}

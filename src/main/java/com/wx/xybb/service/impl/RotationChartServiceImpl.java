package com.wx.xybb.service.impl;

import com.github.pagehelper.PageHelper;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.entity.SysRotationChart;
import com.wx.xybb.mapper.SysRotationChartMapper;
import com.wx.xybb.service.FileService;
import com.wx.xybb.service.RotationChartService;
import com.wx.xybb.utils.PageUtil;
import com.wx.xybb.vo.req.RotationChartDeleteReqVO;
import com.wx.xybb.vo.req.RotationChartReqAddVO;
import com.wx.xybb.vo.req.RotationChartUpdateReqVO;
import com.wx.xybb.vo.req.RotationReqVO;
import com.wx.xybb.vo.resp.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: RotationChartServiceImpl
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
@Service
public class RotationChartServiceImpl implements RotationChartService {
    @Autowired
    private SysRotationChartMapper sysRotationChartMapper;
    @Autowired
    private FileService fileService;
    @Override
    public PageVO<SysRotationChart> pageInfo(RotationReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysRotationChart> sysRotationCharts = sysRotationChartMapper.selectAll();
        return PageUtil.getPageVO(sysRotationCharts);
    }

    @Override
    public void addRotationChart(RotationChartReqAddVO vo, String userId) {
        SysRotationChart sysRotationChart=new SysRotationChart();
        BeanUtils.copyProperties(vo,sysRotationChart);
        sysRotationChart.setId(UUID.randomUUID().toString());
        sysRotationChart.setCreateTime(new Date());
        sysRotationChart.setCreateId(userId);
        int i = sysRotationChartMapper.insertSelective(sysRotationChart);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public void updateRotationChart(String userId, RotationChartUpdateReqVO vo) {
        SysRotationChart sysRotationChart = sysRotationChartMapper.selectByPrimaryKey(vo.getId());
        if(sysRotationChart==null){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if(!sysRotationChart.getFileUrl().equals(vo.getFileUrl())){
            //首先要把文件按信息表的数据删除
            //就是把磁盘上的文件删除
            fileService.deleteByFileUrl(sysRotationChart.getFileUrl());
        }
        BeanUtils.copyProperties(vo,sysRotationChart);
        sysRotationChart.setUpdateId(userId);
        sysRotationChart.setUpdateTime(new Date());
        int i = sysRotationChartMapper.updateByPrimaryKeySelective(sysRotationChart);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public int batchDeleteRotation(List<RotationChartDeleteReqVO> list) {
        //删除轮播图数据
        int i = sysRotationChartMapper.batchDeleteRotation(list);
        //删除轮播与文件在文件信息表里面的数据
        //删除文件对应的磁盘文件
        for (RotationChartDeleteReqVO vo:list){
            fileService.deleteByFileUrl(vo.getFileUrl());
        }
        return i;
    }

    @Override
    public List<SysRotationChart> selectAll() {
        return sysRotationChartMapper.selectAll();
    }
}

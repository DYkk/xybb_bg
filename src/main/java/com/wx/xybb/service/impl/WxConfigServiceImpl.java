package com.wx.xybb.service.impl;

import com.wx.xybb.entity.WxBanner;
import com.wx.xybb.entity.WxConfig;
import com.wx.xybb.mapper.WxBannerMapper;
import com.wx.xybb.mapper.WxConfigMapper;
import com.wx.xybb.service.WxConfigService;
import com.wx.xybb.vo.resp.WxAdRespVO;
import com.wx.xybb.vo.resp.WxNoticRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-15 - 12:15
 */
@Service
@Slf4j
public class WxConfigServiceImpl implements WxConfigService {
    @Autowired
    private WxConfigMapper wxConfigMapper;
    @Autowired
    private WxBannerMapper wxBannerMapper;

    @Override
    public String getSchoolTime() {
        String schoolTime = wxConfigMapper.selectSchoolTime();
        return schoolTime;
    }

    @Override
    public void setSchoolTime() {

    }


    @Override
    public String getTimes() {
        String t = wxConfigMapper.selectTime();
        return t;
    }

    @Override
    public void setTimes() {

    }

    @Override
    public WxNoticRespVO getNotic() {
        WxConfig wxConfig = wxConfigMapper.selectNotice();
        WxNoticRespVO wxNoticRespVO = new WxNoticRespVO();
        wxNoticRespVO.setTitle(wxConfig.getConfigName());
        wxNoticRespVO.setContent(wxConfig.getConfigValue());
        return wxNoticRespVO;
    }

    @Override
    public void setNotic() {

    }


    @Override
    public WxAdRespVO getAd() {
        return null;
    }

    @Override
    public void setAd() {

    }

    @Override
    public List<WxBanner> getBanner() {
        List<WxBanner> wxBanner = wxBannerMapper.selectAllBanner();
        return wxBanner;
    }

    @Override
    public void setBanner() {

    }


}

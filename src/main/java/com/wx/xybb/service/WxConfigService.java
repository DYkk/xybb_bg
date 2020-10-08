package com.wx.xybb.service;

import com.wx.xybb.entity.WxBanner;
import com.wx.xybb.vo.resp.WxAdRespVO;
import com.wx.xybb.vo.resp.WxNoticRespVO;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-15 - 11:34
 */
public interface WxConfigService {
    String getSchoolTime();
    void setSchoolTime();
    String getTimes();
    void setTimes();
    WxNoticRespVO getNotic();
    void setNotic();
    WxAdRespVO getAd();
    void setAd();
    List<WxBanner> getBanner();
    void  setBanner();
}

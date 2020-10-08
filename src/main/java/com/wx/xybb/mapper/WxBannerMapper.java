package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxBanner;

import java.util.List;

public interface WxBannerMapper {
    int insert(WxBanner record);

    int insertSelective(WxBanner record);

    List<WxBanner> selectAllBanner();
}
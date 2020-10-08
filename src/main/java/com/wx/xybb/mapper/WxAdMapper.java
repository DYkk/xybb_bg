package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxAd;

public interface WxAdMapper {
    int insert(WxAd record);

    int insertSelective(WxAd record);
}
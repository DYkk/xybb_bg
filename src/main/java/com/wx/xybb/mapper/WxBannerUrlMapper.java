package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxBannerUrl;

public interface WxBannerUrlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxBannerUrl record);

    int insertSelective(WxBannerUrl record);

    WxBannerUrl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxBannerUrl record);

    int updateByPrimaryKey(WxBannerUrl record);
}
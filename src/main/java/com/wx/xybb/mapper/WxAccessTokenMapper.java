package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxAccessToken;

public interface WxAccessTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxAccessToken record);

    int insertSelective(WxAccessToken record);

    WxAccessToken selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxAccessToken record);

    int updateByPrimaryKey(WxAccessToken record);
}
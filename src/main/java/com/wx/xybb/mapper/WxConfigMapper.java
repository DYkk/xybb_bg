package com.wx.xybb.mapper;

import com.wx.xybb.entity.WxConfig;

public interface WxConfigMapper {
    int insert(WxConfig record);

    int insertSelective(WxConfig record);

    String selectSchoolTime();

    String selectTime();

    WxConfig selectNotice();
}
package com.wx.xybb.mapper;

import com.wx.xybb.entity.TopiceAdmin;

public interface TopiceAdminMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopiceAdmin record);

    int insertSelective(TopiceAdmin record);

    TopiceAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopiceAdmin record);

    int updateByPrimaryKey(TopiceAdmin record);
}
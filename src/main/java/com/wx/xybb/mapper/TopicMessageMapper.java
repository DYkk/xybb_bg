package com.wx.xybb.mapper;

import com.wx.xybb.entity.TopicMessage;

public interface TopicMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicMessage record);

    int insertSelective(TopicMessage record);

    TopicMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicMessage record);

    int updateByPrimaryKey(TopicMessage record);
}
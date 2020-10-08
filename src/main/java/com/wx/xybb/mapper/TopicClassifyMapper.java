package com.wx.xybb.mapper;

import com.wx.xybb.entity.TopicClassify;

public interface TopicClassifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicClassify record);

    int insertSelective(TopicClassify record);

    TopicClassify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicClassify record);

    int updateByPrimaryKey(TopicClassify record);
}
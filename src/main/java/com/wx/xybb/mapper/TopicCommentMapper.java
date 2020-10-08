package com.wx.xybb.mapper;

import com.wx.xybb.entity.TopicComment;

public interface TopicCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicComment record);

    int insertSelective(TopicComment record);

    TopicComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicComment record);

    int updateByPrimaryKey(TopicComment record);
}
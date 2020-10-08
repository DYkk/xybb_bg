package com.wx.xybb.mapper;

import com.wx.xybb.entity.TopicReply;

public interface TopicReplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicReply record);

    int insertSelective(TopicReply record);

    TopicReply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicReply record);

    int updateByPrimaryKey(TopicReply record);
}
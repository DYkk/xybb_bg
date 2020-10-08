package com.wx.xybb.mapper;

import com.wx.xybb.entity.TopicInvitation;

public interface TopicInvitationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicInvitation record);

    int insertSelective(TopicInvitation record);

    TopicInvitation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicInvitation record);

    int updateByPrimaryKey(TopicInvitation record);
}
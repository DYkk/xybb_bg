package com.wx.xybb.mapper;

import com.wx.xybb.entity.TopicPraise;

public interface TopicPraiseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicPraise record);

    int insertSelective(TopicPraise record);

    TopicPraise selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicPraise record);

    int updateByPrimaryKey(TopicPraise record);
}
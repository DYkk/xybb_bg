package com.wx.xybb.entity;

import java.io.Serializable;
import java.util.Date;

public class TopicInvitation implements Serializable {
    private Long id;

    private Long uid;

    private Long classityId;

    private String content;

    private String imgs;

    private Integer praiseNumber;

    private Integer commentNumber;

    private Integer viewNumber;

    private Integer anonymous;

    private Integer superior;

    private Integer enabled;

    private Date time;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getClassityId() {
        return classityId;
    }

    public void setClassityId(Long classityId) {
        this.classityId = classityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs == null ? null : imgs.trim();
    }

    public Integer getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(Integer praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Integer getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(Integer viewNumber) {
        this.viewNumber = viewNumber;
    }

    public Integer getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }

    public Integer getSuperior() {
        return superior;
    }

    public void setSuperior(Integer superior) {
        this.superior = superior;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", classityId=").append(classityId);
        sb.append(", content=").append(content);
        sb.append(", imgs=").append(imgs);
        sb.append(", praiseNumber=").append(praiseNumber);
        sb.append(", commentNumber=").append(commentNumber);
        sb.append(", viewNumber=").append(viewNumber);
        sb.append(", anonymous=").append(anonymous);
        sb.append(", superior=").append(superior);
        sb.append(", enabled=").append(enabled);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
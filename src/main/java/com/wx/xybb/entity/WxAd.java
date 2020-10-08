package com.wx.xybb.entity;

import java.io.Serializable;

public class WxAd implements Serializable {
    private Long id;

    private String adImg;

    private String adImgUrl;

    private Integer adType;

    private Integer adSwitch;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg == null ? null : adImg.trim();
    }

    public String getAdImgUrl() {
        return adImgUrl;
    }

    public void setAdImgUrl(String adImgUrl) {
        this.adImgUrl = adImgUrl == null ? null : adImgUrl.trim();
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public Integer getAdSwitch() {
        return adSwitch;
    }

    public void setAdSwitch(Integer adSwitch) {
        this.adSwitch = adSwitch;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", adImg=").append(adImg);
        sb.append(", adImgUrl=").append(adImgUrl);
        sb.append(", adType=").append(adType);
        sb.append(", adSwitch=").append(adSwitch);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
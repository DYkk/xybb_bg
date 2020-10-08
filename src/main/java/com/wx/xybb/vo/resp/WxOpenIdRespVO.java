package com.wx.xybb.vo.resp;

import lombok.Data;

/**
 * @author Shawshank King
 * @date 2020-07-08 - 18:09
 */
@Data
public class WxOpenIdRespVO {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode = "0";
    private String errmsg;
    private int expires_in;
}

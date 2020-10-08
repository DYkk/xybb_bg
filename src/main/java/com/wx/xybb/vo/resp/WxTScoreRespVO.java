package com.wx.xybb.vo.resp;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Shawshank King
 * @date 2020-08-04 - 9:46
 */
@Data
public class WxTScoreRespVO {
    private List<WxEScoreRespVO> lastTerm;
    private List<WxEScoreRespVO> nextTerm;
}

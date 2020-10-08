package com.wx.xybb.service;

import com.wx.xybb.vo.resp.WxCetRespVO;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-12 - 15:18
 */
public interface WxCetService {
    void setCet(String studentId,String cardName,String cardNumber);
    List<WxCetRespVO> getCetList(String studentId);
    void deleteCet(int id);
}

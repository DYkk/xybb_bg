package com.wx.xybb.service.impl;

import com.wx.xybb.entity.WxCet;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.mapper.WxCetMapper;
import com.wx.xybb.service.WxCetService;
import com.wx.xybb.vo.resp.WxCetRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-12 - 15:28
 */
@Service
@Slf4j
public class WxCetServiceImpl implements WxCetService {

    @Autowired
    private WxCetMapper wxCetMapper;
    @Override
    public void setCet(String studentId,String cardName,String cardNumber) {
        WxCet wxCet = new WxCet();
        wxCet.setStudentId(studentId);
        wxCet.setName(cardName);
        wxCet.setNumber(cardNumber);
        wxCet.setTime(new Date());
        int i = wxCetMapper.insertSelective(wxCet);
        if (i != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public List<WxCetRespVO> getCetList(String studentId) {
        List<WxCetRespVO> wxCetResp = new ArrayList<>();
        List<WxCet> wxCets = wxCetMapper.selectByStudentId(studentId);
        if (wxCets == null){
            throw new BusinessException(BaseResponseCode.NO_CET);
        }
        for (WxCet wxCet : wxCets) {
            WxCetRespVO wxCetRespVO = new WxCetRespVO();
            wxCetRespVO.setId(wxCet.getId().intValue());
            wxCetRespVO.setCardName(wxCet.getName());
            wxCetRespVO.setCardNumber(wxCet.getNumber());
            wxCetResp.add(wxCetRespVO);
        }
        return wxCetResp;
    }

    @Override
    public void deleteCet(int id) {
        WxCet wxCet = new WxCet();
        wxCet.setId(Long.valueOf(id));
        wxCet.setDelect(1);
        int i = wxCetMapper.updateByPrimaryKeySelective(wxCet);
        if (i != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }
}

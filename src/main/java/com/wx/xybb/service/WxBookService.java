package com.wx.xybb.service;

import com.wx.xybb.vo.resp.WxBookRespVO;
import com.wx.xybb.vo.resp.WxBookTitleRespVO;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-13 - 13:44
 */
public interface WxBookService {
    List<WxBookTitleRespVO> getBook(String book,int pageConunt);
    List<WxBookRespVO> getBookNum(String num);
}

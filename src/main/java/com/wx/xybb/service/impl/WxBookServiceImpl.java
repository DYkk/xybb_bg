package com.wx.xybb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.service.WxBookService;
import com.wx.xybb.vo.resp.WxBookRespVO;
import com.wx.xybb.vo.resp.WxBookTitleRespVO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author Shawshank King
 * @date 2020-08-13 - 13:52
 */
@Service
@Slf4j
public class WxBookServiceImpl implements WxBookService {
    private final String url = "http://211.64.32.205:8080/opac/";
    private Connection connection;
    private Connection.Response response;
    private Document document;
    private Elements elements;

    //    字符串拼接
    public String stringPinJie(String book, int pageCount){
        String pinJie = "{\"searchWords\":[{\"fieldList\":[{\"fieldCode\":\"\",\"fieldValue\":\""+book+"\"}]}],\"filters\":[],\"limiter\":[],\"sortField\":\"relevance\",\"sortType\":\"desc\",\"pageSize\":20,\"pageCount\":"+pageCount+",\"locale\":\"zh_CN\",\"first\":true}";
        return pinJie;
    }

    //    提交图书名称,返回图书列表
    @Override
    public List<WxBookTitleRespVO> getBook(String book, int pageConunt) {
        connection = Jsoup.connect(url + "ajax_search_adv.php");
        connection.header("Content-Type", "application/json");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        connection.requestBody(stringPinJie(book,pageConunt));
        try {
            response = connection.cookies(getCookie()).ignoreContentType(true).followRedirects(true)
                    .method(Connection.Method.POST).execute();
        } catch (IOException e) {
            throw new BusinessException(BaseResponseCode.SYSTEM_ERROR);
        }
        JSONObject jsonObject = JSON.parseObject(response.body());
        JSONArray jsonContent = JSON.parseArray(jsonObject.getString("content"));
        List<WxBookTitleRespVO> bookList = new ArrayList<>();
        for (Iterator iterator = jsonContent.iterator(); iterator.hasNext(); ) {
            JSONObject jsonObject1 = (JSONObject) iterator.next();
            WxBookTitleRespVO wxBookT = new WxBookTitleRespVO();
            wxBookT.setNum(jsonObject1.getString("num"));
            wxBookT.setMarcRecNo(jsonObject1.getString("marcRecNo"));
            wxBookT.setTitle(jsonObject1.getString("title"));
            wxBookT.setAuthor(jsonObject1.getString("author"));
            bookList.add(wxBookT);
        }
        return bookList;
    }

    //   图书明细2
    @Override
    public List<WxBookRespVO> getBookNum(String num) {
        connection = Jsoup.connect(url + "/item.php");
        connection.header("Content-Type", "application/json");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        connection.data("marc_no",num);
        try {
            response = connection.cookies(getCookie()).ignoreContentType(true).followRedirects(true)
                    .method(Connection.Method.GET).execute();
        } catch (IOException e) {
            throw new BusinessException(BaseResponseCode.SYSTEM_ERROR);
        }
        document = Jsoup.parse(response.body());
        elements = document.select(".whitetext");
        List<WxBookRespVO> bookList = new ArrayList<>();
        for(int j =1; j <= elements.size(); j++) {
            WxBookRespVO wxBookRespVO = new WxBookRespVO();
            wxBookRespVO.setSuoshu(document.select(".whitetext:eq(" + j + ") td:eq(0)").text());
            wxBookRespVO.setTiaoma(document.select(".whitetext:eq(" + j + ") td:eq(1)").text());
            wxBookRespVO.setGuancang(document.select(".whitetext:eq(" + j + ") td:eq(3)").text());
            wxBookRespVO.setZhuangtai(document.select(".whitetext:eq(" + j + ") td:eq(4)").text());
            wxBookRespVO.setHuanshu(document.select(".whitetext:eq("+j+") td:eq(5)").text());
            bookList.add(wxBookRespVO);
        }
        return bookList;
    }

    //    获取cookie
    public Map<String,String> getCookie(){
        connection = Jsoup.connect(url+"search_adv.php");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        try {
            response = connection.execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(BaseResponseCode.SYSTEM_ERROR);
        }
        Map<String,String> cookies = response.cookies();
        return cookies;
    }
}

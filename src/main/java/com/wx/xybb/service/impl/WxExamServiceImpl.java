package com.wx.xybb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.xybb.entity.WxExam;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.service.WxExamService;
import com.wx.xybb.utils.Aes;
import com.wx.xybb.utils.ConnectJWGL;
import com.wx.xybb.utils.WxUtils;
import com.wx.xybb.vo.resp.WxExamRespVO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author Shawshank King
 * @date 2020-08-11 - 11:12
 */
@Service
@Slf4j
public class WxExamServiceImpl implements WxExamService {
    @Autowired
    private Aes aes;
    //    private final WxUtils wxUtils = new WxUtils();
    @Value("${school_url}")
    private  String url;
    //    private final String url = "http://211.64.32.195";

    @Override
    public WxExamRespVO getExam(String studentId, String password, String schoolCookie) {
        Map<String, String> cookies = WxUtils.getCookie(schoolCookie);
        int[] yearAndTerm = WxUtils.getYAndTByDate();
        WxExamRespVO examFromS = getExamFromS(studentId, password, yearAndTerm[0], yearAndTerm[1], cookies, 0);
        return examFromS;
    }

    //获取考试信息
    public WxExamRespVO getExamFromS(String xh,String pswd,int year, int term, Map<String, String> cookies,int i) {
        Map<String, String> datas = new HashMap<>();
        datas.put("xnm", String.valueOf(year));
        datas.put("xqm", String.valueOf(term * term * 3));
        datas.put("_search", "false");
        datas.put("nd", String.valueOf(System.currentTimeMillis()));
        datas.put("queryModel.showCount", "100");
        datas.put("queryModel.currentPage", "1");
        datas.put("queryModel.sortName", "");
        datas.put("queryModel.sortOrder", "asc");
        datas.put("queryModel.sortName", "");
        datas.put("time", "0");
        Connection connection = Jsoup.connect(url + "/jwglxt/kwgl/kscx_cxXsksxxIndex.html?doType=query&gnmkdm=N358105");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        Connection.Response response = null;
        try {
            response = connection.cookies(cookies).method(Connection.Method.POST)
                    .data(datas).ignoreContentType(true).execute();
        } catch (IOException e) {
            throw new BusinessException(BaseResponseCode.SCHOOL_ERROR);
        }
        //如果cookie过期，重新获取cookie
        if(!response.body().contains("items")){
            i++;
            if(i>=2){
                throw new BusinessException(BaseResponseCode.NO_SCORE);
            }
            ConnectJWGL connectJWGL = new ConnectJWGL(xh, pswd);
            connectJWGL.init();
            Map<String, String> stringStringMap = connectJWGL.checkLogin();
            Map<String, String> newCookie = new HashMap<>();
            newCookie.put("JSESSIONID",stringStringMap.get("cookie"));
            WxExamRespVO examFromS = getExamFromS(xh, pswd, year, term, newCookie, i);
            return examFromS;
        }
        JSONObject jsonObject = JSON.parseObject(response.body());
        JSONArray examTable = JSON.parseArray(jsonObject.getString("items"));
        //System.out.println(examTable);
        List<WxExam> exams = new ArrayList<>();
        for (Iterator iterator = examTable.iterator(); iterator.hasNext(); ) {
            JSONObject one = (JSONObject) iterator.next();
            WxExam exam = new WxExam();
            exam.setName(one.getString("kcmc"));
            exam.setRenovate(one.getString("cxbj"));
            exam.setTime(one.getString("kssj"));
            exam.setPlace(one.getString("cdmc"));
            exam.setArea(one.getString("cdxqmc"));
            exam.setSeat(one.getString("zwh"));

            exams.add(exam);
        }
        WxExamRespVO wxExamRespVO = new WxExamRespVO();
        wxExamRespVO.setSchoolCookie(cookies.get("JSESSIONID"));
        wxExamRespVO.setExamList(exams);
        //System.out.println(exams);
        return wxExamRespVO;
    }
}

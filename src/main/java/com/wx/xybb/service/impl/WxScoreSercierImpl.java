package com.wx.xybb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.xybb.entity.WxEveryScore;
import com.wx.xybb.entity.WxRankPeople;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.mapper.WxEveryScoreMapper;
import com.wx.xybb.service.WxScoreService;
import com.wx.xybb.utils.Aes;
import com.wx.xybb.utils.ConnectJWGL;
import com.wx.xybb.utils.WxUtils;
import com.wx.xybb.vo.resp.WxEScoreRespVO;
import com.wx.xybb.vo.resp.WxScoreRespVO;
import com.wx.xybb.vo.resp.WxTScoreRespVO;
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
 * @date 2020-08-04 - 10:30
 */

@Service
@Slf4j
public class WxScoreSercierImpl implements WxScoreService {

    @Autowired
    private Aes aes;
//    private final WxUtils wxUtils = new WxUtils();
    @Value("${school_url}")
    private  String url;
//    private final String url = "http://211.64.32.195";
    @Value("${kaixue}")
    private String kaixue;
//    private final String kaixue = "2020-02-23";
    @Autowired
    private WxEveryScoreMapper wxEveryScoreMapper;

    @Override
    public WxScoreRespVO getScore(String studentId, String password, String schoolCookie) {
        Map<String, String> cookies = WxUtils.getCookie(schoolCookie);
        WxScoreRespVO studentGrade = getStudentGrade(studentId, password, cookies, 0);
        return studentGrade;
    }

    // 获取成绩信息 不填写year 和term 获取所有成绩
    public WxScoreRespVO getStudentGrade(String xh,String pswd,Map<String,String> cookies,int i)  {
        Map<String, String> datas = new HashMap<>();
        datas.put("xnm","");
        datas.put("xqm","");
        datas.put("_search", "false");
        datas.put("nd", String.valueOf(System.currentTimeMillis()));
        datas.put("queryModel.showCount", "300");
        datas.put("queryModel.currentPage", "1");
        datas.put("queryModel.sortName", "");
        datas.put("queryModel.sortOrder", "asc");
        datas.put("time", "0");
        Connection connection = Jsoup.connect(url + "/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdm=N305005");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        Connection.Response response = null;
        try {
            response = connection.cookies(cookies).method(Connection.Method.POST)
                    .data(datas).ignoreContentType(true).execute();
        } catch (IOException e) {
            throw new BusinessException(BaseResponseCode.SCHOOL_ERROR);
        }
        //如果cookie过期，重新获取cookie
        if(!response.body().contains("kcmc")){
            i++;
            if(i>=2){
                throw new BusinessException(BaseResponseCode.NO_SCORE);
            }
            ConnectJWGL connectJWGL = new ConnectJWGL(xh, pswd);
            connectJWGL.init();
            Map<String, String> stringStringMap = connectJWGL.checkLogin();
            Map<String, String> newCookie = new HashMap<>();
            newCookie.put("JSESSIONID",stringStringMap.get("cookie"));
            WxScoreRespVO studentGrade = getStudentGrade(xh, pswd, newCookie,i);
            return studentGrade;
        }
        JSONObject jsonObject = JSON.parseObject(response.body());
        WxScoreRespVO wxScoreRespVO = new WxScoreRespVO();
        wxScoreRespVO.setSchoolCookie(cookies.get("JSESSIONID"));
        wxScoreRespVO.setEveryScore(getAllScore(jsonObject, xh));
        return wxScoreRespVO;
    }

    /**
    *
    * 组装所有成绩数据
    * @author Shawshank King
    * @date 2020-08-05 19:08
    * @param [jsonObject, studentId]
    * @return java.util.Map<java.lang.String,com.wx.xybb.vo.resp.WxTScoreRespVO>
    */
    public Map<String, WxTScoreRespVO> getAllScore(JSONObject jsonObject,String studentId){
        Set<String> remove = new HashSet();//此集合用作去除重复学年
        Map<String, WxTScoreRespVO> allScore = new LinkedHashMap<>();//学年集合
        WxTScoreRespVO teamScore = null;//学期对象
        List<WxEScoreRespVO> lastTerm = null;//上学期集合
        List<WxEScoreRespVO> nextTerm = null;//下学期集合
        String xnmmc = null;//第几学年
        String xqmmc = null;//第几学期
        JSONArray gradeTable = JSON.parseArray(jsonObject.getString("items"));
        for (Iterator iterator = gradeTable.iterator(); iterator.hasNext(); ) {
            JSONObject lesson = (JSONObject) iterator.next();

            //判断是否有此学年，如果没有则需要添加teamScore数据到year,然后new一个新的teamScore,lastTeam,nextTeam对象
            //如果有此学年，则需要把lastTerm和nextTerm数据添加到teamScore
            if(!remove.contains(lesson.getString("xnmmc"))){
                //判断是否为首次循环，首次循环不添加teamScore的数据到year
                if (!remove.isEmpty()){
                    teamScore.setLastTerm(lastTerm);
                    teamScore.setNextTerm(nextTerm);
                    allScore.put(xnmmc,teamScore);
                }
                xnmmc = lesson.getString("xnmmc");//学年
                xqmmc = lesson.getString("xqmmc");//学期
                //new学期
                teamScore = new WxTScoreRespVO();
                lastTerm = new ArrayList<>();
                nextTerm = new ArrayList<>();
                //判断是第几学期，上学期添加到lastTerm，下学期添加到nextTerm
                if (xqmmc.equals("1")){
                    WxEScoreRespVO eScore1 = getEScore(lesson, studentId);
                    lastTerm.add(eScore1);
                }else {
                    WxEScoreRespVO eScore2 = getEScore(lesson, studentId);
                    nextTerm.add(eScore2);
                }
            }else {
                xnmmc = lesson.getString("xnmmc");//学年
                xqmmc = lesson.getString("xqmmc");//学期
                //判断是第几学期，上学期添加到lastTerm，下学期添加到nextTerm
                if (xqmmc.equals("1")){
                    WxEScoreRespVO eScore1 = getEScore(lesson, studentId);
                    lastTerm.add(eScore1);
                }else {
                    WxEScoreRespVO eScore2 = getEScore(lesson, studentId);
                    nextTerm.add(eScore2);
                }
            }
            //添加学年到去重的set集合
            remove.add(xnmmc);
        }
        //将最后一个学年添加到year
        teamScore.setLastTerm(lastTerm);
        teamScore.setNextTerm(nextTerm);
        allScore.put(xnmmc,teamScore);
        return allScore;
    }

    /**
    *
    * 组装每一个科目的数据
    * @author Shawshank King
    * @date 2020-08-05 15:30
    * @param [lesson, studentId]
    * @return com.wx.xybb.vo.resp.WxEScoreRespVO
    */
    public WxEScoreRespVO getEScore(JSONObject lesson,String studentId){
        WxEScoreRespVO s = new WxEScoreRespVO();
        String kcmc = lesson.getString("kcmc");//课程名称
        String xf = lesson.getString("xf");//学分
        String cj = lesson.getString("cj");//成绩
        String bj = lesson.getString("bj");//班级
        String ksxz = lesson.getString("ksxz");//课程类型
        s.setCourse(kcmc);
        String course_type = lesson.getString("kcxzmc");
        if (course_type == null) {
            course_type = "";
        }
        s.setCourse_type(course_type);
        s.setCredits(xf);
        s.setScore(cj);
        s.setJidian(lesson.getString("jd"));
        s.setScoreType(ksxz);
        s.setTeacher(lesson.getString("jsxm"));
        //添加成绩到数据库
        if(ksxz.equals("正常考试")) {
            WxEveryScore wxEveryScore = new WxEveryScore();
            wxEveryScore.setCourse(kcmc);
            wxEveryScore.setStudentId(studentId);
            wxEveryScore.setScore(cj);
            wxEveryScore.setClassGrade(bj);
            WxRankPeople wxRankPeople = setScore(studentId, kcmc, wxEveryScore);
            if (wxRankPeople != null){
                s.setScoreRank(wxRankPeople.getScoreRank());
                s.setNumberPeople(wxRankPeople.getNumberPeople());
            }
        }
        return s;
    }


    /**
    * 添加科目数据到数据库
    * @author Shawshank King
    * @date 2020-08-09 10:11
    * [studentId, course, wxEveryScore] @param
    * @return void
    */
    public WxRankPeople setScore(String studentId,String course, WxEveryScore wxEveryScore){
        WxRankPeople wxRankPeople = wxEveryScoreMapper.selectByIdAndCourse(studentId, course);
        //查询数据库看是否有此科目的成绩，有则更新，无则添加
        if(wxRankPeople == null) {
            int i = wxEveryScoreMapper.insertSelective(wxEveryScore);
            if(i!=1){
                throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
            }
            return null;
        }else {
            int i = wxEveryScoreMapper.updateByPrimaryKeySelective(wxEveryScore);
            if (i!=1){
                throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
            }
            return wxRankPeople;
        }
    }



    public static void main(String[] args) {
//        String a = "经济管理学院~17市场营销本";
//        String[] split = a.split("~");
//        System.out.println(split[0]);
        String s = "201731900083";
        String substring = s.substring(0, 4);
        System.out.println(substring);
    }
}

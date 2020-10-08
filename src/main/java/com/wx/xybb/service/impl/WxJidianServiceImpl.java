package com.wx.xybb.service.impl;

import com.wx.xybb.entity.WxTotalScore;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.mapper.WxEveryScoreMapper;
import com.wx.xybb.mapper.WxTotalScoreMapper;
import com.wx.xybb.service.WxJidianService;
import com.wx.xybb.utils.Aes;
import com.wx.xybb.utils.ConnectJWGL;
import com.wx.xybb.utils.WxUtils;
import com.wx.xybb.vo.resp.WxJidianRespVO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shawshank King
 * @date 2020-08-10 - 10:33
 */
@Slf4j
@Service
public class WxJidianServiceImpl implements WxJidianService {
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
    @Autowired
    private WxTotalScoreMapper wxTotalScoreMapper;
    @Override

    public WxJidianRespVO getJidian(String studentId, String password, String schoolCookie) {
        Map<String, String> cookies = WxUtils.getCookie(schoolCookie);
        WxJidianRespVO studentJd = getStudentJd(studentId, password, cookies, 0);
        return studentJd;
    }
    /**
    * 从数据库抓取绩点
    * @author Shawshank King
    * @date 2020-08-10 11:25
    * [xh, pswd, cookies, i] @param
    * @return com.wx.xybb.vo.resp.WxJidianRespVO
    */
    public WxJidianRespVO getStudentJd(String xh,String pswd,Map<String, String> cookies,int i){
        Connection connection = Jsoup.connect(url+ "/jwglxt/xsxy/xsxyqk_cxXsxyqkIndex.html?gnmkdm=N105515&layout=default&su=" +xh);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        Connection.Response response = null;
        try {
            response = connection.cookies(cookies).method(Connection.Method.POST)
                    .ignoreContentType(true).execute();
        } catch (IOException e) {
            throw new BusinessException(BaseResponseCode.SCHOOL_ERROR);
        }
        if(!response.body().contains("平均学分绩点")){
            i++;
            if(i>=2){
                throw new BusinessException(BaseResponseCode.NO_SCORE);
            }
            ConnectJWGL connectJWGL = new ConnectJWGL(xh, pswd);
            connectJWGL.init();
            Map<String, String> stringStringMap = connectJWGL.checkLogin();
            Map<String, String> newCookie = new HashMap<>();
            newCookie.put("JSESSIONID",stringStringMap.get("cookie"));
            WxJidianRespVO studentJd = getStudentJd(xh, pswd, newCookie, i);
            return studentJd;
        }
        Document document = Jsoup.parse(response.body());
        Elements elements = document.select("#alertBox font:eq(1) font");
        Float jidian = Float.parseFloat(elements.text());//绩点
        WxJidianRespVO rank = getRank(xh, jidian);
        rank.setSchoolCookie(cookies.get("JSESSIONID"));
        return rank;
    }

    /**
    * 将绩点存入数据库，然后取出排名数据
    * @author Shawshank King
    * @date 2020-08-10 11:25
    * [studentId, jidian] @param
    * @return com.wx.xybb.vo.resp.WxJidianRespVO
    */
    public WxJidianRespVO getRank(String studentId,Float jidian){
        WxJidianRespVO wxJidianRespVO = new WxJidianRespVO();
        wxJidianRespVO.setJpa(jidian);
        String grade = wxEveryScoreMapper.selectClass(studentId);//从数据库查找班级
        if (grade == null){
            throw new BusinessException(BaseResponseCode.JIDIAN_TO_SCORE);
        }
        //将绩点添加到数据库
        WxTotalScore wxTotalScore1 = new WxTotalScore();
        wxTotalScore1.setStudentId(studentId);
        wxTotalScore1.setGrade(grade);
        wxTotalScore1.setJpa(jidian);
        WxTotalScore wxTotalScore = wxTotalScoreMapper.selectByPrimaryKey(studentId);
        if (wxTotalScore == null){
            int i = wxTotalScoreMapper.insertSelective(wxTotalScore1);
            if (i != 1){
                throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
            }
        }else {
            int i = wxTotalScoreMapper.updateByPrimaryKeySelective(wxTotalScore1);
            if (i != 1){
                throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
            }
            wxJidianRespVO.setJpaRank(wxTotalScore.getJpaRank());
            wxJidianRespVO.setJpaRank(wxTotalScore.getNumberPeople());
        }
        return wxJidianRespVO;
    }
}

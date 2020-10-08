package com.wx.xybb;

import com.alibaba.fastjson.JSON;
import com.wx.xybb.entity.WxEveryScore;
import com.wx.xybb.entity.WxTotalScore;
import com.wx.xybb.entity.WxUser;
import com.wx.xybb.mapper.WxEveryScoreMapper;
import com.wx.xybb.mapper.WxTotalScoreMapper;
import com.wx.xybb.mapper.WxUserMapper;
import com.wx.xybb.service.impl.WxCourseServiceImpl;
import com.wx.xybb.service.impl.WxExamServiceImpl;
import com.wx.xybb.service.impl.WxScoreSercierImpl;
import com.wx.xybb.utils.JwtTokenUtil;
import com.wx.xybb.utils.WxUtils;
import com.wx.xybb.vo.resp.WxCourseTaskRespVO;
import com.wx.xybb.vo.resp.WxExamRespVO;
import com.wx.xybb.vo.resp.WxIdScoreTaskRespVO;
import com.wx.xybb.vo.resp.WxTScoreRespVO;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyFrameApplicationTests {

    @Autowired
    private WxUtils wxUtils;
    //    private final WxUtils wxUtils = new WxUtils();
    @Value("${school_url}")
    private  String url;
    @Autowired
    private WxExamServiceImpl wxExamService;
    @Test
    public void test(){
        Map<String, String> cookies = new HashMap<>();
        cookies.put("JSESSIONID","FJAFJS23J8R9FJ2839F89IAJ");
        int[] yearAndTerm = wxUtils.getYAndTByDate();
        System.out.println(yearAndTerm[0]+"-----"+yearAndTerm[1]);
        WxExamRespVO k = wxExamService.getExamFromS("201731900083", "k15863767027", yearAndTerm[0], yearAndTerm[1], cookies, 0);
        System.out.println(k);
    }



}

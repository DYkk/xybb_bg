package com.wx.xybb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wx.xybb.constants.Constant;
import com.wx.xybb.entity.WxSchoolTimetable;
import com.wx.xybb.entity.WxUser;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.mapper.WxSchoolTimetableMapper;
import com.wx.xybb.mapper.WxUserMapper;
import com.wx.xybb.service.RedisService;
import com.wx.xybb.service.WxLoginService;
import com.wx.xybb.utils.Aes;
import com.wx.xybb.utils.ConnectJWGL;
import com.wx.xybb.utils.HttpsUtils;
import com.wx.xybb.utils.JwtTokenUtil;
import com.wx.xybb.vo.req.WxLoginReqVO;
import com.wx.xybb.vo.resp.WxLoginRespVO;
import com.wx.xybb.vo.resp.WxOpenIdRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Shawshank King
 * @date 2020-07-08 - 14:55
 */
@Service
@Slf4j
public class WxLoginServiceImpl implements WxLoginService {
    @Value("${APP_ID}")
    private String APP_ID;
    @Value("${APP_SECRET}")
    private String APP_SECRET;
    @Autowired
    private RedisService redisService;
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private WxSchoolTimetableMapper wxSchoolTimetableMapper;
    @Autowired
    private Aes aes;
    /**
     * JWT 过期时间值 这里写死为和小程序时间一致 30天
     */
    private static final long EXPIRE_TIME = 30;

    //登录
    @Override
    public WxLoginRespVO login(WxLoginReqVO wxLoginReqVO) {
        String studentId = wxLoginReqVO.getStudentId();
        //解密密码
        String password = aes.decrypt(wxLoginReqVO.getPassword());
        //模拟登陆教务系统
        Map<String, String> school = getSchool(studentId, password);
        //获取openid和unionid
        JSON openidAndUnionid = getOpenidAndUnionid(wxLoginReqVO.getCode());
        WxOpenIdRespVO wxOpenIdRespVO = JSONObject.toJavaObject(openidAndUnionid, WxOpenIdRespVO.class);
        WxLoginRespVO wxLoginRespVO = new WxLoginRespVO();
        if(!wxOpenIdRespVO.getErrcode().equals("0")){
            throw new BusinessException(BaseResponseCode.OPENID_ERROR);
        }else {
            //本地数据库查找是否有此用户
            WxUser wxUser = wxUserMapper.selectById(studentId);
            //不存在就新建用户
            WxUser wxUser1 = new WxUser();
            //复制openid和unionid
            wxUser1.setOpenid(wxOpenIdRespVO.getOpenid());
            wxUser1.setUnionid(wxOpenIdRespVO.getUnionid());
            //复制studentid、nickname、avatarurl、gender
            wxUser1.setStudentId(studentId);
            wxUser1.setNickname(wxLoginReqVO.getNickname());
            wxUser1.setAvatarurl(wxLoginReqVO.getAvatarurl());
            wxUser1.setGender(wxLoginReqVO.getGender());
            //set学生姓名
            wxUser1.setName(school.get("stuname"));
            wxUser1.setTime(new Date());
            if (wxUser == null){
               int i = wxUserMapper.insertSelective(wxUser1);
                if (i!=1){
                    throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
                }
            }else {
                int i = wxUserMapper.updateByPrimaryKeySelective(wxUser1);
                if (i!=1){
                    throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
                }
            }
            //生成小程序端token
            String token = creatTokenByWxAccount(wxOpenIdRespVO.getOpenid(), wxLoginReqVO.getStudentId(),password);
            wxLoginRespVO.setName(school.get("stuname"));
            wxLoginRespVO.setSchoolCookie(school.get("cookie"));
            wxLoginRespVO.setToken(token);
        }
        return wxLoginRespVO;
    }

    //退出登录
    @Override
    public void logout(String token) {
        if(StringUtils.isEmpty(token)){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        log.info("subject.getPrincipals()={}",subject.getPrincipals());
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        String jwtId=JwtTokenUtil.getJwtId(token);
        String studentId = JwtTokenUtil.getStudentId(token);
        //把token 加入黑名单 禁止再访问系统资源
        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST+jwtId,studentId,JwtTokenUtil.getRemainingTime(token), TimeUnit.MILLISECONDS);
        //关闭课表推送，关闭蹭课
        WxUser wxUser = new WxUser();
        wxUser.setStudentId(studentId);
        wxUser.setMessagePush(2);
        int i = wxUserMapper.updateByPrimaryKeySelective(wxUser);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        WxSchoolTimetable wxSchoolTimetable = new WxSchoolTimetable();
        wxSchoolTimetable.setStudentId(studentId);
        wxSchoolTimetable.setRubLesson(1);
        int i1 = wxSchoolTimetableMapper.updateByPrimaryKeySelective(wxSchoolTimetable);
        if(i1!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }


    //获取openid和unionid
    public JSON getOpenidAndUnionid(String code){
        StringBuffer url = new StringBuffer();
        url.append("https://api.weixin.qq.com/sns/jscode2session?appid=");
        url.append(APP_ID);
        url.append("&secret=");
        url.append(APP_SECRET);
        url.append("&js_code=");
        url.append(code);
        url.append("&grant_type=authorization_code");
        JSONObject jsonObject = HttpsUtils.doGet(url.toString());
        return jsonObject;
    }

    /**
     * 根据微信用户登陆信息创建 token
     * @param openid 用户信息
     * @return 返回 jwt token
     */
    public String creatTokenByWxAccount(String openid, String studentId,String password){
        //JWT 随机ID,做为验证的key
        String jwtId = UUID.randomUUID().toString();
        Map<String, Object> claims=new HashMap<>();
        claims.put(Constant.JWT_WX_MARK,"0");
        claims.put(Constant.JWT_WX_OPENID,openid);
        claims.put(Constant.JWT_WX_STUDENTID,studentId);
        claims.put(Constant.JWT_WX_PASSWORD,password);
        //生成token
        String token= JwtTokenUtil.getRefreshAppToken(jwtId,claims);
        return token;
    }

    /*
    * 模拟登陆教务系统
    * */
    public Map<String,String> getSchool(String studentId,String password){
        ConnectJWGL connectJWGL = new ConnectJWGL(studentId, password);
        connectJWGL.init();
        return connectJWGL.checkLogin();
    }
}

package com.wx.xybb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.xybb.entity.WxSchoolTimetable;
import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import com.wx.xybb.mapper.WxSchoolTimetableMapper;
import com.wx.xybb.service.WxCourseService;
import com.wx.xybb.utils.Aes;
import com.wx.xybb.utils.ConnectJWGL;
import com.wx.xybb.utils.QiniuUtil;
import com.wx.xybb.utils.WxUtils;
import com.wx.xybb.vo.resp.WxCourseListRespVO;
import com.wx.xybb.vo.resp.WxCourseRespVO;
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
 * @date 2020-07-26 - 10:28
 */
@Service
@Slf4j
public class WxCourseServiceImpl implements WxCourseService {

    @Autowired
    private Aes aes;
//    private WxUtils wxUtils = new WxUtils();
    @Value("${school_url}")
    private  String url;
//    private  String url = "http://211.64.32.195";
    @Value("${colorArrays}")
    private String[] colorArrays;
//    private String[] colorArrays = {"#85B8CF", "#90C652", "#D8AA5A", "#FC9F9D", "#0A9A84", "#61BC69", "#12AEF3", "#E29AAD"};
    @Value("${kaixue}")
    private String kaixue;
//    private String kaixue = "2020-02-23";
    @Autowired
    private WxSchoolTimetableMapper wxSchoolTimetableMapper;
    @Autowired
    private QiniuUtil qiniuUtil;


    @Override
    public WxCourseRespVO getMyCourse(String studentId, String password, String schoolCookie) {
        //从开学时间中获取当前年份和学期
        int[] yearAndTerm = WxUtils.getYearAndTerm(kaixue);
        Map<String, String> cookies = WxUtils.getCookie(schoolCookie);
        WxCourseRespVO studentTimetable = getStudentTimetable(studentId, password, yearAndTerm[0], yearAndTerm[1], cookies,0);
        studentTimetable.setOpenData(kaixue);
        return studentTimetable;
    }

    @Override
    public String getQNToken() {
        String upToken = qiniuUtil.getUpToken();
        return upToken;
    }


    /**
    *
    * 获取学生课表
    * @author Shawshank King
    * @date 2020-07-31 15:54
    * @param
    * @return java.util.Map<java.lang.String,java.lang.String>
    */
    public WxCourseRespVO getStudentTimetable( String xh,String pswd, int year, int term, Map<String,String> cookies,int i) {
        Connection connection = Jsoup.connect(url + "/jwglxt/kbcx/xskbcx_cxXsKb.html?gnmkdm=N2151");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        connection.data("xnm", String.valueOf(year));
        connection.data("xqm", String.valueOf(term * term * 3));
        Connection.Response response = null;
        try {
            response = connection.cookies(cookies).method(Connection.Method.POST).ignoreContentType(true).execute();
        } catch (IOException e) {
            throw new BusinessException(BaseResponseCode.SCHOOL_ERROR);
        }
        //如果cookie过期，重新获取cookie
        if(!response.body().contains("kbList")){
            i++;
            if(i>=2){
                throw new BusinessException(BaseResponseCode.NO_SCORE);
            }
            System.out.println("学号："+xh+"密码："+pswd);
            ConnectJWGL connectJWGL = new ConnectJWGL(xh, pswd);
            connectJWGL.init();
            Map<String, String> stringStringMap = connectJWGL.checkLogin();
            Map<String, String> newCookie = new HashMap<>();
            newCookie.put("JSESSIONID",stringStringMap.get("cookie"));
            WxCourseRespVO allTable = getStudentTimetable(xh, pswd, year, term, newCookie,i);
            return allTable;
        }

        JSONObject jsonObject = JSON.parseObject(response.body());
        //获取学生姓名
        JSONObject xsxx = JSON.parseObject(jsonObject.getString("xsxx"));
        String name = xsxx.getString("XM");
        //获取实践课程或其他课程
        List<Map<String,String>> otherTimeTable = getOtherTimeTable(jsonObject);
        //正常课表部分
        List<WxCourseListRespVO> newTimeTable = getNewTimeTable(jsonObject);
        //组装数据
        WxCourseRespVO allTable = new WxCourseRespVO();
        allTable.setOtherCourse(otherTimeTable);
        allTable.setCourse(newTimeTable);
        //将数据存到数据库
        storageTable(xh,name,allTable);
        allTable.setStudentName(name);
        allTable.setSchoolCookie(cookies.get("JSESSIONID"));
        return allTable;
    }


    /**
    *
    * 将数据存到数据库
    * @author Shawshank King
    * @date 2020-08-01 16:22
    * @param  studentId, name, otherTimeTable, newTimeTable
    * @return void
    */
    public void storageTable(String studentId,String name,WxCourseRespVO timeTable){
        WxSchoolTimetable wxSchoolTimetable = new WxSchoolTimetable();
        wxSchoolTimetable.setStudentId(studentId);
        wxSchoolTimetable.setName(name);
        Map<String, Object> params = new HashMap<>();
        params.put("otherCourse",timeTable.getOtherCourse());
        params.put("course",timeTable.getCourse());
        params.put("name",name);
        params.put("openData",kaixue);
        String json = JSON.toJSONString(params);//map转json字符串
        wxSchoolTimetable.setTimetable(json);
        wxSchoolTimetable.setTime(new Date());
        //查询学生课表是否存在，不存在增加，存在更新
        WxSchoolTimetable wxSchoolTimetable1 = wxSchoolTimetableMapper.selectByPrimaryKey(studentId);
        if (wxSchoolTimetable1 == null) {
            wxSchoolTimetableMapper.insertSelective(wxSchoolTimetable);
        } else {
            wxSchoolTimetableMapper.updateByPrimaryKeySelective(wxSchoolTimetable);
        }
    }
    /**
    *
    *获取实践课程或其他课程
    * @author Shawshank King
    * @date 2020-07-31 15:30
    * @param
    * @return com.alibaba.fastjson.JSONArray
    */
    public List<Map<String,String>> getOtherTimeTable(JSONObject jsonObject){
        List<Map<String,String>> otherTimeTable = new ArrayList<Map<String, String>>();
        if(otherTimeTable.isEmpty()){
            Map<String,String> sjtable = new HashMap();
            sjtable.put("courseName","暂时没有实践课程");
            otherTimeTable.add(sjtable);
        }else {
            JSONArray otherTable = JSON.parseArray(jsonObject.getString("sjkList"));
            for (Iterator iterator = otherTable.iterator(); iterator.hasNext(); ) {
                JSONObject lesson = (JSONObject) iterator.next();
                Map<String,String> sjtable = new HashMap();
                sjtable.put("courseName", lesson.getString("qtkcgs"));
                otherTimeTable.add(sjtable);
            }
        }
        return otherTimeTable;
    }
    /**
    *
    * 获取正常课表
    * @author Shawshank King
    * @date 2020-07-31 15:33
    * @param
    * @return com.alibaba.fastjson.JSONArray
    */
    public List<WxCourseListRespVO> getNewTimeTable(JSONObject jsonObject){
        Map<String,String> colorMap = new HashMap<String,String>();//设置颜色的map
        int count = 0;//颜色计数
        JSONArray timeTable = JSON.parseArray(jsonObject.getString("kbList"));
        //System.out.println(timeTable)
        List<WxCourseListRespVO> newTimeTable = new ArrayList<WxCourseListRespVO>();
        for (Iterator iterator = timeTable.iterator(); iterator.hasNext(); ) {
            JSONObject lesson = (JSONObject) iterator.next();
            WxCourseListRespVO wxCourseListRespVO = new WxCourseListRespVO();
            wxCourseListRespVO.setCourseName(lesson.getString("kcmc"));//课程名字
            wxCourseListRespVO.setCoursePlace(lesson.getString("cdmc"));//课程地点
            wxCourseListRespVO.setCourseTeacher(lesson.getString("xm"));//课程老师
            wxCourseListRespVO.setCourseType(lesson.getString("khfsmc"));//课程类型
            wxCourseListRespVO.setCourseTime(lesson.getString("zcd"));//前端显示的课程时间
            wxCourseListRespVO.setCourseLesson(lesson.getString("jc"));//第几节课上
            wxCourseListRespVO.setCourseDay(Integer.parseInt(lesson.getString("xqj")));//周几上课
            //设置课程颜色
            if (count >= colorArrays.length) {
                count = 0;//如果课程数大于颜色数则重置count
            }
            if(colorMap.get(lesson.getString("kcmc")) == null){
                int i =count++;
                colorMap.put(lesson.getString("kcmc"),colorArrays[i]);
                wxCourseListRespVO.setCourseColor(colorArrays[i]);
            }else {
                String setColor =  colorMap.get(lesson.getString("kcmc"));
                wxCourseListRespVO.setCourseColor(setColor);
            }
            //哪几周上课，全部列出
            List<Integer> zcd = getAllWeek(lesson.getString("zcd"));
            wxCourseListRespVO.setCourseAllWeeks(zcd);
            String[] jcs = lesson.getString("jcs").split("-");
            wxCourseListRespVO.setCourseStart(Integer.parseInt(jcs[0]));//是第几节上课
            wxCourseListRespVO.setCourseLength(Integer.parseInt(jcs[1]) - Integer.parseInt(jcs[0]) + 1);//课程长度，有几节课
            newTimeTable.add(wxCourseListRespVO);
        }
        return newTimeTable;
    }
    //
    public String getCookie(String cookie){
        String newcookie = cookie;
        return newcookie;
    }

    //得到周数  分四种情况 ?-?周(单) ?-?周(双) ?-?周 ?周
    public List<Integer> getAllWeek(String week){
        String[] strArr = week.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for(int index = 0;index < strArr.length;index++) {
            String type = subString(strArr[index],"(",")");
            if(type == null) {
                String[] strArr1 = subString(strArr[index],"","周").split("-");
                if(strArr1.length == 1) {
                    getWeekArr(list,strArr1[0],strArr1[0],type);
                }else {
                    getWeekArr(list,strArr1[0],strArr1[1],type);
                }
            }else {
                String[] strArr1 = subString(strArr[index],"","周").split("-");
                if(strArr1.length == 1) {
                    getWeekArr(list,strArr1[0],strArr1[0],type);
                }else {
                    getWeekArr(list,strArr1[0],strArr1[1],type);
                }
            }
        }
        return list;
    }

    /**
     * 取出字符串前后中间的字符串
     * 作者：Daen
     * @param str		原字符串
     * @param strStart	前面字符串
     * @param strEnd	后面字符串
     * @return
     */
    public  String subString(String str, String strStart, String strEnd) {
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);
        if (strStartIndex < 0) {
            return null;
        }
        if (strEndIndex < 0) {
            return null;
        }
        return str.substring(strStartIndex, strEndIndex).substring(strStart.length());
    }

    /**
     * 计算两个周之间的指定类型的周，返回文本数组
     * 作者：Daen
     * @param list		集合
     * @param weekStart	比如：5
     * @param weekEnd	比如：7
     * @param type		类型：单、双、NULL
     * @return
     */
    public  Integer[] getWeekArr(List<Integer> list,String weekStart,String weekEnd,String type) {
        if(type == null) {
            for (int i = Integer.parseInt(weekStart); i <= Integer.parseInt(weekEnd); i++) {
                list.add(i);
            }
        }else {
            if(type.equals("单")) {
                for (int i = Integer.parseInt(weekStart); i <= Integer.parseInt(weekEnd); i++) {
                    if (i % 2 != 0) {
                        list.add(i);
                    }
                }
            }else {
                for (int i = Integer.parseInt(weekStart); i <= Integer.parseInt(weekEnd); i++) {
                    if (i % 2 == 0) {
                        list.add(i);
                    }
                }
            }
        }

        return list.toArray(new Integer[0]);
    }

    public static void main(String[] args) {
        WxCourseServiceImpl wxCourseService = new WxCourseServiceImpl();
        Map<String, String> cookies = new HashMap<>();
        cookies.put("JSESSIONID","27AC854EC76D34018C230A7835FFD24C");
        wxCourseService.getStudentTimetable("201731900083", "k15863767027", 2020, 1, cookies,1);


    }
}

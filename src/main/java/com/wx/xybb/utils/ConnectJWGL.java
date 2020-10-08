package com.wx.xybb.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.wx.xybb.exception.BusinessException;
import com.wx.xybb.exception.code.BaseResponseCode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

public class ConnectJWGL {

    private final String url = "http://211.64.32.195";
    private Map<String, String> cookies = new HashMap<>();
    private String modulus;
    private String exponent;
    private String csrftoken;
    private Connection connection;
    private Connection.Response response;
    private Document document;
    private String stuNum;
    private String password;
    private String insertCookie;


    public ConnectJWGL(String stuNum, String password) {
        this.stuNum = stuNum;
        this.password = password;
    }

    public void init(){
        getCsrftoken();
        getRSApublickey();
    }

    // 获取csrftoken和Cookies
    private void getCsrftoken() {
        try {
            connection = Jsoup.connect(url + "/jwglxt/xtgl/login_slogin.html?language=zh_CN&_t=");
            connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            response = connection.execute();
            cookies = response.cookies();
            System.out.println("=====最原始的cookie====="+cookies);
            //保存csrftoken
            document = Jsoup.parse(response.body());
            csrftoken = document.getElementById("csrftoken").val();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(BaseResponseCode.SCHOOL_ERROR);
        }
    }

    // 获取公钥并加密密码
    public void getRSApublickey(){
        connection = Jsoup.connect(url + "/jwglxt/xtgl/login_getPublicKey.html");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        try {
            response = connection.cookies(cookies).ignoreContentType(true).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(BaseResponseCode.SCHOOL_ERROR);
        }
        JSONObject jsonObject = JSON.parseObject(response.body());
        modulus = jsonObject.getString("modulus");
        exponent = jsonObject.getString("exponent");
        password = RSAEncoder.RSAEncrypt(password, B64.b64tohex(modulus), B64.b64tohex(exponent));
        password = B64.hex2b64(password);
    }

    //检查登录登录
    public Map<String, String> checkLogin(){
        Map<String, String> map = new HashMap();
        connection = Jsoup.connect(url + "/jwglxt/xtgl/login_slogin.html");
        connection.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        connection.data("csrftoken", csrftoken);
        connection.data("yhm", stuNum);
        connection.data("mm", password);
        System.out.println("检查登录前"+cookies);
        try {
            response = connection.cookies(cookies).ignoreContentType(true).followRedirects(true)
                    .method(Connection.Method.POST).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(BaseResponseCode.SCHOOL_ERROR);
        }
        document = Jsoup.parse(response.body());
        if (document.html().contains("退出")) {
            System.out.println("欢迎登陆");
            cookies = response.cookies();
            System.out.println("检查登录"+cookies);
            String stuname = getStudentInformaction();
            map.put("stuname", stuname); //返回学生姓名
            map.put("cookie", cookies.get("JSESSIONID") /*+ "," + cookies.get("insert_cookie")*/);
        } else {
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }
        return map;
    }

    // 查询学生信息
    public String getStudentInformaction(){
        connection = Jsoup.connect(url + "/jwglxt/xsxxxggl/xsgrxxwh_xgXsgrxx.html?gnmkdm=N100808&layout=default&su=");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        connection.header("Cookie", getCookieHeader(cookies));
        System.out.println("查询学生"+cookies);
        try {
            response = connection.cookies(cookies).ignoreContentType(true).method(Connection.Method.GET).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(BaseResponseCode.SCHOOL_ERROR);
        }
        Document doc = Jsoup.parse(response.body());
        return doc.getElementsByClass("form-control-static").get(1).text();
    }

    /**
     * 把cookie转换成header头
     *
     * @param cookies
     * @return
     */
    public static String getCookieHeader(Map<String, String> cookies) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!cookies.isEmpty()) {
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                stringBuffer = stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            }
        }
        return stringBuffer.toString();
    }
}

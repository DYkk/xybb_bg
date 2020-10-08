package com.wx.xybb.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.print.DocFlavor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shawshank King
 * @date 2020-07-26 - 16:22
 */

public class WxUtils {
    /**
     * 计算两个日期之间相差的天数
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算现在是第几周
     */
    public static Map<String, Object> getNowWeek(String kaixue) {
        Map<String, Object> map = new HashMap<String, Object>();
        //计算现在是第几周
        int now = 0;
        String month = "5";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date d1 = sdf.parse(kaixue);
            Date d = new Date();
            String dateNowStr = sdf.format(d);
            Date d2 = sdf.parse(dateNowStr);
            month = dateNowStr.substring(5, 7);
            if (month.contains("0")) {
                month = month.substring(1, 2);
            }
            if (now == 0) {
                now = daysBetween(d1, d2) / 7 + 1;
                if (now > 21) {
                    now = 1;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        map.put("now", now);
        map.put("month", month);
        return map;
    }

    /**
     * 从开学时间获取当前的年份和学期
     */
    public static int[] getYearAndTerm(String kaixue) {
        int yearAndTerm[] = new int[2];
        int term = 1;
        int year = Integer.valueOf(kaixue.substring(0, 4));
        int month = Integer.valueOf(kaixue.substring(5, 7));
        if (month >= 2 && month <= 7) {
            year = year - 1;
            term = 2;
        }
        yearAndTerm[0] = year;
        yearAndTerm[1] = term;
        return yearAndTerm;
    }

    /**
     * 通过时间获取当前的年份和学期
     */
    public static int[] getYAndTByDate() {
        int yearAndTerm[] = new int[2];
        int term = 1;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        if (month >= 3 && month <= 8) {
            year = year - 1;
            term = 2;
        }
        yearAndTerm[0] = year;
        yearAndTerm[1] = term;
        return yearAndTerm;
    }

    /**
     * 判断是否是数字
     */
    public static boolean isNumericZidai(String str) {
        System.out.println("数字文字"+ str);
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
    *
    * 组装cookie
    * @author Shawshank King
    * @date 2020-07-31 16:02
    * @param schoolCookie
    * @return java.util.Map<java.lang.String,java.lang.String>
    */
    public static Map<String,String> getCookie(String schoolCookie){
        Map<String,String> newCookie = new HashMap<>();
        newCookie.put("JSESSIONID",schoolCookie);
        return newCookie;
    }
}

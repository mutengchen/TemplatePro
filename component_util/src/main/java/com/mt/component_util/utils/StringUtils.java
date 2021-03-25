package com.mt.component_util.utils;

import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created by cmt on 2019/5/22
 * 字符串处理类
 */
public class StringUtils {
    public static final String DEFAULT_QUERY_REGEX = "-";
    public static String isContainsEnglish = ".*[a-zA-z\\*-].*";
    public static String WynnTimeFormat(String time){
        String[] temp = time.split("-|T");
        time = temp[2]+"/"+temp[1]+"/"+temp[0];
        return time;
    }
    //切割出ip地址
    public static String splitStrIp(String str){
        if(str==null||str.equals(""))return "";
        String ipres =str.split("://|:")[1];
        Log.i("ipres",ipres);
        return ipres;
    }
    //首字母大写
    public static String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    //处理千分位字符串，省略小数点后的数字
    public static String dealDataFormat(String a){
        double temp = 0;
        if(a==null){
            return "-";
        }else{
            if((!a.equals("-")&&!a.matches(isContainsEnglish))||specialSymbols(a)){
                temp = Double.parseDouble(a);
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                return numberFormat.format((int)temp);
            }
        }
        return a;
    }
    //处理千分位字符串，保留小数点后两位
    public static String changDataFormatDouble(String a){
        double temp = 0;
        if(a==null){
            return "-";
        }else{
            if((!a.equals("-")&&!a.matches(isContainsEnglish))||specialSymbols(a)){
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setMaximumFractionDigits(2);
                temp = Double.parseDouble(a);
                return numberFormat.format(temp);
            }
        }
        return a;
    }

    public static String setHourFormat(String data){
        if(data.equals("-"))return data;
        String res = "";
        int hour = Integer.parseInt(data);
        res =  (hour>12) ? hour%12+" PM":hour+" AM";
        if(hour==24) res = "0 AM";
        return res;
    }
    public static String dealDataNullBackZero(String a){
        if(a==null){
            return "0";
        }
        else{
            if((!a.equals("-")&&!a.matches(isContainsEnglish))||specialSymbols(a)){
                return a;
            }
        }
        return a;
    }
    public static boolean specialSymbols(String value) {
        if(value.length()==1){
            return false;
        }
        Pattern pattern = Pattern.compile(DEFAULT_QUERY_REGEX);
        Matcher matcher = pattern.matcher(value);

        char[] specialSymbols = DEFAULT_QUERY_REGEX.toCharArray();

        boolean isStartWithSpecialSymbol = false; // 是否以特殊字符开头
        for (int i = 0; i < specialSymbols.length; i++) {
            char c = specialSymbols[i];
            if (value.indexOf(c) == 0) {
                isStartWithSpecialSymbol = true;
                break;
            }
        }

        return matcher.find() && isStartWithSpecialSymbol;
    }

    public static String getYYMMDDHHMM(){
        Calendar cd = Calendar.getInstance();
        return ""+cd.get(Calendar.YEAR)+(cd.get(Calendar.MONTH)+1)+(cd.get(Calendar.DATE))+(cd.get(Calendar.HOUR)+(cd.get(Calendar.MINUTE)));
    }



    //获取当天时间的时间戳
    public static long getTodayHHMMTimeStamp(String target){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        date=date+" "+target;
        SimpleDateFormat res = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date time = res.parse(date);
            return time.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String datestrChangFormat(String target, String format){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = sdf.parse(target);
            long time = d.getTime();
            SimpleDateFormat sdf_new = new SimpleDateFormat(format);
            return sdf_new.format(new Date(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return target;
        }
    }
    public static String getSystemDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * string 转换成对应的时间戳，long类型
     * @param target 2000-02-03 12:22:22
     * @param format yyyy-MM-dd HH:mm:ss
     * @return 180928147
     */
    public static long getTimeStampByStr(String target,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);// HH:mm:ss
        Date date = null;
        //获取当前时间
        try {
            date = simpleDateFormat.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime()/1000;
    }

    /**
     * string 转换成date对象
     * @param target 2000-02-03 12:22:22
     * @param format yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getTimeDateByStr(String target,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);// HH:mm:ss
        Date date = null;
        //获取当前时间
        try {
            date = simpleDateFormat.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long getYYYYMMddHHmmTimeStampByStr(String target){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// HH:mm:ss
        Date date = null;
        //获取当前时间
        try {
            date = simpleDateFormat.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime()/1000;
    }
    public static String getTime(String user_time, String timeFormat) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }

    //判断时间是否在时间段内
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if ((date.after(begin) && date.before(end))||(date.equals(begin)||date.equals(end))) {
            return true;
        } else {
            return false;
        }
    }

    //GMT时间转换
    public static String stampToDate(String GMT) {

        long lt = Long.parseLong(GMT)+8*3600;
        String res = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm");

            res = simpleDateFormat.format(lt*1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }

}

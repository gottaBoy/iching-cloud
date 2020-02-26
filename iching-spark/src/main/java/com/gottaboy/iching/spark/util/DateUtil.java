package com.gottaboy.iching.spark.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/10 0010.
 */
public class DateUtil {
    public static long getbydateformt(long time,String dateFormat){
            DateFormat dateformat = new SimpleDateFormat(dateFormat);
            String datestring = dateformat.format(new Date(time));
        long resulttime = 0l;
        try {
            Date date = dateformat.parse(datestring);
            resulttime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resulttime;
    }
}

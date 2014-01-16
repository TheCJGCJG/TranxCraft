
package com.wickedgaminguk.TranxCraft;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TCP_Time {
    public static final int second = 1;
    public static final int minute = second * 60;
    public static final int hour = minute * 60;
    public static final int day = hour * 24;
    public static final int week = day * 7;
    public static final int month = week * 4;
    public static final int year = month * 12;    
    
    public static String getDate() {    
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String date = sdf.format(new Date());
        return date;
    }
    
}

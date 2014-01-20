
package com.wickedgaminguk.TranxCraft;

import java.text.SimpleDateFormat;
import java.util.*;

public class TCP_Time {
    private static final Calendar UK = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
    private static final Date time = UK.getTime(); 
    
    public static final int year = UK.get(Calendar.YEAR);
    public static final int month = UK.get(Calendar.MONTH);
    public static final int week = UK.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    public static final int day = UK.get(Calendar.DAY_OF_MONTH);
    public static final int hour = UK.get(Calendar.HOUR);
    public static final int minute = UK.get(Calendar.MINUTE);
    public static final int second = UK.get(Calendar.SECOND);
    public static final int msecond = UK.get(Calendar.MILLISECOND);
    
    public static String getDate() {    
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");
        String date = sdf.format(time);
        return date;
    }
    
    public static String getLongDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String date = sdf.format(time);
        return date;
    }
    
}

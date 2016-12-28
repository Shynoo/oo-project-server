package com.github.shynoo.ooad.util;

import java.util.Date;

public class DateUtil{
    final static long dayMillis = 1000*60*60*24;
    
    public static int defferentNumber(Date d1, Date d2){
        return (int) ((d1.getTime()-d2.getTime())/dayMillis);
    }
    
    
}

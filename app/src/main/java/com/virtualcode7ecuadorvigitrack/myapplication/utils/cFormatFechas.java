package com.virtualcode7ecuadorvigitrack.myapplication.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class cFormatFechas
{
    public static String Timestamp_from_String(long timestamp)
    {
        Date mDate = new Date(timestamp);
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("American/Mexico"));
        return mSimpleDateFormat.format(mDate);
    }
}

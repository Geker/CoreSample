package org.corejava.tz;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneChange {
    public static void main(String[] args) {
        Calendar cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        System.out.println(cSchedStartCal.getTimeZone());
        Date dt0 = cSchedStartCal.getTime();
        cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Date dt1 = cSchedStartCal.getTime();
        System.out.println(dt0.getTime());
        System.out.println(dt1.getTime()-dt0.getTime());

        System.out.println(cSchedStartCal.getTimeZone());
        long gmtTime = cSchedStartCal.getTime().getTime();

        long timezoneAlteredTime = gmtTime + TimeZone.getTimeZone("Asia/Calcutta").getRawOffset();
        Calendar cSchedStartCal1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        cSchedStartCal1.setTimeInMillis(timezoneAlteredTime);
    }
}

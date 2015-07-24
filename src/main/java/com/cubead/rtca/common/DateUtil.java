package com.cubead.rtca.common;

/**
 * Created by xiaoao on 5/7/15.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class DateUtil {

    protected static Log logger = LogFactory.getLog(DateUtil.class);

    public static String getYesterday() {
        return getDate(-1);
    }

    public static String getToday() {
        return getDate(0);
    }

    public static String getDate(int days) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(currentTime.getTime());
    }
    public static String convertDate(Long uTime,SimpleDateFormat dateFormat) {
        Date date= new Date(uTime);
        return dateFormat.format(date);
    }

    public static List<String> getAllDates(String startDate, String endDate) {
        try {
            List<String> list = new ArrayList<String>();
            Date sDate = null;
            Date eDate = null;
            if (StringUtils.isEmpty(startDate)) {
                sDate = new Date();
            }
            else {
                sDate = DateUtils.parseDate(startDate, new String[] { "yyyy-MM-dd" });
            }
            if (StringUtils.isEmpty(endDate)) {
                eDate = new Date();
            }
            else {
                eDate = DateUtils.parseDate(endDate, new String[] { "yyyy-MM-dd" });
            }

            if(sDate.after(eDate)) return list;

            while (!DateUtils.isSameDay(sDate, eDate)) {
                list.add(DateFormatUtils.format(sDate, "yyyy-MM-dd"));
                sDate = DateUtils.addDays(sDate, 1);
            }

            list.add(endDate);

            return list;
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}


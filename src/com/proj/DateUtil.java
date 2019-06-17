package com.proj;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    public DateUtil() {
    }
    public static Date convertStringIntoSqlDate(String dateIn) {
        if (dateIn != null && !dateIn.isEmpty() && !dateIn.equals("null") && dateIn.length() != 0) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dateInUtil = format.parse(dateIn);
                Date dateOut = new Date(dateInUtil.getTime());
                return dateOut;
            } catch (ParseException var4) {
                var4.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
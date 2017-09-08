package co.jp.nej.earth.util;

import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static co.jp.nej.earth.model.constant.Constant.DatePattern;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

    public static final int COUNTITEM = 7;
    public static final int FRIST = 0;

    public static final int TWO = 1;
    public static final int THREE = 2;
    public static final int FOUR = 3;
    public static final int FIVE = 4;
    public static final int SIX = 5;
    public static final int END_INDEX_MONTH = 6;
    public static final int SEVEN = 6;
    public static final int END_INDEX_YEAR = 4;
    public static final int END_INDEX_DAY = 8;
    public static final int END_INDEX_HOUR = 10;
    public static final int END_INDEX_MINUTE = 12;
    public static final int END_INDEX_SECOND = 14;
    public static final int END_INDEX = 17;
    public static final int DATE_LENGTH = 14;

    /**
     * Dateを指定のフォーマットへ変換.
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String convertSimpleDateFormat(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String getCurrentDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * Get System Date.
     *
     * @return
     */
    // Get current date as string
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * Get current date as string.
     *
     * @return
     */
    public static String getCurrentDateString() {

        return new SimpleDateFormat(DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS).format(getCurrentDate());
    }

    public static String getCurrentShortDateString() {

        return new SimpleDateFormat(DatePattern.YYYYMMDD).format(getCurrentDate());
    }

    public static String convertSimpleDateFormat(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * Convert Time to format yyyy/MM/dd HH:mm:ss.SSS
     *
     * @param date Date with format yyyy/MM/dd HH:mm:ss
     * @param pattern yyyy/MM/dd HH:mm:ss.SSS
     * @return
     * @throws ParseException
     */
    public static String convertDateTimeFormat(String date, String pattern) throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat(pattern);
        DateFormat inputFormat = new SimpleDateFormat(Constant.DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

        return outputFormat.format(inputFormat.parse(date));
    }

    /**
     * @param stringDate
     * @param pattern
     * @return
     * @throws Exception
     */
    public static Date convertStringSimpleDateFormat(String stringDate, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(stringDate);
    }

    public static boolean isDate(String dateTime) {
        boolean flag = true;

        if(dateTime.matches(Constant.DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_REGEX)){
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            try {
                dateFormat.parse(dateTime.trim());
            } catch (ParseException pe) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static boolean isDate(String dateTime, String pattern) {
        if (EStringUtil.isEmpty(dateTime)) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            sdf.parse(dateTime);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public static String convertStringToDateFormat(String stringDate) throws Exception {
        String dateString = "";
        if (stringDate != null&&stringDate.length()>=DATE_LENGTH) {
            String[] inputObj = new String[COUNTITEM];
            inputObj[FRIST] = stringDate.substring(0, END_INDEX_YEAR);
            inputObj[TWO] = stringDate.substring(END_INDEX_YEAR, END_INDEX_MONTH);
            inputObj[THREE] = stringDate.substring(END_INDEX_MONTH, END_INDEX_DAY);
            inputObj[FOUR] = stringDate.substring(END_INDEX_DAY, END_INDEX_HOUR);
            inputObj[FIVE] = stringDate.substring(END_INDEX_HOUR, END_INDEX_MINUTE);
            inputObj[SIX] = stringDate.substring(END_INDEX_MINUTE, END_INDEX_SECOND);

            dateString = String.format("%-4s/%-2s/%-2s %-2s:%-2s:%-2s", inputObj);
        }
        return dateString;
    }

    public static String convertDateToStringFormat(String stringDate) {
        String dateString = stringDate.replace("/", "").replace(":", "").replace(" ", "");
        return dateString;
    }
}

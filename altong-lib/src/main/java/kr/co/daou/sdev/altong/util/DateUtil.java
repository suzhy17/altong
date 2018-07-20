package kr.co.daou.sdev.altong.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @FileName DateUtil.java
 * @Project bizweb
 * @Date 2017. 3. 10.
 * @Author jungminji
 * @Comment 날짜관련 유틸
 *
 */
public class DateUtil {
	
	/**
	 * @Method Name unixTimeToYmdHms
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment timestamp를 yyyyMMddHHmmss 포맷으로 변환
	 *
	 * @param unixSeconds
	 * @return
	 */
	public static String unixTimeToYmdHms(long unixSeconds) {
		Date date = new Date(unixSeconds*1000L); 
		return DateFormatUtils.format(date, "yyyyMMddHHmmss");
	}
	public static String unixTimeToYmdHmsWithDash(long unixSeconds) {
		Date date = new Date(unixSeconds*1000L); 
		return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * @Method Name getYmdHms
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment 현재시간을 yyyyMMddHHmmss 포맷으로 조회
	 *
	 * @return
	 */
	public static String getYmdHms() {
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
	}
	
	/**
	 * @Method Name getYmd
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment 현재시간을 yyyyMMdd 포맷으로 조회
	 *
	 * @return
	 */
	public static String getYmd() {
		return DateFormatUtils.format(new Date(), "yyyyMMdd");
	}
	
	/**
	 * @Method Name getNow
	 * @Date 2017. 4. 27.
	 * @Author jungminji
	 * @Comment 현재시간을 format 형식으로 조회
	 * (예: DateUtil.getNow("yyyy-MM-dd HH:mm:ss");)
	 * @return
	 */
	public static String getNow(String format) {
		return DateFormatUtils.format(new Date(), format);
	}
	
	/**
	 * @Method Name addTime
	 * @Date 2017. 4. 17.
	 * @Author jungminji
	 * @Comment
	 * (예1: DateUtil.addTime(Calendar.HOUR, 1, "yyyy-MM-dd HH:mm:ss"); // 현재시간에서 1시간 후 yyyy-MM-dd HH:mm:ss 조회)
	 * (예2: DateUtil.addTime(Calendar.MINUTE, 1, "yyyy-MM-dd HH:mm:ss"); // 현재시간에서 1분 후 yyyy-MM-dd HH:mm:ss 조회)
	 * (예3: DateUtil.addTime(Calendar.SECOND, 10, "yyyy-MM-dd HH:mm:ss"); // 현재시간에서 10초 후 yyyy-MM-dd HH:mm:ss 조회)
	 * @param calendar
	 * @param time
	 * @param format
	 * @return
	 */
	public static String addTime(int calendar, int time, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(calendar, time);
		return DateFormatUtils.format(cal, format);
	}
	
	/**
	 * @Method Name addDay
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment "현재 + day" 조회 
	 * (예: DateUtil.addDay(1, "yyyyMMdd"); // if 현재 = 20170101, 결과 = 20170102
	 *
	 * @param day
	 * @param format
	 * @return
	 */
	public static String addDay(int day, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, day);
		return DateFormatUtils.format(cal, format);
	}
	
	/**
	 * @Method Name addDay
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment "fromdate + day" 조회 
	 * (예: DateUtil.addDay("20170101", 1); // 결과 = 20170102)
	 *
	 * @param fromDate
	 * @param day
	 * @return
	 */
	public static String addDay(String fromDate, int day) {
		Calendar cal = Calendar.getInstance();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			cal.setTime(dateFormat.parse(fromDate));
			cal.add(Calendar.DAY_OF_MONTH, day);
		} catch (ParseException e) {
			return null;
		}
		return DateFormatUtils.format(cal, "yyyyMMdd");
	}
	
	/**
	 * @Method Name addMonth
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment "현재월 + month" 조회 
	 * (예: DateUtil.addMonth(1, "yyyyMM"); // 현재월 = 201703, 결과 = 201704)
	 *
	 * @param month
	 * @param format
	 * @return
	 */
	public static String addMonth(int month, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, month);
		return DateFormatUtils.format(cal, format);
	}
	
	/**
	 * @Method Name addMonth
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment "fromdate + month" 조회 
	 * (예: DateUtil.addMonth("201701", 1); // 결과 = 201702)
	 *
	 * @param fromDate
	 * @param month
	 * @return
	 */
	public static String addMonth(String fromDate, int month) {
		Calendar cal = Calendar.getInstance();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			cal.setTime(dateFormat.parse(fromDate));
			cal.add(Calendar.MONTH, month);
		} catch (ParseException e) {
			return null;
		}
		return DateFormatUtils.format(cal, "yyyyMM");
	}
	
	/**
	 * @Method Name maxDayOfMonth
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment month 월의 마지막날 조회
	 * (예: DateUtil.maxDayOfMonth("201703"); // 결과 = 31)
	 * 
	 * @param month
	 * @return
	 */
	public static int maxDayOfMonth(String month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Integer.parseInt(month) -1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * @Method Name maxDayOfMonth
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment 현재 월의 마지막날 조회 
	 * (예: DateUtil.maxDayOfMonth(); // 현재가 3월인 경우, 결과 = 31)
	 *
	 * @return
	 */
	public static int maxDayOfMonth() {
		String month = DateFormatUtils.format(new Date(), "yyyyMM").substring(4,6);
		return maxDayOfMonth(month);
	}
	
	/**
	 * @Method Name addMinute
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment "fromdate 시간 + monute 분" 조회 
	 * (예: DateUtil.addMinute("20170101000000", 10, "yyyyMMddHHmmss"); // 결과 = 20170101001000)
	 * 
	 * @param fromDate
	 * @param minute
	 * @param format
	 * @return
	 */
	public static String addMinute(String fromDate, int minute, String format) {
		Calendar cal = Calendar.getInstance();
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			cal.setTime(dateFormat.parse(fromDate));
			cal.add(Calendar.MINUTE, minute);
		} catch (Exception e) {
			return null;
		}
		return DateFormatUtils.format(cal, format);
	}
	
	/**
	 * @Method Name addMinute
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment "현재시간 + monute 분" 조회 
	 * (예: DateUtil.addMinute(10, "yyyyMMddHHmmss"); //현재 시간보다 10분후 시간 조회)
	 *
	 * @param minute
	 * @param format
	 * @return
	 */
	public static String addMinute(int minute, String format) {
		String fromDate = DateFormatUtils.format(new Date(), format);
		return addMinute(fromDate, minute, format);
	}
	
	/**
	 * @Method Name getTimeStamp
	 * @Date 2017. 3. 10.
	 * @Author jungminji
	 * @Comment 현재 timestamp 조회
	 *
	 * @return
	 */
	public static int getTimeStamp(){
		long unixTime = System.currentTimeMillis() / 1000L;
		return (int)unixTime;
	}
	
	/**
	 * @Method Name getTimeStamp
	 * @Date 2017. 4. 6.
	 * @Author jungminji
	 * @Comment yyyymmddhhiiss를 timestamp로 조회
	 *
	 * @param date
	 * @return
	 */
	public static int getTimeStamp(String date){
		Calendar cal = Calendar.getInstance();
		int timestamp;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			cal.setTime(dateFormat.parse(date));
			timestamp = (int)(cal.getTimeInMillis() /1000L);
		} catch (ParseException e) {
			timestamp = 0;
		}
		return timestamp;
	}
	
	public static String unixTimeToYmd(long unixSeconds) {
		Date date = new Date(unixSeconds*1000L); 
		return DateFormatUtils.format(date, "yyyyMMdd");
	}
	
	
	public static long ymdToUnixTime(String ymd) throws ParseException {
		return DateUtils.parseDate(ymd, "yyyyMMdd").getTime()/1000L;
	}
	
	public static String unixTimeToYmdWithDash(long unixSeconds) {
		Date date = new Date(unixSeconds*1000L); 
		return DateFormatUtils.format(date, "yyyy-MM-dd");
	}
	
	
	public static long ymdToUnixTimeWithDash(String ymd) throws ParseException {
		return DateUtils.parseDate(ymd, "yyyy-MM-dd").getTime()/1000L;
	}

	/**
	 * @Method Name appendStartHHmmss
	 * @Date 2017. 3. 30.
	 * @Author mayard
	 * @Comment ymd 타입형식에 시분초(000000) 을 붙여준다.
	 *
	 * @return
	 */
	public static String appendStartHHmmss(String ymd) {

		if (StringUtils.isEmpty(ymd)) {
			return ymd;
		}

		ymd = ymd.replaceAll("[-./]", "");
		return ymd + "000000";
	}

	/**
	 * @Method Name appendEndHHmmss
	 * @Date 2017. 3. 30.
	 * @Author mayard
	 * @Comment ymd 타입형식에 시분초(235959) 을 붙여준다.
	 *
	 * @return
	 */
	public static String appendEndHHmmss(String ymd) {

		if (StringUtils.isEmpty(ymd)) {
			return ymd;
		}

		ymd = ymd.replaceAll("[-./]", "");
		return ymd + "235959";
	}

	/**
	 * @Method Name convertStringDate
	 * @Date 2017. 5. 08.
	 * @Author mayard
	 * @Comment format 형식의 문자열 날짜(stringDate)를 convertFormat 형식으로 변경한다.
	 *
	 * @return
	 */
	public static String convertStringDate(String stringDate, String format, String convertFormat) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(format);
		DateFormat newFormat = new SimpleDateFormat(convertFormat);

		Date date = dateFormat.parse(stringDate);

		return newFormat.format(date);
	}
}

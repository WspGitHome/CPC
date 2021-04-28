package com.wanfangdata.cpc.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期格式化类
 */
@SuppressWarnings("unused")
public class DateUtil {
	private static final long serialVersionUID = 1L;

	private static final DateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat dateChinaFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm");

	private static String strFormat1 = "yyyy-MM-dd HH:mm";
	private static String strFormat2 = "yyyy-MM-dd";
	private static String strFormat3 = "yyyy/MM/dd";
	private static String strFormat4 = "yyyy/MM/dd HH:mm";
	private static String strFormat5 = "HH:mm";
	private static String strFormat6 = "h:mm a";
	private static String strFormat7 = "yyyyMMddHHmmss";
	private static String strFormat8 = "yyyy-MM-dd HH:mm:ss";
	private static String strFormat9 = "ddMMMyy";
	private static String strFormat10 = "yyyyMMdd";
	private static String strFormat15 = "yyMM";
	private static String strFormat17 = "MM/dd/yyyy";
	private static String strFormat18 = "dd/MM/yyyy";
	private static String strFormat19 = "yy-MM-dd";
	private static String strFormat20 = "yyyyMM";
	private static String strFormat21 = "HHmmss";
	private static String strFormat22 = "yyyy-MM";
	private static String strFormat23 = "yyyy年MM月dd日";
	private static String strFormat24 = "yyyyMMdd HH:mm:ss";
	private static String strFormat25 = "MM月dd日";
	private static String strFormat26 = "yyyy-MM";
	private static String strFormat27 = "yyyy-MM-dd HH:mm:ss:SS";

	/**
	 * M 年中的月份 Month July; Jul; 07 w 年中的周数 Number 27 W 月份中的周数 Number 2 D 年中的天数 Number 189 d 月份中的天数 Number 10 F 月份中的星期
	 * Number 2 E 星期中的天数 Text Tuesday; Tue a Am/pm 标记 Text PM H 一天中的小时数（0-23） Number 0 k 一天中的小时数（1-24） Number 24 K am/pm
	 * 中的小时数（0-11） Number 0 h am/pm 中的小时数（1-12） Number 12 m 小时中的分钟数 Number 30 s 分钟中的秒数 Number 55 S 毫秒数 Number 978 z 时区
	 * General time zone Pacific Standard Time; PST; GMT-08:00 Z 时区 RFC 822 time zone -0800
	 */

	/**
	 * 当前时间
	 *
	 * @return 时间Timestamp
	 */
	public static Timestamp parseTime() {
		return new Timestamp(getSystemDate().getTime().getTime());
	}

	/**
	 * 当前时间
	 *
	 * @return 时间Date
	 */
	public static java.sql.Date parseDate() {
		return new java.sql.Date(getSystemDate().getTime().getTime());
	}

	/**
	 * 取当前系统时间
	 *
	 * @return 时间Calendar
	 */
	public static Calendar getSystemDate() {
		return Calendar.getInstance();
	}

	/**
	 * 将Date类型转换为字符串 yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 * @return String
	 */
	public static String format(Date date) {
		return format(date, null);
	}

	/**
	 * 将Date类型转换为字符串 yyyy年MM月dd日HH:mm
	 *
	 * @param date
	 * @return String
	 */
	public static String formatChinaDate(Date date) {
		if (date == null) {
			return "";
		}
		return dateChinaFormat.format(date);
	}

	/**
	 * 将字符串 yyyy年MM月dd日HH:mm转换为Date类型
	 *
	 * @param sDate
	 * @return String
	 */
	public static Date formatChinaDate(String sDate) {
		if (sDate != null && !sDate.equals("")) {
			try {
				return dateChinaFormat.parse(sDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将Date类型转换为字符串
	 *
	 * @param date
	 * @param pattern
	 *            字符串格式
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";

		} else if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			return datetimeFormat.format(date);

		} else {
			return new SimpleDateFormat(pattern).format(date);
		}
	}

	/**
	 * 将Date类型转换为字符串 yyyy-MM-dd
	 *
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormat.format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 *
	 * @param sDate
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date convert(String sDate) {

		try {
			if (sDate != null) {
				if (sDate.length() == 10) {
					return dateFormat.parse(sDate);
				} else if (sDate.length() == 19) {
					return datetimeFormat.parse(sDate);
				}
			}
		} catch (ParseException pe) {
		}
		return convert(sDate, null);
	}

	/**
	 * 将字符串转换为Date类型
	 *
	 * @param sDate
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Date convert(String sDate, String pattern) {
		Date date = null;
		try {
			if (sDate == null || sDate.equals("") || sDate.equals("null")) {
				return null;

			} else if (pattern == null || pattern.equals("") || pattern.equals("null")) {
				return datetimeFormat.parse(sDate);

			} else {
				return new SimpleDateFormat(pattern).parse(sDate);
			}
		} catch (ParseException pe) {
		}
		return date;
	}

	/**
	 * String转换为Date
	 *
	 * @param dateStr
	 *            日期"yyyy-MM-dd"
	 * @return 日期Date
	 */
	public static Date convertDate(String dateStr) {
		return convert(dateStr, "yyyy-MM-dd");
	}

	/**
	 * String转换为Timestamp
	 *
	 * @param sDate
	 *            日期 "yyyy-MM-dd" / "yyyy-MM-dd HH:mm:ss"
	 * @return 日期Timestamp
	 */
	public static Timestamp convertTimestamp(String sDate) {
		if ("".equals(StringUtil.nvl(sDate))) {
			return null;
		}
		if (sDate.length() == 10) {
			sDate = sDate + " 00:00:00";
		}
		if (sDate.length() == 16) {
			sDate = sDate + ":00";
		}
		return Timestamp.valueOf(sDate);
	}

	/**
	 * 转换类型
	 *
	 * @param sDate
	 *            日期
	 * @return 日期Timestamp
	 */
	public static Timestamp convertTimestampE(String sDate) {

		if (sDate.length() == 10) {
			sDate = sDate + " 23:59:59";
		}

		return Timestamp.valueOf(sDate);
	}

	/**
	 * 用于将 yyy-mm-dd 格式 转换为 yyyy-mm-dd yyyy-mm-dd hh24:mi:ssxff 格式 传入字符串日期格式，如2011-12-07 根据isFirst的值决定返回 2011-12-07
	 * 00:00:00.0 还是 2011-12-07 23:59:59.0
	 *
	 * @param date
	 * @param isFirst
	 * @return
	 */
	public static String converStringTimestatmp(String date, boolean isFirst) {

		if ("".equals(date) || null == date) {
			return "";
		}
		if (isFirst) {
			return date + " 00:00:00";
		}
		return date + " 23:59:59";
	}

	/**
	 * Date转换为Timestamp
	 */
	public static Timestamp convert(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 取当前日期(yyyy-mm-dd)
	 *
	 * @return 时间Timestamp
	 */
	public static String getTodayDate() {
		return formatDate(new Date());
	}

	/**
	 * 取当前日期(yyyy-mm-dd hh:mm:ss)
	 *
	 * @return 时间Timestamp
	 */
	public static String getTodayDateTime() {
		return format(new Date());
	}

	/**
	 * 取得n分钟后的时间
	 *
	 * @param date
	 *            日期
	 * @param afterMins
	 * @return 时间Timestamp
	 */
	public static Date getAfterMinute(Date date, long afterMins) {
		if (date == null)
			date = new Date();

		long longTime = date.getTime() + afterMins * 60 * 1000;

		return new Date(longTime);
	}

	// public static void main(String[] arg) {
	// System.err.println(format((new Date())));
	// System.err.println(format(getAfterMinute(new Date(), 3)));
	// }

	/**
	 * 取得指定日期几天后的日期
	 *
	 * @param date
	 *            日期
	 * @param afterDays
	 *            天数
	 * @return 日期
	 */
	public static Date getAfterDay(Date date, int afterDays) {
		if (date == null)
			date = new Date();

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, afterDays);
		return cal.getTime();
	}

	/**
	 * 取得指定日期几天后的日期
	 *
	 * @param sDate
	 *            日期 yyyy-MM-dd
	 * @param afterDays
	 *            天数
	 * @return 日期
	 */
	public static String getAfterDay(String sDate, int afterDays) {
		Date date = convertDate(sDate);

		date = getAfterDay(date, afterDays);
		return formatDate(date);
	}

	/**
	 * 获得几个月后的日期
	 *
	 * @param date
	 *            日期
	 * @param afterMonth
	 *            月数
	 * @return 日期Date
	 */
	public static Date getAfterMonth(Date date, int afterMonth) {
		if (date == null)
			date = new Date();

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);

		cal.add(Calendar.MONTH, afterMonth);
		return cal.getTime();
	}

	/**
	 * 获得几个月后的日期
	 *
	 * @param sDate
	 *            日期
	 * @param afterMonth
	 *            月数
	 * @return 日期"yyyy-MM-dd"
	 */
	public static String getAfterMonth(String sDate, int afterMonth) {
		Date date = convertDate(sDate);

		date = getAfterMonth(date, afterMonth);
		return formatDate(date);
	}

	/**
	 * 获得几年后的日期
	 *
	 * @param date
	 *            日期
	 * @param afterYear
	 *            年数
	 * @return 日期Date
	 */
	public static Date getAfterYear(Date date, int afterYear) {
		if (date == null)
			date = new Date();

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);

		cal.add(Calendar.YEAR, afterYear);
		return cal.getTime();
	}

	/**
	 * 获得几年后的日期
	 *
	 * @param sDate
	 *            日期
	 * @param afterYear
	 *            年数
	 * @return 日期Date
	 */
	public static String getAfterYear(String sDate, int afterYear) {
		Date date = convertDate(sDate);

		date = getAfterYear(date, afterYear);
		return formatDate(date);
	}

	/**
	 * 取得月份第一天日期
	 *
	 * @param sDate
	 *            (yyyy-mm-dd) : 如为Null，默认取当前系统时间
	 * @return yyyy-mm-dd
	 */
	public static String getMonthFirstDay(String sDate) {
		Date date = null;
		if (!StringUtil.isNull(sDate)) {
			date = convertDate(sDate);
		}
		Calendar gc = Calendar.getInstance();
		if (date != null) {
			gc.setTime(date);
		}
		gc.set(Calendar.DATE, 1);

		return formatDate(gc.getTime());
	}

	/**
	 * 取得月份最后一天日期
	 *
	 * @param sDate
	 *            (yyyy-mm-dd) : 如为Null，默认取当前系统时间
	 * @return yyyy-mm-dd
	 */
	public static String getMonthLastDay(String sDate) {
		Date date = null;

		if (!StringUtil.isNull(sDate)) {
			date = convertDate(sDate);
		}
		Calendar gc = Calendar.getInstance();
		if (date != null) {
			gc.setTime(date);
		}
		gc.add(Calendar.MONTH, 1);
		gc.add(Calendar.DATE, -gc.get(Calendar.DAY_OF_MONTH));

		return formatDate(gc.getTime());
	}

	/**
	 * 取得日期，星期几
	 *
	 * @param dateTime
	 *            : dateTime
	 * @return "星期日","星期一","星期二","星期三","星期四","星期五","星期六"
	 */
	public static String getWeekDayName(String dateTime) throws ParseException {
		Date d = convertDate(dateTime);
		return getWeekDayName(d);
	}

	/**
	 * 取得日期，星期几
	 *
	 * @param dateTime
	 *            : dateTime
	 * @return "星期日","星期一","星期二","星期三","星期四","星期五","星期六"
	 */
	public static String getWeekDayName(Date dateTime) throws ParseException {

		String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

		int day = getWeekDay(dateTime);
		String week_day_name = dayNames[day - 1];
		return week_day_name;
	}

	/**
	 * 取得日期是星期的第几天
	 *
	 * @param dateTime
	 *            : dateTime
	 * @return :1： "星期日"，2："星期一"， 3："星期二"，,4："星期三", 5："星期四", 6："星期五", 7："星期六"
	 */
	public static int getWeekDay(Date dateTime) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 传入的日期格式是否是yyyy-MM-dd
	 *
	 * @param in
	 *            被检查的日期
	 * @return 是指定的日期格式:true,否则:false
	 */
	public static boolean isFormatDate(String in) {
		return isFormatDate(in, "yyyy-MM-dd");
	}

	/**
	 * 传入的日期格式是否是指定的日期格式
	 *
	 * @param in
	 *            被检查的日期
	 * @param format
	 *            指定的日期格式
	 * @return 是指定的日期格式:true,否则:false
	 */
	private static boolean isFormatDate(String in, String format) {
		if (StringUtil.isNull(format)) {
			return false;
		}

		final SimpleDateFormat sdFormat = new SimpleDateFormat(format);
		Date dateCompare = null;
		String sDate = "";

		try {
			dateCompare = sdFormat.parse(in, new ParsePosition(0));
			sDate = sdFormat.format(dateCompare);
		} catch (final Exception e) {
			return false;
		}

		if (dateCompare == null) {
			return false;
		} else {
			return sDate.equals(in);
		}
	}

	/**
	 * 取得下一个整点时长 Administrator 2014-7-4
	 *
	 * @return long
	 */
	public static long getNextHourTime() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		// 毫秒
		long ss = c.getTime().getTime() - System.currentTimeMillis();
		return ss;

	}

	/**
	 * 取得下一天时长 Administrator 2014-7-4
	 *
	 * @return long
	 */
	public static long getNextDayTime() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		c.add(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		// 毫秒
		long ss = c.getTime().getTime() - System.currentTimeMillis();
		return ss;

	}

	/**
	 * 比较两个日期先后
	 *
	 * @param date1
	 *            : yyyy-MM-dd
	 * @param date1
	 *            : yyyy-MM-dd
	 * @return date1 > date2 : false， else : true
	 */
	public static boolean compareDate(String date1, String date2) {
		if (isFormatDate(date1, "yyyy-MM-dd") && isFormatDate(date2, "yyyy-MM-dd")) {
			if (date1.compareTo(date2) > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 比较两个日期先后
	 *
	 * @param date1
	 *            : yyyy-MM-dd HH:mm:ss
	 * @param date1
	 *            : yyyy-MM-dd HH:mm:ss
	 * @return date1 > date2 : true， else : false
	 */
	public static boolean compareDateDetail(String date1, String date2) {
		if (isFormatDate(date1, "yyyy-MM-dd HH:mm:ss") && isFormatDate(date2, "yyyy-MM-dd HH:mm:ss")) {
			if (date1.compareTo(date2) > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 得到当前时间 return java.sql.Timestamp
	 *
	 * @return Timestamp
	 */
	public static Timestamp getNowTimestamp() {
		long curTime = System.currentTimeMillis();
		return new Timestamp(curTime);
	}

	/**
	 * 日期类型转化
	 *
	 * @param sTsp
	 *            日期串
	 * @param iType
	 *            0:yyyy年M月d日； <br>
	 *            1:yyyy-M-d; <br>
	 *            2:yyyy/M/d <br>
	 *            3:yyyy年MM月dd日; <br>
	 *            4:yyyy-MM-dd; <br>
	 *            5:yyyy/MM/dd; <br>
	 *            6:yyyy-MM-dd HH:mm; <br>
	 *            7:yyyy/MM/dd HH:mm; <br>
	 *            8:HH:mm <br>
	 *            9:h:mm a <br>
	 *            10:yyyyMMdd <br>
	 *            11:yyyyMMddHHmmSS<br>
	 *            12:yyyy-MM-dd HH:mm:ss <br>
	 *            13:HH:mm:ss <br>
	 *            14:ddMMMyy <br>
	 *            15:yyMM <br>
	 *            17:MM/dd/yyyy <br>
	 *            18:dd/MM/yyyy <br>
	 *            19:yy-MM-dd
	 * @return 日期串
	 */
	public static String formatDate(String sTsp, int iType) {

		if (sTsp == null || "".equals(sTsp)) {
			return "";
		}

		if (sTsp.length() == 10) {
			return formatDate(convertTimestamp(sTsp), iType);
		} else if (sTsp.length() > 10) {
			String[] sDatas = sTsp.split("\\.");
			if (sDatas.length > 2) {
				if (sDatas.length > 1) {
					String[] sDates = sDatas[0].split("-");
					if (sDates[1].length() == 1)
						sDates[1] = "0" + sDates[1];
					if (sDates[2].length() == 1)
						sDates[2] = "0" + sDates[2];

					for (int i = 1; i < 4; i++) {
						sDatas[i] = sDatas[i].trim();
						if (sDatas[i].length() == 1)
							sDatas[i] = "0" + sDatas[i];
					}

					sTsp = sDates[0] + "-" + sDates[1] + "-" + sDates[2] + " " + sDatas[1] + ":" + sDatas[2] + ":"
							+ sDatas[3] + ".000000000";
				}
			}

			return formatDate(Timestamp.valueOf(sTsp), iType);
		} else {
			return "";
		}
	}

	/**
	 * 日期类型转化
	 *
	 * @param tsp
	 *            日期
	 * @param iType
	 *            0:yyyy年M月d日； <br>
	 *            1:yyyy-M-d; <br>
	 *            2:yyyy/M/d <br>
	 *            3:yyyy年MM月dd日; <br>
	 *            4:yyyy-MM-dd; <br>
	 *            5:yyyy/MM/dd; <br>
	 *            6:yyyy-MM-dd HH:mm; <br>
	 *            7:yyyy/MM/dd HH:mm; <br>
	 *            8:HH:mm <br>
	 *            9:h:mm a <br>
	 *            10:yyyyMMdd <br>
	 *            11:yyyyMMddHHmmSS<br>
	 *            12:yyyy-MM-dd HH:mm:ss <br>
	 *            13:HH:mm:ss <br>
	 *            14:ddMMMyy <br>
	 *            15:yyMM <br>
	 *            16:上一个月日期yyyyMMdd <br>
	 *            17:MM/dd/yyyy <br>
	 *            18:dd/MM/yyyy <br>
	 *            19:yy-MM-dd <br>
	 *            20:yyyyMM 21:HHmmSS 22:yyyy-MM
	 * @return 日期串
	 */
	public static String formatDate(Timestamp tsp, int iType) {

		SimpleDateFormat sFormat1 = new SimpleDateFormat(strFormat1);
		SimpleDateFormat sFormat2 = new SimpleDateFormat(strFormat2);
		SimpleDateFormat sFormat3 = new SimpleDateFormat(strFormat3);
		SimpleDateFormat sFormat4 = new SimpleDateFormat(strFormat4);
		SimpleDateFormat sFormat5 = new SimpleDateFormat(strFormat5);
		SimpleDateFormat sFormat6 = new SimpleDateFormat(strFormat6);
		SimpleDateFormat sFormat7 = new SimpleDateFormat(strFormat7);
		SimpleDateFormat sFormat8 = new SimpleDateFormat(strFormat8);
		SimpleDateFormat sFormat9 = new SimpleDateFormat(strFormat9);
		SimpleDateFormat sFormat10 = new SimpleDateFormat(strFormat10);
		SimpleDateFormat sFormat15 = new SimpleDateFormat(strFormat15);
		SimpleDateFormat sFormat17 = new SimpleDateFormat(strFormat17);
		SimpleDateFormat sFormat18 = new SimpleDateFormat(strFormat18);
		SimpleDateFormat sFormat19 = new SimpleDateFormat(strFormat19);
		SimpleDateFormat sFormat20 = new SimpleDateFormat(strFormat20);
		SimpleDateFormat sFormat21 = new SimpleDateFormat(strFormat21);
		SimpleDateFormat sFormat22 = new SimpleDateFormat(strFormat22);
		SimpleDateFormat sFormat23 = new SimpleDateFormat(strFormat23);
		SimpleDateFormat sFormat24 = new SimpleDateFormat(strFormat24);
		SimpleDateFormat sFormat25 = new SimpleDateFormat(strFormat25);
		SimpleDateFormat sFormat26 = new SimpleDateFormat(strFormat26);
		SimpleDateFormat sFormat27 = new SimpleDateFormat(strFormat27);

		GregorianCalendar cal = new GregorianCalendar();
		// java.util.Date dDate = null;

		if (tsp == null) {
			cal.setTime(new Date());
		} else {
			cal.setTime(tsp);
		}

		String sDate = "";
		// 0:yyyy年M月d日

		if (iType == 0) {
			int iYear = cal.get(Calendar.YEAR);
			int iMonth = cal.get(Calendar.MONTH) + 1;
			int iDay = cal.get(Calendar.DAY_OF_MONTH);

			sDate = "" + iYear + "年" + iMonth + "月" + iDay + "日";
		}
		// 1:yyyy-M-d
		if (iType == 1) {
			int iYear = cal.get(Calendar.YEAR);
			int iMonth = cal.get(Calendar.MONTH) + 1;
			int iDay = cal.get(Calendar.DAY_OF_MONTH);

			sDate = "" + iYear + "-" + iMonth + "-" + iDay;
		}
		// 2:yyyy/M/d
		if (iType == 2) {
			int iYear = cal.get(Calendar.YEAR);
			int iMonth = cal.get(Calendar.MONTH) + 1;
			int iDay = cal.get(Calendar.DAY_OF_MONTH);

			sDate = "" + iYear + "/" + iMonth + "/" + iDay;
		}
		// 3:yyyy年MM月dd日;
		if (iType == 3) {
			String strYear = String.valueOf(cal.get(Calendar.YEAR));
			String strMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
			strMonth = "00" + strMonth;
			strMonth = strMonth.substring(strMonth.length() - 2, strMonth.length());
			String strDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
			strDay = "00" + strDay;
			strDay = strDay.substring(strDay.length() - 2, strDay.length());

			sDate = "" + strYear + "年" + strMonth + "月" + strDay + "日";
		}
		// 4:yyyy-MM-dd;
		if (iType == 4) {
			sDate = sFormat2.format(cal.getTime());
		}
		// 5:yyyy/MM/dd;
		if (iType == 5) {
			sDate = sFormat3.format(cal.getTime());
		}
		// 6:yyyy-MM-dd HH:mm;
		if (iType == 6) {
			sDate = sFormat1.format(cal.getTime());
		}
		// 7:yyyy/MM/dd HH:mm;
		if (iType == 7) {
			sDate = sFormat4.format(cal.getTime());
		}
		// 8:HH:mm
		if (iType == 8) {
			sDate = sFormat5.format(cal.getTime());
		}
		// 9:h:mm a
		if (iType == 9) {
			sDate = sFormat6.format(cal.getTime());
		}
		// 10:h:mm a
		if (iType == 10) {
			sDate = sFormat10.format(cal.getTime());
		}
		if (iType == 11) {
			sDate = sFormat7.format(cal.getTime());
		}
		// 12:yyyy-MM-dd HH:mm:ss;
		if (iType == 12) {
			sDate = sFormat8.format(cal.getTime());
		}
		// 13:HH:mm:ss;
		if (iType == 13) {
			sDate = sFormat8.format(cal.getTime());
			sDate = sDate.substring(10);
		}
		// 14:ddMMMyy
		if (iType == 14) {
			sDate = sFormat9.format(cal.getTime());
		}
		// 15:yyMM
		if (iType == 15) {
			sDate = sFormat15.format(cal.getTime());
		}
		if (iType == 16) {
			int iYear = cal.get(Calendar.YEAR);
			int iMonth = cal.get(Calendar.MONTH) + 1;
			int iDay = cal.get(Calendar.DAY_OF_MONTH);
			switch (iMonth) {
			case 1: {
				cal.set(iYear, Calendar.JANUARY, iDay);
				break;
			}
			case 2: {
				cal.set(iYear, Calendar.FEBRUARY, iDay);
				break;
			}
			case 3: {
				cal.set(iYear, Calendar.MARCH, iDay);
				break;
			}
			case 4: {
				cal.set(iYear, Calendar.APRIL, iDay);
				break;
			}
			case 5: {
				cal.set(iYear, Calendar.MAY, iDay);
				break;
			}
			case 6: {
				cal.set(iYear, Calendar.JUNE, iDay);
				break;
			}
			case 7: {
				cal.set(iYear, Calendar.JULY, iDay);
				break;
			}
			case 8: {
				cal.set(iYear, Calendar.AUGUST, iDay);
				break;
			}
			case 9: {
				cal.set(iYear, Calendar.SEPTEMBER, iDay);
				break;
			}
			case 10: {
				cal.set(iYear, Calendar.OCTOBER, iDay);
				break;
			}
			case 11: {
				cal.set(iYear, Calendar.NOVEMBER, iDay);
				break;
			}
			case 12: {
				cal.set(iYear, Calendar.DECEMBER, iDay);
				break;
			}
			}
			cal.add(Calendar.MONTH, -1);
			sDate = sFormat2.format(cal.getTime());

		}
		// 17:MM/dd/yyyy
		if (iType == 17) {
			sDate = sFormat17.format(cal.getTime());
		}

		// 18:dd/MM/yyyy
		if (iType == 18) {
			sDate = sFormat18.format(cal.getTime());
		}
		// 19:yy-MM-dd;
		if (iType == 19) {
			sDate = sFormat19.format(cal.getTime());
		}
		// 20:yyyyMM
		if (iType == 20) {
			sDate = sFormat20.format(cal.getTime());
		}
		// 20:yyyyMM
		if (iType == 21) {
			sDate = sFormat21.format(cal.getTime());
		}
		// 22:yyyy-MM
		if (iType == 22) {
			sDate = sFormat22.format(cal.getTime());
		}

		// 23:yyyy年MM月dd日
		if (iType == 23) {
			sDate = sFormat23.format(cal.getTime());
		}
		// 24:yyyyMMdd HH:mm:ss
		if (iType == 24) {
			sDate = sFormat24.format(cal.getTime());
		}
		if (iType == 25) {
			sDate = sFormat25.format(cal.getTime());
		}
		// 26 yyyy-MM
		if (iType == 26) {
			sDate = sFormat26.format(cal.getTime());
		}

		// 26 yyyy-MM-dd HH:mm:ss:SS
		if (iType == 27) {
			sDate = sFormat27.format(cal.getTime());
		}
		return sDate;
	}

	/**
	 * 取得n分钟前的时间
	 *
	 * @param lminute
	 * @return 时间Timestamp
	 */
	public static Timestamp gettimebefore(long lminute) {

		Timestamp tsp = parseTime();
		long lngTime = tsp.getTime() - lminute * 60 * 1000;

		return new Timestamp(lngTime);

	}

	/**
	 * 取得n分钟前的时间
	 *
	 * @param date
	 *            日期
	 * @param lminute
	 * @return 时间Timestamp
	 */
	public static Timestamp gettimebefore(Date date, long lminute) {

		long lngTime = date.getTime() - lminute * 60 * 1000;
		return new Timestamp(lngTime);

	}

	/**
	 * 取得时间差
	 *
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return 日期2-日期1的毫秒时间差
	 */
	public static long getDateDifference(Date date1, Date date2) {
		Calendar cld1Work = Calendar.getInstance();
		Calendar cld2Work = Calendar.getInstance();
		Calendar cld1 = Calendar.getInstance();
		Calendar cld2 = Calendar.getInstance();
		long lTime1;
		long lTime2;

		cld1Work.setTime(date1);
		cld2Work.setTime(date2);
		cld1.clear();
		cld2.clear();
		cld1.set(cld1Work.get(Calendar.YEAR), cld1Work.get(Calendar.MONTH), cld1Work.get(Calendar.DATE));
		cld2.set(cld2Work.get(Calendar.YEAR), cld2Work.get(Calendar.MONTH), cld2Work.get(Calendar.DATE));
		lTime1 = (cld1.getTime()).getTime();
		lTime2 = (cld2.getTime()).getTime();

		return (lTime2 - lTime1) / (1000 * 60 * 60 * 24);
	}

	// zhangdan add 20140605 start
	public static int getDays(String fromDate, String endDate) {

		long from = stringToDate(fromDate).getTime();
		long end = stringToDate(endDate).getTime();

		return (int) ((end - from) / (24 * 60 * 60 * 1000)) + 1;
	}

	public static int getDays(Date fromDate, Date endDate) {

		long from = fromDate.getTime();
		long end = endDate.getTime();

		return (int) ((end - from) / (24 * 60 * 60 * 1000)) + 1;
	}

	// zhangdan add 20140605 end
	public static Date stringToDate(String date) {
		if (date == null)
			return null;

		Calendar cd = Calendar.getInstance();
		StringTokenizer token = new StringTokenizer(date, "-/ :");
		if (token.hasMoreTokens()) {
			cd.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.YEAR, 1970);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MONTH, Integer.parseInt(token.nextToken()) - 1);
		} else {
			cd.set(Calendar.MONTH, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.HOUR_OF_DAY, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MINUTE, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MINUTE, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.SECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.SECOND, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MILLISECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MILLISECOND, 0);
		}

		return cd.getTime();
	}

	/**
	 * get day index of a week for the specific date
	 *
	 * @param date
	 *            the specific date
	 * @return day index of a week,Sun. is 1,Mon. is 2,Tues. is 3,Wed. is 4,Thurs. is 5,Fri. is 6,Sat. is 7
	 * @throws ParseException
	 */
	public static int getDayOfWeek2(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/*
	 * Get the Next Date Write by Jeffy pan 2004-10-21 Date Format:(YYYY-MM-DD) (YYYY:M:D HH:MM:SS) (YYYY/M/DD hh:MM)
	 */
	public static String dateToString(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date).trim();
	}

	/**
	 * 将格式如2007-04-18 00:00:00.0的字符串，<br>
	 * 转换为用于sql的字符串(to_timestamp('2007-04-18 00:00:00.0' , 'yyyy-mm-dd hh24:mi:ssxff'))
	 *
	 * @param s
	 *            待转换的字符串
	 * @return 例如to_timestamp('2007-04-18 00:00:00.0' , 'yyyy-mm-dd hh24:mi:ssxff')
	 */
	public static String toTimeStamp(String s) {
		s = StringUtil.nvl(s);
		if ("".equals(s))
			return "";
		String sql = "to_timestamp('" + s + "' , 'yyyy-mm-dd hh24:mi:ssxff')";
		return sql;
	}

	/**
	 * 将格式如2007-04-18 的字符串，<br>
	 * 转换为用于sql的字符串(to_timestamp('2007-04-18' , 'yyyy-mm-dd'))
	 *
	 * @param s
	 *            待转换的字符串
	 * @return 例如to_timestamp('2007-04-18' , 'yyyy-mm-dd')
	 */
	public static String toDate(String s) {
		s = StringUtil.nvl(s);
		if ("".equals(s))
			return "";
		String sql = "to_date('" + s + "' , 'yyyy-mm-dd')";
		return sql;
	}

	/**
	 * 判断日期段是不是在从今天起的两个月范围内
	 *
	 * @param startDate
	 * @param endDate
	 * @return ture:今天以后的两个月内 false:今天以前的日期或两个月后的日期
	 */
	public static boolean isInTwoMonths(String startDate, String endDate) {
		Date currentDate = parseDate();
		Date lastDate = getAfterMonth(currentDate, 2);
		String sd = formatDate(currentDate);
		String ed = formatDate(lastDate);

		return !convertDate(startDate).before(convertDate(sd)) && !convertDate(endDate).after(convertDate(ed));
	}

	/**
	 * get day index of a week for the specific date
	 *
	 * @param date
	 *            the specific date
	 * @return day index of a week,Mon. is 1,Tues. is 2,Wed. is 3,Thurs. is 4,Fri. is 5,Sat. is 6,Sun. is 7
	 * @throws ParseException
	 */
	public static int getDayOfWeek(Date date) {
		if (date == null)
			return 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int result = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (result == 0)
			result = 7;
		return result;
	}
	public  static void main(String[] aa){
	System.out.print(getYestodayDate());
	}
	/*
	 * @param months months月前 return BeforeToday YYYY-MM-DD
	 */
	public static String getBeforeToday(int months) {
		Calendar calendar = new GregorianCalendar();
		String month = "";
		String date = "";
		calendar.add(Calendar.MONTH, -months);
		if ((calendar.get(Calendar.MONTH) + 1) < 10) {
			month = "0" + (calendar.get(Calendar.MONTH) + 1);
		} else {
			month = "" + (calendar.get(Calendar.MONTH) + 1);
		}
		if (calendar.get(Calendar.DATE) < 10) {
			date = "0" + calendar.get(Calendar.DATE);
		} else {
			date = "" + calendar.get(Calendar.DATE);
		}
		String past = "" + calendar.get(Calendar.YEAR) + "-" + month + "-" + date;
		return past;
	}

	/**
	 * 邮箱验证是否超时（24hour）
	 *
	 * @param sendTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean sendEmailTime(String sendTime) throws ParseException {
		SimpleDateFormat sFormat = null;
		sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		boolean timechek = false;
		Date date1 = sFormat.parse(sendTime);
		Date date2 = sFormat.parse(String.valueOf(new Timestamp(System.currentTimeMillis())));
		long time = date2.getTime() - date1.getTime();

		long day = time / (24 * 60 * 60 * 1000);
		long hour = (time / (60 * 60 * 1000) - day * 24);
		if (day > 0) {
			timechek = false;
		} else {
			if (hour > 24) {
				timechek = false;
			} else {
				timechek = true;
			}
		}
		return timechek;
	}

	// /**
	// * @description 把日期从年月日分割转成-分割形式
	// * @param date
	// * @return
	// * @author zhangDan
	// * @date 2014-6-24
	// */
	// public static String formateDate(String date) {
	// if(date == null || "".equals(date)){
	// return "";
	// }
	// date = date.replace("年", "-");
	// date = date.replace("月", "-");
	// date = date.replace("日", "");
	// String[] dateArr = date.split("-");
	// // 年
	// String fdate = dateArr[0];
	// // 月
	// if(dateArr[1].length() == 1){
	// fdate = fdate + "-0" + dateArr[1];
	// } else {
	// fdate = fdate + "-" + dateArr[1];
	// }
	// // 日
	// if(dateArr[2].length() == 1){
	// fdate = fdate + "-0" + dateArr[2];
	// } else {
	// fdate = fdate + "-" + dateArr[2];
	// }
	// return fdate;
	// }

	/**
	 * @description 把String日期yyyyMMDD转成yyyy-MM-DD形式
	 * @param date
	 * @return
	 * @authorlijl
	 * @date 2014-6-30
	 */
	public static String formateStringDate(String date) {
		if (date == null || "".equals(date)) {
			return "";
		}
		String fdate = "";
		if (date.length() == 8) {
			fdate = date.substring(0, 4);
			fdate = fdate + "-" + date.substring(4, 6);
			fdate = fdate + "-" + date.substring(6, 8);
		} else {
			return "";
		}
		return fdate;
	}

	/**
	 * @description 计算两个日期之间的天数
	 * @param sDate
	 *            , eDate
	 * @return
	 * @author lull
	 * @date 2014-12-15
	 */
	public static String daysBetween(String sDate, String eDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String days = "";
		try {
			long to = df.parse(sDate).getTime();
			long from = df.parse(eDate).getTime();

			days = String.valueOf((from - to) / (1000 * 60 * 60 * 24));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return days;
	}

	/**
	 * @description 计算两个日期之间的天数（包含开始日期）
	 * @param sDate
	 *            , eDate
	 * @return
	 * @author lull
	 * @date 2014-12-15
	 */
	public static String gjDaysBetween(String sDate, String eDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String days = "";
		try {
			long to = df.parse(sDate).getTime();
			long from = df.parse(eDate).getTime();

			days = String.valueOf((from - to) / (1000 * 60 * 60 * 24) + 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return days;
	}

	/**
	 * 将Timestamp类型转换为字符串 MM-dd
	 *
	 * @param date
	 * @return String
	 */
	public static String formatDateType(Date date) {
		if (date == null) {
			return "";
		}

		String sDate = "";
		SimpleDateFormat sFormat = new SimpleDateFormat("MM-dd");

		try {
			String dateFmt = dateFormat.format(date);
			sDate = sFormat.format(dateFormat.parse(dateFmt));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sDate;
	}

	/**
	 * 将Date类型转换为字符串 MM年dd月
	 *
	 * @param date
	 * @return String
	 */
	public static String formatDateType(Date date, String formatType) {
		if (date == null || formatType == null || "".equals(formatType)) {
			return "";
		}

		String sDate = "";
		SimpleDateFormat sFormat = new SimpleDateFormat(formatType);

		try {
			String dateFmt = dateFormat.format(date);
			sDate = sFormat.format(dateFormat.parse(dateFmt));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sDate;
	}

	/**
	 * 取得日期，星期几
	 *
	 * @param dateTime
	 *            : dateTime
	 * @return "周日","周一","周二","周三","周四","周五","周六"
	 */
	public static String getWeekDays(Date dateTime) {
		String dayNames[] = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

		int day = getWeekDay(dateTime);
		String week_day_name = dayNames[day - 1];
		return week_day_name;
	}

	/**
	 * 获取到达下一个 周i的j时k分的剩余时间。
	 *
	 * @param i
	 *            星期(0：周日，1：周一，2：周二...)
	 * @param j
	 *            小时
	 * @param k
	 *            分钟
	 */
	public static long getleftTime(int i, int j, int k) {
		long times = 0;
		// 国际酒店城市信息每周周四3:20执行 计算当前时间此时间的时间剩余量
		long nowT = System.currentTimeMillis();
		long nowD = nowT - nowT % 86400000l + j * 3600000 + k * 60000 - 28800000;// 今日的时刻(使用为东8时区)
		long sevenDay = 604800000l;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(nowT));
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		nowD = nowD - (week - i) * 86400000l;// 当前周的周日的时刻
		if (nowT < nowD) {
			times = nowD - nowT;
		} else {
			times = nowD - nowT + sevenDay;
		}
		return times;
	}

	/**
	 * 取得星期数
	 *
	 * @param dateTime
	 *            : dateTime
	 * @return 0:星期天 1：星期一 2：星期二 3：星期三 4：星期四 5：星期五 6：星期六
	 */
	public static int getWeekDaysCount(Date dateTime) {
		String dayNames[] = { "0", "1", "2", "3", "4", "5", "6" };

		int day = getWeekDay(dateTime);
		int week_days = Integer.valueOf(dayNames[day - 1]);
		return week_days;
	}

	/**
	 * 获取某年第一天日期
	 *
	 * @param year
	 *            年份
	 *
	 * @return Date
	 */

	public static Date getYearFirst(int year) {

		Calendar calendar = Calendar.getInstance();

		calendar.clear();

		calendar.set(Calendar.YEAR, year);

		Date currYearFirst = calendar.getTime();

		return currYearFirst;

	}

	/**
	 *
	 * 获取某年最后一天日期
	 *
	 * @param year
	 *            年份
	 *
	 * @return Date
	 */

	public static Date getYearLast(int year) {

		Calendar calendar = Calendar.getInstance();

		calendar.clear();

		calendar.set(Calendar.YEAR, year);

		calendar.roll(Calendar.DAY_OF_YEAR, -1);

		Date currYearLast = calendar.getTime();

		return currYearLast;

	}

	/**
	 * SQL Timestamp
	 *
	 * @return 时间Timestamp
	 * @throws ParseException
	 */
	public static Timestamp parseSqlTime(String time) {
		try {
			return new Timestamp(datetimeFormat.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SQL Date
	 *
	 * @return 时间Date
	 * @throws ParseException
	 */
	public static java.sql.Date parseSqlDate(String date) {
		try {
			return new java.sql.Date(dateFormat.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据时间差,检查分钟数是不是超过30分钟
	 *
	 * @return Boolean
	 * @throws ParseException
	 */
	public static Boolean checkMinute(String time) {
		boolean timeFlag = false;
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begin = dfs.parse(time);
			Date end = dfs.parse(DateUtil.getTodayDateTime());
			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			long day = between / (24 * 3600);
			long hour = between % (24 * 3600) / 3600;
			long minute = between % 3600 / 60;
			long second = between % 60 / 60;

			if (day > 0 || hour > 0 || minute > 30 || (minute == 30 && second > 0)) {
				timeFlag = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return timeFlag;
	}

	/**
	 * 获取天数差
	 *
	 * @param date
	 * @return
	 */
	public static double getCompareDays(String date) {
		// 获取当前年份 yy-01-01 天数差
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sf2.format(sf1.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String nowYear = new SimpleDateFormat("yyyy-01-01").format(Calendar.getInstance().getTime());
		Integer compDate = getDays(nowYear, date);
		double timeStamp = compDate.doubleValue();
		return timeStamp;
	}

	/**
	 * 获取系统时间到毫秒
	 *
	 * @param
	 * @return
	 */
	public static String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		String res = sdf.format(new Date());
		return res;
	}



	/**
	 * 获取距凌晨时间
	 *
	 * @param
	 * @return
	 */
	public static int getBeforeYawn(){
		Calendar c = Calendar.getInstance();
		long now = c.getTimeInMillis();

		c.add(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		long millis = c.getTimeInMillis() - now;
		int time=(int)(millis/1000);
		return time;
	}

	/**
	 * 获取据凌晨时间
	 *
	 * @param
	 * @return
	 */
	public static String getTodatDate(){

		return format(new Date(),"yyyyMMdd");
	}

	public static String getYestodayDate(){
		Date date=new Date();
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);
		date=calendar.getTime();
		return format(date,"yyyyMMdd");
	}

	/**
	 * solr时间的转换
	 *
	 * @param strDate  原字符串日期
	 */
	public static String formatDateView(Object strDate){

		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		String stringDate = "";
		try {
			Date lDate = format.parse(strDate.toString());
			SimpleDateFormat lFormat = new SimpleDateFormat("yyyy年MM月");
			stringDate = lFormat.format(lDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return stringDate;
	}
}

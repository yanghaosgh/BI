package com.mxyc.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 提供公用方法
 * 
 * @author Administrator
 *
 */
public class CommonUtils {
	private static final Logger log = Logger.getLogger(CommonUtils.class);

	/** 常用日期格式化类型 */
	public static final String[] date_format = { "yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd-HH", "yyyy_MM_dd" };

	/**
	 * 判断集合是否有效
	 * 
	 * @param list
	 * @return
	 */
	public static boolean listIsValid(Collection<?> list) {
		if (list == null || list.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * list to set
	 * 
	 * @param list
	 * @return
	 */
	public static <T> Set<T> list2Set(List<T> list) {
		Set<T> set = new HashSet<T>();
		if (list != null) {
			for (T p : list) {
				set.add(p);
			}
		}
		return set;
	}

	/**
	 * 根据需要获日期字符串
	 * 
	 * @param model
	 * @param date
	 * @param year
	 *            年+
	 * @param month
	 *            月+
	 * @param day
	 *            日+
	 * @return
	 */
	public static String createDate(String model, Date date, int year, int month, int day) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(model);
		if (year == 0 && month == 0 && day == 0) {
			return f.format(date);
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);
			gc.add(Calendar.YEAR, year);
			gc.add(Calendar.MONTH, month);
			gc.add(Calendar.DATE, day);
			return f.format(gc.getTime());
		}
	}

	/**
	 * 根据需要获日期
	 * 
	 * @param model
	 * @param date
	 * @param year
	 *            年+
	 * @param month
	 *            月+
	 * @param day
	 *            日+
	 * @return
	 */
	public static Date createDate(Date date, int year, int month, int day, int hour) {
		if (date == null) {
			return null;
		}

		if (year == 0 && month == 0 && day == 0 && hour == 0) {
			return date;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);
			gc.add(Calendar.YEAR, year);
			gc.add(Calendar.MONTH, month);
			gc.add(Calendar.DATE, day);
			gc.add(Calendar.HOUR, hour);

			return gc.getTime();
		}
	}

	/**
	 * 解析日期时间字符串 为 Date型
	 * 
	 * @param model
	 * @param timeStr
	 * @return
	 */
	public static Date createDate(String model, String timeStr) {
		Date date = null;
		if (StringUtils.isEmpty(timeStr)) {
			return date;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(model);
		try {
			date = sdf.parse(timeStr);
		} catch (ParseException e) {
			log.error(e, e);
		}

		return date;
	}

	/**
	 * string array to int list
	 * 
	 * @param strArray
	 * @return
	 */
	public static Integer[] convertStrArrayToInt(String[] strArray) {
		if (strArray != null && strArray.length > 0) {
			Integer array[] = new Integer[strArray.length];
			for (int i = 0; i < strArray.length; i++) {
				array[i] = Integer.parseInt(strArray[i]);
			}

			return array;
		} else {
			return null;
		}
	}

	/**
	 * string to Integer list
	 * 
	 * @param strArray
	 * @return
	 */
	public static List<Integer> convertStrToList(String str, String sepator) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}

		if (StringUtils.isEmpty(sepator)) {
			sepator = ",";
		}

		String[] split = str.split(sepator);
		List<Integer> list = null;
		if (split != null && split.length > 0) {
			list = new ArrayList<Integer>();

			for (String s : split) {
				list.add(Integer.parseInt(s));
			}
		}

		return list;
	}

	/**
	 * string to Integer list
	 * 
	 * @param strArray
	 * @return
	 */
	public static List<Long> convertStrToList4Long(String str, String sepator) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}

		if (StringUtils.isEmpty(sepator)) {
			sepator = ",";
		}

		String[] split = str.split(sepator);
		List<Long> list = null;
		if (split != null && split.length > 0) {
			list = new ArrayList<Long>();

			for (String s : split) {
				list.add(Long.parseLong(s));
			}
		}

		return list;
	}

	/**
	 * 获取 int 值
	 * 
	 * @param obj
	 * @return
	 */
	public static int objectToInt(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof BigInteger) {
			return ((BigInteger) obj).intValue();
		} else if (obj instanceof Integer) {
			return ((Integer) obj).intValue();
		} else if (obj instanceof Long) {
			return ((Long) obj).intValue();
		} else {
			log.error("[FATAL]" + obj + " is not BigInteger/Integer/Long");
			return 0;
		}
	}

	/**
	 * 判断是否是有效int<br>
	 * x!=null && x>=min
	 * 
	 * @param c
	 * @param min
	 * @return
	 */
	public static boolean isInteger(Integer x, int min) {
		if (x != null && x >= min) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是有效long<br>
	 * x!=null && x>=min
	 * 
	 * @param c
	 * @param min
	 * @return
	 */
	public static boolean isLong(Long x, long min) {
		if (x != null && x >= min) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeral(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}

		Matcher matcher = Pattern.compile("[0-9]+").matcher(str);
		return matcher.find();
	}

	/**
	 * 获取 随机数
	 * 
	 * @param max
	 *            最大数
	 * @param count
	 *            位数，当随机数不够时默认在前面加0
	 * @return
	 */
	public static String getRandom(int max, int count) {
		Random r = new Random();
		int i = r.nextInt(max);
		String s = i + "";
		int len = s.length();
		if (len < count) {
			for (int j = 0; j < count - len; j++) {
				s = "0" + s;
			}
		}
		return s;
	}

	/**
	 * 数字型字符串转字节数组
	 * 
	 * @param str
	 *            待处理字符串
	 * @param separator
	 *            分隔符
	 * @param continuous
	 *            连接符
	 * @return
	 */
	@Deprecated
	public static byte[] getBinaryStream(String str, String separator, String continuous) {
		List<Integer> list = extractNumber(str, separator, continuous);

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		if (list.size() == 0) {
			try {
				dout.writeInt(0);
			} catch (IOException e) {
				log.error(e, e);
			}
		}

		try {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				dout.writeInt(list.get(i));

				if (i == size - 1) {
					dout.flush();
					dout.close();
				}
			}
		} catch (IOException e) {
			log.error(e, e);
		}

		return bout.toByteArray();
	}

	/**
	 * 从字符串表达式中抽取整数集合
	 * 
	 * @param str
	 * @param separator
	 *            分割符
	 * @param continuous
	 *            连接符
	 * @return
	 */
	public static List<Integer> extractNumber(String str, String separator, String continuous) {
		List<Integer> list = new ArrayList<Integer>();

		if (StringUtils.isEmpty(str)) {
			return list;
		}

		if (StringUtils.isEmpty(separator)) {
			separator = ",";
		}

		if (StringUtils.isEmpty(continuous)) {
			continuous = "-";
		}

		if (str.contains(separator)) {
			String spl[] = str.split(separator);
			for (int i = 0; i < spl.length; i++) {
				if (spl[i].contains(continuous)) {
					String[] spl2 = spl[i].split(continuous);
					for (int j = Integer.parseInt(spl2[0]); j <= Integer.parseInt(spl2[1]); j++) {
						list.add(j);
					}
				} else {
					list.add(Integer.parseInt(spl[i]));
				}
			}
		} else if (str.contains(continuous)) {
			String[] spl = str.split(continuous);
			for (int i = Integer.parseInt(spl[0]); i <= Integer.parseInt(spl[1]); i++) {
				list.add(i);
			}
		} else {
			list.add(Integer.parseInt(str));
		}

		return list;
	}

	/**
	 * 根据属性字段获取get方法名称
	 * 
	 * @param attr
	 * @return
	 */
	public static String getGet(String attr) {
		return "get" + attr.substring(0, 1).toUpperCase() + attr.substring(1);
	}

	/**
	 * 根据属性字段获取set方法名称
	 * 
	 * @param attr
	 * @return
	 */
	public static String getSet(String attr) {
		return "set" + attr.substring(0, 1).toUpperCase() + attr.substring(1);
	}

	/**
	 * 合并两个int为一个long
	 * 
	 * @param high
	 * @param low
	 * @return
	 */
	public static long combineInt2Long(int high, int low) {
		return ((long) low & 0xFFFFFFFFl) | (((long) high << 32) & 0xFFFFFFFF00000000l);
	}

	/**
	 * 解析字符串为Integer
	 * 
	 * @param str
	 * @return
	 */
	public static Integer parseInt(String str) {
		Integer i = null;
		if (StringUtils.isEmpty(str)) {
			return i;
		}

		try {
			i = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		}

		return i;
	}

	/**
	 * 解析字符串为Integer
	 * 
	 * @param str
	 * @return
	 */
	public static Long parseLong(String str) {
		Long i = null;
		if (StringUtils.isEmpty(str)) {
			return i;
		}

		try {
			i = Long.parseLong(str);
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		}

		return i;
	}

}

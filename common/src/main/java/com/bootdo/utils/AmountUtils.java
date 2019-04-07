package com.bootdo.utils;

import java.text.DecimalFormat;
import java.text.FieldPosition;

/**
 * 金额工具类
 * 
 * @author created by zjw on 2019年2月26日 下午2:33:29
 */
public class AmountUtils {

	/**
	 * 将字符串"元"转换成"分".
	 * 
	 * @param str
	 * @return String
	 */
	public static String convertDollarToCent(String str) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		StringBuffer sb = df.format(Double.parseDouble(str), new StringBuffer(), new FieldPosition(0));
		int idx = sb.toString().indexOf(".");
		sb.deleteCharAt(idx);
		for (; sb.length() != 1;) {
			if (sb.charAt(0) == '0') {
				sb.deleteCharAt(0);
			} else {
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符串"分"转换成"元"（长格式），如：100分被转换为1.00元.
	 * 
	 * @param str
	 * @return String
	 */
	public static String convertCentToDollar(String str) {
		
		if (StringUtils.isBlank(str)) {
			return "";
		}
		
		long l;
		if (str.length() != 0) {
			if (str.charAt(0) == '+') {
				str = str.substring(1);
			}
			l = Long.parseLong(str);
		} else {
			return "";
		}
		boolean negative = false;
		if (l < 0) {
			negative = true;
			l = Math.abs(l);
		}
		str = Long.toString(l);
		if (str.length() == 1)
			return (negative ? ("-0.0" + str) : ("0.0" + str));
		if (str.length() == 2)
			return (negative ? ("-0." + str) : ("0." + str));
		else
			return (negative ? ("-" + str.substring(0, str.length() - 2) + "." + str.substring(str.length() - 2))
					: (str.substring(0, str.length() - 2) + "." + str.substring(str.length() - 2)));
	}

	/**
	 * 将字符串"分"转换成"元"（短格式），如：100分被转换为1元.
	 * 
	 * @param s
	 * @return
	 */
	public static String convertCentToDollarShort(String str) {
		
		String ss = convertCentToDollar(str);
		ss = "" + Double.parseDouble(ss);
		if (ss.endsWith(".0"))
			return ss.substring(0, ss.length() - 2);
		if (ss.endsWith(".00"))
			return ss.substring(0, ss.length() - 3);
		else
			return ss;
	}
}
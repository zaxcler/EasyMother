package com.Library.ToolsClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression
{
	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str)
	{
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str)
	{
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9)
		{
			m = p1.matcher(str);
			b = m.matches();
		}
		else
		{
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}
	
	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isNumber(String str)
	{
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^\\d+$|-\\d+$"); // 验证是否为整数
		m = p.matcher(str);
		b = m.matches();
		return b; 
	}
	
	/**
	 * 判断是否为小数
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
//	public static boolean isDecimals(String str)
//	{
//		Pattern p = null;
//		Matcher m = null;
//		boolean b = false;
//		p = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$"); // 验证是否为小数
//		m = p.matcher(str);
//		b = m.matches();
//		return b; 
//	}
	
	/**
	 * 判断是否为整数或小数
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isNumberDecimals(String str)
	{
		if(str==null)
			return false;
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("\\d+|-\\d+|\\d+\\.\\d+$|-\\d+\\.\\d+$"); // 验证是否为小数、整数
		m = p.matcher(str);
		b = m.matches();
		return b; 
	}
}

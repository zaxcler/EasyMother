package com.easymother.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtils {
	/**
	 * 判断是否是电话号码
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneString){
		//电话号码格式
		Pattern p=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m=p.matcher(phoneString);
		
		return m.matches();
		
	}

}

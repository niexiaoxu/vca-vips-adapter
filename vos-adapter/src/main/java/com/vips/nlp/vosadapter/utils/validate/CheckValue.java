package com.vips.nlp.vosadapter.utils.validate;

import java.util.List;
import java.util.Map;

/**
 * 检验非空工具类
 * @author niecheng
 */
public class CheckValue {
	
	public static boolean isEmpty(String str){
		if(str!=null && !str.trim().equals(""))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static boolean isEmpty(Integer str){
		if(str!=null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static boolean isEmpty(Object obj){
		if(obj!=null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isEmpty(Map<String,Object> map){
		if(map != null && map.size() > 0){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isEmpty(List<?> list){
		if(list == null || list.size() == 0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 字符串是否为数字
	 * @param str
	 * @author niecheng
	 * @date 2015年1月13日
 	 * @since jdk1.7
	 * @return
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

}

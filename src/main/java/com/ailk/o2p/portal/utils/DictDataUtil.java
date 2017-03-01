package com.ailk.o2p.portal.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DictDataUtil
 * @Description: 
 * @author zhengpeng
 * @date 2015-7-12 下午5:20:39
 *
 */
public class DictDataUtil {
	
	private static List<Map<String,Object>> provinceList = new ArrayList<Map<String,Object>>();
	private static Map<String,List<Map<String,Object>>> cityList = new HashMap<String,List<Map<String,Object>>>();
	
	public static Map<String, List<Map<String, Object>>> getCityList() {
		return cityList;
	}

	public static void setCityList(Map<String, List<Map<String, Object>>> cityList) {
		DictDataUtil.cityList = cityList;
	}

	public static void setProvinceList(List<Map<String,Object>> provinceList){
		DictDataUtil.provinceList = provinceList;
	}
	
	public static List<Map<String,Object>> getProvinceList(){
		return provinceList;
	}

}

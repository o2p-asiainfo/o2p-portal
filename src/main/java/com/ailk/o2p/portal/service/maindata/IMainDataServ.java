package com.ailk.o2p.portal.service.maindata;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;

public interface IMainDataServ {
	public List<MainDataType> selectMainDataType(MainDataType mainDataType);
	public List<MainData> selectMainData(MainData mainData) ;
	
	/**
	 * 根据租户查询基础数据
	 * @param mainDataType
	 * @param flag
	 * @return
	 */
	public Object getMainInfo(String mainTypeSign,Object operateCode);
}
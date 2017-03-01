package com.ailk.o2p.portal.service.orderManager;

import java.util.Map;
import java.util.List;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;


public interface IOrderManagerService {
	
	public List<MainData> selectMainData(MainData mainData);
	
	public List<MainDataType> selectMainDataType(MainDataType mainDataType);
	
	public List<Map> queryOrderInfoList(Map map);
	
	public List<Map> queryCrmOrderById(Map map);
	
	public List<Map> queryCrmOrderUserById(Map map);
	
	public List<Map> queryCrmOrderCustomerById(Map map);
	
	public List<Map> queryCrmOrderProductById(Map map);
	
	public List<Map> queryCrmUserAddressById(Map map);
	
	public List<Map> queryAttrSpec();

}

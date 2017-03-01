package com.ailk.o2p.portal.dao.orderManager;

import java.util.Map;
import java.util.List;


@SuppressWarnings("all")
public interface IOrderManagerDao {
	
	public List<Map> queryCrmOrderProductById(Map map);
	
	public List<Map> queryOrderInfoList(Map map);
	
	public List<Map> queryCrmOrderById(Map map);
	
	public List<Map> queryCrmOrderUserById(Map map);
	
	public List<Map> queryCrmOrderCustomerById(Map map);
	
	public List<Map> queryCrmUserAddressById(Map map);
	
	public List<Map> queryAttrSpec(Map paramMap);
	

}

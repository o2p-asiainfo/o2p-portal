package com.ailk.o2p.portal.service.orderManager;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.o2p.portal.dao.orderManager.IOrderManagerDao;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;


@Service
public class OrderManagerService implements IOrderManagerService{
	@Autowired
	private MainDataDao mainDataSqlDAO;
	@Autowired
	private MainDataTypeDao mainDataTypeSqlDAO;
	@Autowired
	private IOrderManagerDao orderManagerDao;
	
	public IOrderManagerDao getOrderManagerDao() {
		return orderManagerDao;
	}
	public void setOrderManagerDao(IOrderManagerDao orderManagerDao) {
		this.orderManagerDao = orderManagerDao;
	}

	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}


	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}
	
	public List<MainData> selectMainData(MainData mainData){
		return mainDataSqlDAO.selectMainData(mainData) ;
	}
	
	public List<MainDataType> selectMainDataType(MainDataType mainDataType){
		return mainDataTypeSqlDAO.selectMainDataType(mainDataType) ;
	}
	
	public List<Map> queryOrderInfoList(Map map){
		return orderManagerDao.queryOrderInfoList(map);
	}
	
	public List<Map> queryCrmOrderById(Map map){
		return orderManagerDao.queryCrmOrderById(map);
	}
	
	public List<Map> queryCrmOrderUserById(Map map){
		return orderManagerDao.queryCrmOrderUserById(map);
	}
	
	public List<Map> queryCrmOrderCustomerById(Map map){
		return orderManagerDao.queryCrmOrderCustomerById(map);
	}
	
	public List<Map> queryCrmOrderProductById(Map map){
		return orderManagerDao.queryCrmOrderProductById(map);
	}
	
	public List<Map> queryCrmUserAddressById(Map map){
		return orderManagerDao.queryCrmUserAddressById(map);
	}
	
	public List<Map> queryAttrSpec(){
		Map paramMap=new HashMap(); 
		return orderManagerDao.queryAttrSpec(paramMap);
	}

}

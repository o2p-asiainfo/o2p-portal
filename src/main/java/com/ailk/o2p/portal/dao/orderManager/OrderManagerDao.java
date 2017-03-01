package com.ailk.o2p.portal.dao.orderManager;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NData;
import com.ailk.eaap.op2.bo.i18n.ProvideI18NDatas;
import com.linkage.rainbow.dao.SqlMapDAO;

@Repository 
public class OrderManagerDao implements IOrderManagerDao{

	@Autowired
	private SqlMapDAO sqlMapDao;

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	
	public List<Map> queryAttrSpec(Map paramMap){
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-orderManager.queryAttrSpec", paramMap);
	}
	
	public List<Map> queryCrmUserAddressById(Map map){
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-orderManager.queryCrmUserAddressById", map);
	}
	
	
	@ProvideI18NDatas(values = { 
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "actTypeId", propertyNames = "ACTTYPE")
		}
	)
	public List<Map> queryCrmOrderProductById(Map map){
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-orderManager.queryCrmOrderProductById", map);
	}
	
	
	@ProvideI18NDatas(values = { 
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "userTypeId", propertyNames = "USERTYPE")
		}
	)
	public List<Map> queryCrmOrderCustomerById(Map map){
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-orderManager.queryCrmOrderCustomerById", map);
	}
	
	
	@ProvideI18NDatas(values = { 
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "userTypeId", propertyNames = "USERTYPE")
		}
	)
	public List<Map> queryCrmOrderUserById(Map map){
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-orderManager.queryCrmOrderUserById", map);
	}
	
	
	@ProvideI18NDatas(values = { 
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "cepId", propertyNames = "CEPNAME"),
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "tradeTypeCodeId", propertyNames = "TRADETYPECODE"),
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "orderStatusId", propertyNames = "ORDERSTATUS")
		}
	)
	public List<Map> queryCrmOrderById(Map map){
		return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-orderManager.queryCrmOrderById", map);
	}
	
	
	@ProvideI18NDatas(values = { 
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "cepId", propertyNames = "CEPNAME"),
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "tradeTypeCodeId", propertyNames = "TRADETYPECODE"),
			@ProvideI18NData(tableName = "main_data", columnNames = "CEP_NAME", idName = "orderStatusId", propertyNames = "ORDERSTATUSNAME")
		}
	)
	public List<Map> queryOrderInfoList(Map map) {
		if("ALLNUM".equals(String.valueOf(map.get("queryType")))){
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-orderManager.queryCrmOrderCount", map);
		}else{
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-orderManager.queryCrmOrderList", map);
		}
    }

}

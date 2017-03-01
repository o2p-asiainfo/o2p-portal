package com.ailk.o2p.portal.service.maindata;
 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.Channel;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannel;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.o2p.portal.dao.pardoffer.IPardOfferDao;
import com.ailk.o2p.portal.utils.TenantInterceptor;
@Service
public class MainDataServ implements IMainDataServ {
	public final static String MAININFO_LIST="list";
	public final static String MAININFO_MAP="map";
	@Autowired
    private MainDataDao mainDataSqlDAO ;
	@Autowired
	private MainDataTypeDao mainDataTypeSqlDAO;
	
	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}
	
	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}


	public MainDataDao getMainDataSqlDAO() {
		return mainDataSqlDAO;
	}

	public MainDataTypeDao getMainDataTypeSqlDAO() {
		return mainDataTypeSqlDAO;
	}

	public List<MainDataType> selectMainDataType(MainDataType mainDataType){
		return mainDataTypeSqlDAO.selectMainDataType(mainDataType) ;
	}

	public List<MainData> selectMainData(MainData mainData) {
		return mainDataSqlDAO.selectMainData(mainData) ;
	}
	public List getMainInfo(String mainTypeSign) {
		Object obj=getMainInfo(mainTypeSign,MAININFO_LIST);
		if(obj!=null)return (List)obj;
		else return null;
	}
	public Object getMainInfo(String mainTypeSign,String flag) {
		MainDataType mainDataType = new MainDataType();
		Map stateMap = null;
		mainDataType.setMdtSign(mainTypeSign);
		mainDataType.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE);
		List<MainDataType> mdtList = this.selectMainDataType(mainDataType);
		if(mdtList.size()>0){
			mainDataType = mdtList.get(0);
			stateMap = new LinkedHashMap();
			MainData mainData = new MainData();
			mainData.setMdtCd(mainDataType.getMdtCd());
			mainData.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE);
			List<MainData> mainDataList = this.selectMainData(mainData);
			if(MAININFO_LIST.equals(flag))return mainDataList;
			if (mainDataList != null && mainDataList.size() > 0) {
				for (int i = 0; i < mainDataList.size(); i++) {
					stateMap.put(mainDataList.get(i).getCepCode(), mainDataList.get(i).getCepName());
				}
			}
		}
		return stateMap;
	}
	
	/**
	 * 根据租户查询基础数据
	 * @param mainDataType
	 * @param flag
	 * @return
	 */
	public Object getMainInfo(String mainTypeSign,Object operateCode){
		Map stateMap = null;
		if(operateCode != null && EAAPConstants.isCloud()){
			MainDataType mainDataType = new MainDataType();
			mainDataType.setMdtSign(mainTypeSign);
			mainDataType.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE);
			mainDataType.setOperateCode(String.valueOf(operateCode));
			
			List<MainDataType> mdtList = this.selectMainDataType(mainDataType);
			if(mdtList != null & mdtList.size() > 0){
				mainDataType = mdtList.get(0);
				stateMap = new LinkedHashMap();
				MainData mainData = new MainData();
				mainData.setMdtCd(mainDataType.getMdtCd());
				mainData.setState(EAAPConstants.EAAP_MAIN_DATA_ONLINE);
				mainData.setOperateCode(String.valueOf(operateCode));
				
				List<MainData> mainDataList = this.selectMainData(mainData);
				return mainDataList;
			}
			return stateMap;
		}
		return getMainInfo(mainTypeSign,MAININFO_LIST);
	}
}

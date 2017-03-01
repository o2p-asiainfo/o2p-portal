package com.ailk.o2p.portal.dao.mgr;

  
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 







import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.App;
import com.ailk.eaap.op2.bo.AppApiList;
import com.ailk.eaap.op2.bo.AppType;
import com.ailk.eaap.op2.bo.Component;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.SerInvokeIns;
import com.ailk.eaap.op2.bo.Service;
import com.ailk.eaap.op2.common.EAAPException;

 

public interface DevMgrDao {
	public String selectSeqApp();
	public List<AppType> selectAppType(AppType appTypeBean) ;
	public String saveCom(Component componentBean);
	public Integer saveApp(App appBean) ;
	public List<App> selectAPP(App appBean);
	
	public List<Map> selectService(Api api) ;
	
	
	public Integer insertSerInvokeIns(SerInvokeIns serInvokeIns) ;
	public List<Component> selectCom(Component componentBean);
	
	public Integer updateApp(App appBean) ;
	public Integer updateAppApiListByAppId(App appBean);
	public Integer updateAppApiListByIdAndState(App appBean) ;
	
	public List<Map> selectApiInfo(App appBean) throws EAAPException;
	public List<AppApiList> selectAppApiList(AppApiList appApiBean);
	public void deleteAppApiListByAppId(App appBean);
	public Integer insertAppApiList(Map appApiBean);
	public void deleteAppApiList(AppApiList appApiBean) ;
     public List<Map> queryAppApiNum(App appBean) ;
     public List<Map> queryServiceByAppId(App appBean) ;
     public List<Map> queryAppStatistics(String queryType,Map map) ;
     
     public List<Map> queryApiStatistics(String queryType,Map map) ;
     
     public Integer  queryAppApiCount (Map map)throws EAAPException;
     public List<Map>  queryAppApiList (Map map);
     
     public List<Map> selectApiInfoNew(Map map) throws EAAPException;
     public Integer selectApiInfoNewCount(Map map) throws EAAPException;
     public List<Map> queryApiIds(Map map) throws EAAPException;
     
     
     public Integer insertOrgOfferRel(Map map);
     public List<Map> getCategory(Map map) throws EAAPException;
     public List<Map> queryOffersInfo(Map map) throws EAAPException;
     public List<Map> getOffersIds(Map map) throws EAAPException; 
     public void updateAPPwithApi(Map map);
     public void updateOrgwithOffer(Map map);
 	 public List<Map<String, Object>> getPricingPlanCycle(Map map);
 	 public Integer insertPricingCase(Map map);
 	public List<Map<String, Object>> checkOfferhasBooks(Map map);
 	public List<Map> selectServiceById(Service service);
	public List<Map<String, String>> getWebServiceMsg(Map paramMap);	
	/**
	 * 由服务调用实例得到webservice api的调用地址
	 * @param serInvokeInsId
	 * @return
	 */
	public List<Map<String, String>> getWebServiceApiMsg(Map paramMap);
	public List<Map<String, String>> getSerInvokeInsId(Map map);
	public List<Map> queryChargeInfo(Map map);
	public List<Map<String, Object>> querydevAPICharges(Map map);
	public Integer querydevAPIChargesCou(Map map);
	public List<Map<String, Object>> getAPPList(Map map);
	
	public List<Map<String, Object>> getAPIList(Map map);
	
	public List<Map<String, Object>> detaildevAPICharges(Map map);
	public Integer detaildevAPIChargesCou(Map map);
	public Integer queryOffersInfoCount(Map map);

	public Integer updateOrgInfo(Org orgBean) ;
	
     
}

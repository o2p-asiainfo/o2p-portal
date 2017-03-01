package com.ailk.o2p.portal.dao.mgr;

import java.sql.Timestamp;
import java.util.ArrayList; 
import java.util.List; 
import java.util.Map;

import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.App;
import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.AppType;
import com.ailk.eaap.op2.bo.AppApiList;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.SerInvokeIns;
import com.ailk.eaap.op2.bo.Service;
import com.ailk.eaap.op2.common.EAAPErrorCodeDef;
import com.ailk.eaap.op2.common.EAAPException;
import com.ailk.eaap.op2.common.EAAPTags;
import com.linkage.rainbow.dao.SqlMapDAO;

public class DevMgrDaoImpl   implements DevMgrDao {
	private SqlMapDAO sqlMapDao;
	private SqlMapDAO sqlMapDaoDep;
	public String selectSeqApp(){
		return (String)sqlMapDao.queryForObject("app.selectSeqApp", null);
	}
	public List<AppType> selectAppType(AppType appTypeBean){
		return  (ArrayList<AppType>)sqlMapDao.queryForList("app_type.selectAppTypeById", appTypeBean) ;
	     
	}
	
	public List<Component> selectCom(Component componentBean){
		return (ArrayList<Component>)sqlMapDao.queryForList("component.selectComponent", componentBean) ;
	     
	}
	public List<Map> selectService(Api api){
		return (ArrayList<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.selectService", api) ;
	    
	}
	
	
	public List<App> selectAPP(App appBean){
		return (ArrayList<App>)sqlMapDao.queryForList("app.selectApp", appBean) ;
	     
	}
	
	
	public String saveCom(Component componentBean){
		return (String)sqlMapDao.insert("component.insertComponent", componentBean);
	 }
	public Integer saveApp(App appBean){
		return (Integer)sqlMapDao.insert("app.insertApp", appBean) ;
	}
	public Integer updateApp(App appBean){
		return (Integer)sqlMapDao.update("app.updateApp", appBean) ;
	}
	
	public Integer updateAppApiListByIdAndState(App appBean){
		return (Integer)sqlMapDao.update("appApiList.updateAppApiListByIdAndState", appBean) ;
	}
	
	public Integer updateAppApiListByAppId(App appBean){
		return (Integer)sqlMapDao.update("appApiList.updateAppApiListByAppId", appBean) ;
	}
	
	
	public List<Map> selectApiInfo(App appBean) throws EAAPException{
		
		try{
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryApiList", appBean) ;
		}catch(Exception e){
		    throw new EAAPException(EAAPTags.SEG_DEVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
			      new Timestamp(System.currentTimeMillis()) + ":��ȡapi�б��쳣", e);
	   }
		
	}
	
     public List<Map> queryAppApiNum(App appBean) {
		
		 
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryAppApiNum", appBean) ;
		 
		
	 }

     
      public List<Map> queryAppStatistics(String queryType,Map map) {
 		
    	  if("countByday".equals(queryType)){
    	    return (List<Map>)sqlMapDaoDep.queryForList("eaap-op2-portal-devMgr.queryAppStatisticsByDayCount", map) ;
    	 }else if("countByMonth".equals(queryType)){
    		 return (List<Map>)sqlMapDaoDep.queryForList("eaap-op2-portal-devMgr.queryAppStatisticsCount", map) ;
    	 }else  if("appByMonth".equals(queryType)){
 			return (List<Map>)sqlMapDaoDep.queryForList("eaap-op2-portal-devMgr.queryAppStatistics", map) ;
 		 }else{
 			return (List<Map>)sqlMapDaoDep.queryForList("eaap-op2-portal-devMgr.queryAppStatisticsByDay", map) ;
 		 } 
 			
 		 
 		
 	 }
      
       public List<Map> queryApiStatistics(String queryType,Map map) {
    	   if("countByday".equals(queryType)){
    		   return (List<Map>)sqlMapDaoDep.queryForList("eaap-op2-portal-devMgr.queryApiStatisticsByDayCount", map) ;
    	 }else if("countByMonth".equals(queryType)){
    		 return (List<Map>)sqlMapDaoDep.queryForList("eaap-op2-portal-devMgr.queryApiStatisticsCount", map) ;
    	 }else if("apiByMonth".equals(queryType)){
  			return (List<Map>)sqlMapDaoDep.queryForList("eaap-op2-portal-devMgr.queryApiStatistics", map) ;
  		 }else if("apiByDay".equals(queryType)){
  			return (List<Map>)sqlMapDaoDep.queryForList("eaap-op2-portal-devMgr.queryApiStatisticsByDay", map) ;
  		 }else{
  			return null;
  		 }
  	 }
       
     
       public List<Map> queryServiceByAppId(App appBean) {
   		  return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryServiceByAppId", appBean) ;
   	 }
      
       
	
	public List<AppApiList> selectAppApiList(AppApiList appApiBean){
		return (ArrayList<AppApiList>)sqlMapDao.queryForList("appApiList.selectAppApiList", appApiBean) ;
		
	}
	
	public void deleteAppApiListByAppId(App appBean){
	      sqlMapDao.delete("appApiList.deleteAppApiListByAppId", appBean) ;
	}
	
	public void deleteAppApiList(AppApiList appApiBean){
	      sqlMapDao.update("appApiList.updateAppApiList", appApiBean) ;
	}
	
	
	 
	public Integer insertSerInvokeIns(SerInvokeIns serInvokeIns){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-devMgr.insertSerInvokeIns", serInvokeIns) ;
	}
	
	
	
	public Integer insertAppApiList(Map appApiBean){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-devMgr.insertAppApiList", appApiBean) ;
	}
	
	public Integer queryAppApiCount(Map map) throws EAAPException {
		// TODO Auto-generated method stub
		Integer num = 0;
		num = (Integer) sqlMapDao.queryForObject("eaap-op2-portal-devMgr.queryAppApiCount",map);
		return num;
	}
	
	public List<Map> queryAppApiList(Map map) {
		// TODO Auto-generated method stub
		return sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryAppApiList",map);
		 
	}
	
	public List<Map> selectApiInfoNew(Map map) throws EAAPException{
		if(map.get("ORG_TYPE_CODES")!=null && !"".equals(map.get("ORG_TYPE_CODES").toString())){
			map.put("ORG_TYPE_CODES", map.get("ORG_TYPE_CODES").toString().split(","));
		}
		try{
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryApiListForOffer", map) ;
		}catch(Exception e){
			
		    throw new EAAPException(EAAPTags.SEG_DEVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
			      new Timestamp(System.currentTimeMillis()) + ":��ȡapi�б��쳣", e);
	   }
		
	}
	public Integer selectApiInfoNewCount(Map map) throws EAAPException {
		// TODO Auto-generated method stub
		Integer num = 0;
		num = (Integer) sqlMapDao.queryForObject("eaap-op2-portal-devMgr.countApiListForOffer",map);
		return num;
	}
	
	public List<Map> queryApiIds(Map map) throws EAAPException{
			if(map.get("offerId")!=null && !"".equals(map.get("offerId").toString())){
				map.put("offerId", map.get("offerId").toString().split(","));
			}
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryApiIds", map) ;
	
		
	}
	public List<Map> getCategory(Map map) throws EAAPException{
		 
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.getCategory", map) ;
		 
		
	}
	public List<Map> queryOffersInfo(Map map) throws EAAPException{
		try{
			if(map.get("PROD_OFFER_ID")!=null && !"".equals(map.get("PROD_OFFER_ID").toString())){
				map.put("PROD_OFFER_ID", map.get("PROD_OFFER_ID").toString().split(","));
			}
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryOffersInfo", map) ;
		}catch(Exception e){
			
		    throw new EAAPException(EAAPTags.SEG_DEVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
			      new Timestamp(System.currentTimeMillis()) + ":", e);
	   }
	}
    public List<Map> getOffersIds(Map map) throws EAAPException{
    	try{
    		if(map.get("API_IDS")!=null && !"".equals(map.get("API_IDS").toString())){
				map.put("API_IDS", map.get("API_IDS").toString().split(","));
			}
			return (List<Map>)sqlMapDao.queryForList("eaap-op2-portal-devMgr.getOffersIds", map) ;
		}catch(Exception e){
			
		    throw new EAAPException(EAAPTags.SEG_DEVAPP_SIGN, EAAPErrorCodeDef.WRITE_QUEUE_ERR_9012,
			      new Timestamp(System.currentTimeMillis()) + ":", e);
	   }
    }
	
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	public SqlMapDAO getSqlMapDaoDep() {
		return sqlMapDaoDep;
	}
	public void setSqlMapDaoDep(SqlMapDAO sqlMapDaoDep) {
		this.sqlMapDaoDep = sqlMapDaoDep;
	}
	
	public Integer insertOrgOfferRel(Map map){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-devMgr.insertOrgOfferRel", map) ;
		
	}
	public void updateAPPwithApi(Map map){
		
		sqlMapDao.update("eaap-op2-portal-devMgr.updateAPPwithApi", map);
	}
	public void updateOrgwithOffer(Map map){
		sqlMapDao.update("eaap-op2-portal-devMgr.updateOrgwithOffer", map);
	}
	public List<Map<String, Object>> getPricingPlanCycle(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-devMgr.getPricingPlanCycle", map);
	}
	public Integer insertPricingCase(Map map){
		return (Integer)sqlMapDao.insert("eaap-op2-portal-devMgr.insertPricingCase", map);

	}
	public List<Map<String, Object>> checkOfferhasBooks(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-devMgr.checkOfferhasBooks", map);
	}
	public List<Map> selectServiceById(Service service){
		return sqlMapDao.queryForList("eaap-op2-portal-devMgr.selectServiceById", service);
	}
	
	/**
	 * 由服务调用实例得到webservice的调用地址
	 * @param serInvokeInsId
	 * @return
	 */
	@Override
	public List<Map<String, String>> getWebServiceMsg(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-portal-devMgr.getWebServiceMsg", paramMap);
	}
	/**
	 * 由服务调用实例得到webservice的 api调用地址
	 * @param serInvokeInsId
	 * @return
	 */
	@Override
	public List<Map<String, String>> getWebServiceApiMsg(
			Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-portal-devMgr.getWebServiceApiMsg", paramMap);
	}
	
	public List<Map<String, String>> getSerInvokeInsId(Map map){
		return sqlMapDao.queryForList("eaap-op2-portal-devMgr.getSerInvokeInsId", map);
		
	}
	public List<Map> queryChargeInfo(Map map){
		Integer tenantId=Integer.parseInt(map.get("tenantId").toString());
		List<Map> father = new ArrayList<Map>();
		List<Map> listMap = sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryPricingPlan", map);
        if(listMap.size()>0){
	        for(int m=0;m<listMap.size();m++){
	        	Map temp = listMap.get(m);
	        	temp.put("tenantId", tenantId);
	        	List<Map> listcomp= sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryPricingComponent", temp);
	        	List<Map> comp = new ArrayList<Map>();
	        	for(int kk=0;kk<listcomp.size();kk++){
	        		Map tempcomp = listcomp.get(kk);
	        		tempcomp.put("tenantId", tenantId);
	        		List<Map> son = sqlMapDao.queryForList("eaap-op2-portal-devMgr.queryPricingDetail", tempcomp);
		        	tempcomp.put("ChargesDetail", son);
		        	comp.add(tempcomp);
	        	}
	        	temp.put("ChargesComp", comp);
				 father.add(temp);
	        }
        }
		return father;
	}
	
	public List<Map<String, Object>> querydevAPICharges(Map map){
		if(map.get("appIds")!=null && !"".equals(map.get("appIds").toString())){
			map.put("APP_IDS", map.get("appIds").toString().split(","));
		}
		return sqlMapDao.queryForList("eaap-op2-portal-prov.queryAPICharges", map);
		 
	}
	public Integer querydevAPIChargesCou(Map map){
		if(map.get("appIds")!=null && !"".equals(map.get("appIds").toString())){
			map.put("APP_IDS", map.get("appIds").toString().split(","));
		}
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prov.queryAPIChargesCou", map);
		 
	}
    public List<Map<String, Object>> getAPPList(Map map){
    	return sqlMapDao.queryForList("eaap-op2-portal-prov.getAPPList", map);
		 
    }
	
	public List<Map<String, Object>> getAPIList(Map map){
		if(map.get("appIds")!=null && !"".equals(map.get("appIds").toString())){
			map.put("APP_IDS", map.get("appIds").toString().split(","));
		}
		return sqlMapDao.queryForList("eaap-op2-portal-prov.getAPIList", map);
 
	}
	public List<Map<String, Object>> detaildevAPICharges(Map map){
	    return  sqlMapDao.queryForList("eaap-op2-portal-prov.detailAPICharges", map);
		 
	}
	public Integer detaildevAPIChargesCou(Map map){
		 
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-prov.detailAPIChargesCou", map);
		 
	}
	public Integer queryOffersInfoCount(Map map){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-devMgr.queryOffersInfoCount", map);
		
	}

	public Integer updateOrgInfo(Org orgBean){
		return (Integer)sqlMapDao.update("org.updateOrg", orgBean) ;
	}
	
	
}

package com.ailk.o2p.portal.service.mgr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.App;
import com.ailk.eaap.op2.bo.AppApiList;
import com.ailk.eaap.op2.bo.AppType;
import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.SerInvokeIns;
//import com.ailk.eaap.op2.bo.Service;
import com.ailk.eaap.op2.common.EAAPException;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.eaap.op2.dao.OrgDao;
import com.ailk.o2p.portal.dao.mgr.DevMgrDao;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.linkage.rainbow.util.StringUtil;
 
@Service
public class DevMgrServ implements IDevMgrServ {
	@Autowired
 	private DevMgrDao devMgrSqlDAO;
	private MainDataDao mainDataSqlDAO;
	private MainDataTypeDao mainDataTypeSqlDAO;
	private OrgDao orgSqlDAO;
	
	private static Log log = LogFactory.getLog(DevMgrServ.class);

	 /**
	  * 保存订购关系
	  */
	 public  String saveAppOrderApi(String offerId,App app){
		 
		 Map<String,Object> temp = new HashMap<String,Object>();
		 AppApiList appApiBean = new AppApiList() ;
		 List hasBook = null;
		// App app=new App();
		 List ids = null;
		 String apiIds[];
		 String serviceIds[];
		 
	  		if(offerId!=null){
	  			 String offerIds[] = offerId.split(",");
	  			 //insert  
	  			 for(int j=0;j<offerIds.length;j++){
	  				 String offerID = offerIds[j];
	  				 
	  				 temp.put("offerId", offerIds[j]);
	  				 temp.put("appDeve", app.getAppDeve());
	  				 temp.put("appId", app.getAppId());
	  				 //check has already order
	  				hasBook = checkOfferhasBooks(temp);
	  				if(hasBook.size()==0){
	  					temp.put("PROD_OFFER_ID", offerIds[j]);
	  					List prodOfferList = queryOffersInfo(temp);
	  					if(prodOfferList.size()>0){
	  						prodOfferList.get(0);
	  					}
	  					insertOrgOfferRel(temp);
		  				String offerApiRelaId = StringUtil.valueOf(temp.get("offerApiRelaId"));
		  				if(!"0".equals(offerApiRelaId)){
		  				//get price plan
			  				Map<String,Object> map = new HashMap<String,Object>();
			  				List<Map<String, Object>> pricePlan;
			  				map.put("prodOfferId", offerIds[j]);
			  				map.put("PROD_OFFER_ID", offerIds[j]);
			  				map.put("OFFER_API_RELA_ID", temp.get("offerApiRelaId"));
			  				 
			  				pricePlan = getPricingPlanCycle(map); 
			  				//insert pricing case 
			  				if(pricePlan.size()>0){
			  					Map plan;
			  					String effTime;
			  					String expTime;
			  					for(int i=0;i<pricePlan.size();i++){
			  						plan = pricePlan.get(i);
			  						map.put("PRICING_INFO_ID", plan.get("PRICINGINFOID"));
			  						
			  						effTime =getOffsetTime("1",plan);//生效时间
			  						plan.put("EFF_TIME", effTime);
			  						expTime =getOffsetTime("2",plan);//失效时间
			  						map.put("EFF_TIME", effTime);
			  						map.put("EXP_TIME", expTime);
			  						insertPricingCase(map);
			  					}
			  				}
		  				}
	  				}
	  			    
	  				//get apiId
	  				ids =queryApiIds(temp);
		  			if(ids.size()>0){
		  				apiIds = new String[ids.size()];
		  				serviceIds = new String[ids.size()];
		  			 for(int i=0;i<ids.size();i++){
		  				temp = (HashMap)ids.get(i);
		  				apiIds[i]= StringUtil.valueOf(temp.get("API_ID_R")) ;
		  				serviceIds[i]= StringUtil.valueOf(temp.get("API_ID")) ;
		  			 }
					 for(int i=0;i<apiIds.length;i++){
						    appApiBean.setAppId(app.getAppId());
							appApiBean.setApiId(Integer.valueOf(apiIds[i])) ;
							Map<String,Object> appApiBeanMap = new HashMap<String,Object>();
							appApiBeanMap.put("appId", app.getAppId());
							appApiBeanMap.put("apiId", Integer.valueOf(apiIds[i]));
							appApiBeanMap.put("prodOfferId", offerID);
							
							insertAppApiList(appApiBeanMap) ;
							com.ailk.eaap.op2.bo.Service service = new com.ailk.eaap.op2.bo.Service(); 
							 SerInvokeIns serInvokeIns = new SerInvokeIns() ;
							 service.setServiceId(Integer.valueOf(serviceIds[i])) ;
							 Map<String,Object> serviceMap = (HashMap)selectServiceById(service).get(0);
							 serInvokeIns.setComponentId(selectAPP(app).get(0).getComponentId()) ;
							 serInvokeIns.setMessageFlowId(Integer.valueOf((serviceMap.get("DEFAULT_MSG_FLOW")==null?"0":serviceMap.get("DEFAULT_MSG_FLOW")).toString()));
							 serInvokeIns.setServiceId(Integer.valueOf(serviceMap.get("SERVICE_ID").toString()));
							 serInvokeIns.setSerInvokeInsName(serviceMap.get("SERVICE_CN_NAME").toString()+":"+serviceMap.get("SERVICE_ID").toString()) ;
							 //查询是否已经订购
							 //获取serInvokeInsId
							   List list = null;
							   temp.put("serviceId", serviceMap.get("SERVICE_ID"));
								temp.put("componentId",serInvokeIns.getComponentId());
								list = getSerInvokeInsId(temp);
								if(list.size()<=0){
									insertSerInvokeIns(serInvokeIns) ;
							     }
					
					 }
		  			}
	  				 
	  			 }
	  			 
	  	 
	  			
			 }

		 return"";
	 }
		/**
		 * //Calculation  OffsetTime
		 * getOffsetTime:(这里用一句话描述这个方法的作用). <br/> 
		 
		 * 
		 * @author m 
		 * @param type
		 * @param plan
		 * @return 
		 * @since JDK 1.6
		 */
		public String getOffsetTime(String type ,Map plan){
			String effMod="0";//0: Immediately 1: Delay
			String  delayMod ;
			int  delayUni ;
			 
			Date date = new Date();
			String dates="";
			//Calculation  
			 
			effMod = StringUtil.valueOf(plan.get("SUB_EFFECT_MODE"));
			
			if("1".equals(type)){
				//eff time
				if("0".equals(effMod)){
					dates = efforExpTime("1",date,effMod,"",-1);
				}else if("1".equals(effMod)){
					delayMod = (String)plan.get("SUB_DELAY_CYCLE");
					String t = StringUtil.valueOf(plan.get("SUB_DELAY_UNIT"));
					delayUni = Integer.parseInt(t);
					dates = efforExpTime("1",date,effMod,delayMod,delayUni);
				}
			}else{
				String expDate=StringUtil.valueOf(plan.get("EFF_TIME"));
				Date dd = new Date();
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		        try {
		            dd = formatDate.parse(expDate);
		        } catch (Exception e) {
		        	log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));

		        }
			    delayMod = (String)plan.get("VALID_TYPE");
				String t = StringUtil.valueOf(plan.get("VALID_UNIT"));
				delayUni = Integer.parseInt(t);
				dates = efforExpTime("2",dd,"",delayMod,delayUni);
			}
			
			
			return  dates;
			
		}
		
		/**
		  * 生失效时间偏移函数
		  * main:(这里用一句话描述这个方法的作用). <br/> 
		  * TODO timeType 时间类型 1 表示 生效时间  2表示 失效时间（先计算生效时间，后计算失效时间）
		  * TODO time 时间（生效时间/失效时间）
		  *      生效类型  isOffset  （0 当前生效  1 偏移月生效 ）
		  * TODO 偏移类型 offset （  ）
		  * 1	: By day
			2: 	By month
			3: 	By bill
			4: By hour
		  *     
		  * TODO n 偏移周期数（若为当前生效 则 为0，下月生效为1，下下月生效为2）
		  * 
		  * @author m  
		  */
		 public  String efforExpTime(String timeType,Date time,String isOffset,String offset,int n){
		
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		         String rstTime;
				 rstTime = dateFormat.format( time );  
				 Calendar cal = Calendar.getInstance();//可以对每个时间域单独修改
				 cal.setTime(time);
				 if("0".equals(isOffset)&&"1".equals(timeType)){
					 return rstTime;
				 }
				 String timeFormat="00:00:00";
				 if("1".equals(timeType)){
					 //如果是偏移天
					 String dateYear = "";
					 String dateMon = "";
					 String dateday = "";
					 if("1".equals(offset)){
						 cal.add(Calendar.DATE, n); 
						 cal.add(Calendar.MONTH, 1);
						 int year = cal.get(Calendar.YEAR); 
						 int month = cal.get(Calendar.MONTH);
						 int day = cal.get(Calendar.DATE); 
						 if(month==0){
							  cal.add(Calendar.YEAR,-1); 
							  year = cal.get(Calendar.YEAR); 
							 dateMon = "12";
						 }else if(month<10){
							 dateMon = "0"+month;
						 }else{dateMon = month+"";}
						 if(day<10){
							 dateday = "0"+day;
						 }else{dateday = day+"";}
						 dateYear=year+"";
						 rstTime = year + "-" + dateMon + "-"+dateday + " " +timeFormat; 
					 }else if("2".equals(offset)){ 
						 //如果是偏移月
						 cal.add(Calendar.MONTH, n+1); 
					
						 int year = cal.get(Calendar.YEAR); 
						 int month = cal.get(Calendar.MONTH); 
						 if(month==0){
							 cal.add(Calendar.YEAR,-1); 
							  year = cal.get(Calendar.YEAR); 
							 dateMon = "12";
						 }else if(month<10){
							 dateMon = "0"+month;
						 }else{dateMon = month+"";}
						 dateYear=year+"";
						 rstTime = dateYear + "-" + dateMon + "-01"+ " " +timeFormat;
					 }
					
				 }else if("2".equals(timeType)){
					 //如果是偏移天
					 String dateYear = "";
					 String dateMon = "";
					 String dateday = "";
					 if("1".equals(offset)){
						 cal.add(Calendar.DATE, n); 
						 cal.add(Calendar.MONTH, 1);
						 int year = cal.get(Calendar.YEAR); 
						 int month = cal.get(Calendar.MONTH);
						 int day = cal.get(Calendar.DATE); 
						 if(month==0){
							  cal.add(Calendar.YEAR,-1); 
							  year = cal.get(Calendar.YEAR); 
							 dateMon = "12";
						 }else if(month<10){
							 dateMon = "0"+month;
						 }else{dateMon = month+"";}
						 if(day<10){
							 dateday = "0"+day;
						 }else{dateday = day+"";}
						 dateYear=year+"";
						 rstTime = year + "-" + dateMon + "-"+dateday + " " +timeFormat; 
					 }else if("3".equals(offset)){ 
						 //如果是偏移月
						 cal.add(Calendar.MONTH, n+1); 
					
						 int year = cal.get(Calendar.YEAR); 
						 int month = cal.get(Calendar.MONTH); 
						 if(month==0){
							 cal.add(Calendar.YEAR,-1); 
							  year = cal.get(Calendar.YEAR); 
							 dateMon = "12";
						 }else if(month<10){
							 dateMon = "0"+month;
						 }else{dateMon = month+"";}
						 dateYear=year+"";
						 rstTime = dateYear + "-" + dateMon + "-01"+ " " +timeFormat;
					 }
					
				 }
				 
				 return rstTime  ;
			 }
	@Transactional	 
	public JSONArray apiOfferListSrv(Map temp){
		//Map paramMap = new HashMap();
		JSONArray dataList=new JSONArray(); 
		JSONArray subDataList=null; 
		List<Map> packages = queryOffersInfo(temp);
		List<Map> father = new ArrayList<Map>();
		if(packages.size()>0){
			List<Map> son = null;
			List<Map> charge = null;
			for(int m=0;m<packages.size();m++){
				
				temp = packages.get(m);
				temp.put("offerId", temp.get("PROD_OFFER_ID"));
				//son = devMgrService.queryApiIds(temp);
				 
                charge = queryChargeInfo(temp); 
                String chargeString="";
				if(charge.size()>0){
					chargeString = pricePlanToString(charge);
				}else{
					 
					if(temp.get("PRICING_NAME_S")==null||"".equals(temp.get("PRICING_NAME_S"))){
						temp.put("PRICING_NAME_S", "");
					}
				}
				
				temp.put("TRstring", chargeString);
				//temp.put("APIs", son);
				temp.put("Charges", charge);
				father.add(temp);
				
				//数据转换成 页面格式
				subDataList=new JSONArray();
				subDataList.add("<input type='checkbox' value='' class='checkboxes' onChange='offerCheck(this)' id='"+temp.get("PROD_OFFER_ID")+"' name='' />");
//				subDataList.add(pardSpecMap.get("PROD_OFFER_ID"));
//				subDataList.add(pardSpecMap.get("OFFER_PROVIDER_ID"));
				subDataList.add(temp.get("PACKAGENAME"));
				subDataList.add(temp.get("API_NAME_S"));
				//subDataList.add(pardSpecMap.get("PRICING_NAME_S"));
				//subDataList.add("<a data-content='&lt;h5&gt;title&lt;/h5&gt;&lt;p&gt;And here some amazing content. It very engaging. Right?&lt;/p&gt;&lt;h5&gt;title&lt;/h5&gt;&lt;p&gt;And here some amazing content. It very engaging. Right?&lt;/p&gt;' title='' data-toggle='popover' data-placement='left' href='javascript:;' data-original-title='Popover title'>"+pardSpecMap.get("PRICING_NAME_S")+"</a>");
				subDataList.add("<a data-content='"+temp.get("TRstring")+"' title='' data-toggle='popover' data-placement='left' href='javascript:;' data-original-title='Price Plan'>"+temp.get("PRICING_NAME_S")+"</a>");
				
				subDataList.add(temp.get("PROD_OFFER_DESC"));
				dataList.add(subDataList);
			}
		}
		return dataList;
	}
	
	 public String pricePlanToString(List<Map> charge){
		 StringBuilder chargeString=new StringBuilder();
			List<Map> ChargesComp;
			List<Map> ChargesDetail;
			StringBuilder CompString=null;
			StringBuilder DetailString=null;
			for(Map chargeInfo:charge){
				ChargesComp=(ArrayList)chargeInfo.get("ChargesComp");
				chargeString.append("&lt;h5&gt;").append(chargeInfo.get("PRICING_NAME")).append("&lt;/h5&gt;&lt;p&gt;"); 
				CompString=new StringBuilder();
				for(Map CompInfo:ChargesComp){
					CompString.append("Every").append(CompInfo.get("RATING_UNIT_VAL")).append(" accesses will be a charging unit from ").append(CompInfo.get("EFF_DATE")).append(" to ").append(CompInfo.get("EXP_DATE")).append("<br/>");
					ChargesDetail=(ArrayList)CompInfo.get("ChargesDetail");
					DetailString=new StringBuilder();
					for(Map DetailInfo:ChargesDetail){
						DetailString.append(DetailInfo.get("START_VAL")).append(" ~ ").append(DetailInfo.get("END_VAL")).append(" units will charge ").append(DetailInfo.get("RATE_VAL")).append("<br/>");
						}
					CompString.append(DetailString.toString());
				}
				chargeString.append(CompString.append("&lt;/p&gt;").toString());
				
			}
			return chargeString.toString();
		
	 }
	@Transactional
	public String apiOfferInsertSrv(Map temp,String offerId,String apiId ){
		String serviceIds[];
		App app=new App();
		AppApiList appApiBean = new AppApiList() ;
		List ids = null;
		List hasBook = null;
		String offerIds[] = offerId.split(",");
		String apiIds[] = apiId.split(",");
			 //insert  
			 for(int j=0;j<offerIds.length;j++){
				 String offerID = offerIds[j];
				 
				 temp.put("offerId", offerIds[j]);
				 temp.put("appDeve", app.getAppDeve());
				 temp.put("appId", app.getAppId());
				 //check has already order
				hasBook = checkOfferhasBooks(temp);
				if(hasBook.size()==0){
					temp.put("PROD_OFFER_ID", offerIds[j]);
					List prodOfferList = queryOffersInfo(temp);
					if(prodOfferList.size()>0){
						prodOfferList.get(0);
					}
					insertOrgOfferRel(temp);
 				String offerApiRelaId = StringUtil.valueOf(temp.get("offerApiRelaId"));
 				if(!"0".equals(offerApiRelaId)){
 				//get price plan
	  				Map<String,Object> map = new HashMap<String,Object>();
	  				List<Map<String, Object>> pricePlan;
	  				map.put("prodOfferId", offerIds[j]);
	  				map.put("PROD_OFFER_ID", offerIds[j]);
	  				map.put("OFFER_API_RELA_ID", temp.get("offerApiRelaId"));
	  				 
	  				pricePlan = getPricingPlanCycle(map); 
	  				//insert pricing case 
	  				if(pricePlan.size()>0){
	  					Map plan;
	  					String effTime;
	  					String expTime;
	  					for(int i=0;i<pricePlan.size();i++){
	  						plan = pricePlan.get(i);
	  						map.put("PRICING_INFO_ID", plan.get("PRICINGINFOID"));
	  						
	  						effTime =getOffsetTime("1",plan);//生效时间
	  						plan.put("EFF_TIME", effTime);
	  						expTime =getOffsetTime("2",plan);//失效时间
	  						map.put("EFF_TIME", effTime);
	  						map.put("EXP_TIME", expTime);
	  						insertPricingCase(map);
	  					}
	  				}
 				}
				}
			    
				//get apiId
				ids =queryApiIds(temp);
 			if(ids.size()>0){
 				apiIds = new String[ids.size()];
 				serviceIds = new String[ids.size()];
 			 for(int i=0;i<ids.size();i++){
 				temp = (HashMap)ids.get(i);
 				apiIds[i]= StringUtil.valueOf(temp.get("API_ID_R")) ;
 				serviceIds[i]= StringUtil.valueOf(temp.get("API_ID")) ;
 			 }
			 for(int i=0;i<apiIds.length;i++){
				    appApiBean.setAppId(app.getAppId());
					appApiBean.setApiId(Integer.valueOf(apiIds[i])) ;
					Map<String,Object> appApiBeanMap = new HashMap<String,Object>();
					appApiBeanMap.put("appId", app.getAppId());
					appApiBeanMap.put("apiId", Integer.valueOf(apiIds[i]));
					appApiBeanMap.put("prodOfferId", offerID);
					
					insertAppApiList(appApiBeanMap) ;
					com.ailk.eaap.op2.bo.Service service = new com.ailk.eaap.op2.bo.Service(); 
					 SerInvokeIns serInvokeIns = new SerInvokeIns() ;
					 service.setServiceId(Integer.valueOf(serviceIds[i])) ;
					 Map serviceMap = (HashMap)selectServiceById(service).get(0);
					 serInvokeIns.setComponentId(selectAPP(app).get(0).getComponentId()) ;
					 serInvokeIns.setMessageFlowId(Integer.valueOf((serviceMap.get("DEFAULT_MSG_FLOW")==null?"0":serviceMap.get("DEFAULT_MSG_FLOW")).toString()));
					 serInvokeIns.setServiceId(Integer.valueOf(serviceMap.get("SERVICE_ID").toString()));
					 serInvokeIns.setSerInvokeInsName(serviceMap.get("SERVICE_CN_NAME").toString()+":"+serviceMap.get("SERVICE_ID").toString()) ;
					 //查询是否已经订购
					 //获取serInvokeInsId
					   List list = null;
					   temp.put("serviceId", serviceMap.get("SERVICE_ID"));
						temp.put("componentId",serInvokeIns.getComponentId());
						list = getSerInvokeInsId(temp);
						if(list==null){
							insertSerInvokeIns(serInvokeIns) ;
					     }
			
			 }
 			}
				 
			 }
			 
	 
			
	 
		 return "";
	}
    @Transactional
	public Integer updateOrgInfo(Org org) {
		//return orgSqlDAO.updateOrgInfo(org);
		return devMgrSqlDAO.updateOrgInfo(org);
	}
    @Transactional
	public List<MainData> selectMainData(MainData mainData) {
		return mainDataSqlDAO.selectMainData(mainData);
	}
    @Transactional
	public List<MainDataType> selectMainDataType(MainDataType mainDataType) {
		return mainDataTypeSqlDAO.selectMainDataType(mainDataType);
	}
    @Transactional
	public String selectSeqApp() {
		return devMgrSqlDAO.selectSeqApp();
	}
    @Transactional
	public List<Map> selectService(Api api) {
		return devMgrSqlDAO.selectService(api);
	}
    @Transactional
	public List<App> selectAPP(App appBean) {
		return devMgrSqlDAO.selectAPP(appBean);
	}
    @Transactional
	public Integer insertSerInvokeIns(SerInvokeIns serInvokeIns) {
		return devMgrSqlDAO.insertSerInvokeIns(serInvokeIns);
	}
    @Transactional
	public List<Component> selectCom(Component componentBean) {
		return devMgrSqlDAO.selectCom(componentBean);
	}
    @Transactional
	public List<Map> selectApiInfo(App appBean) throws EAAPException {
		return devMgrSqlDAO.selectApiInfo(appBean);
	}
    @Transactional
	public List<AppApiList> selectAppApiList(AppApiList appApiBean) {
		return devMgrSqlDAO.selectAppApiList(appApiBean);
	}
    @Transactional
	public List<AppType> selectAppType(AppType appTypeBean) {
		return devMgrSqlDAO.selectAppType(appTypeBean);
	}
    @Transactional
	public String saveCom(Component componentBean) {
		return devMgrSqlDAO.saveCom(componentBean);
	}
    @Transactional
	public Integer saveApp(App appBean) {
		return devMgrSqlDAO.saveApp(appBean);
	}
    @Transactional
	public Integer updateApp(App appBean) {
		return devMgrSqlDAO.updateApp(appBean);
	}
    @Transactional
	public Integer updateAppApiListByAppId(App appBean) {
		return devMgrSqlDAO.updateAppApiListByAppId(appBean);
	}
    @Transactional
	public void deleteAppApiListByAppId(App appBean) {
		devMgrSqlDAO.deleteAppApiListByAppId(appBean);
	}
    @Transactional
	public void deleteAppApiList(AppApiList appApiBean) {
		devMgrSqlDAO.deleteAppApiList(appApiBean);
	}
    @Transactional
	public Integer insertAppApiList(Map appApiBean) {
		return devMgrSqlDAO.insertAppApiList(appApiBean);
	}
    @Transactional
	public List<Map> queryAppApiNum(App appBean) {
		return devMgrSqlDAO.queryAppApiNum(appBean);
	}
    @Transactional
	public List<Map> queryAppStatistics(String queryType, Map map) {
		return devMgrSqlDAO.queryAppStatistics(queryType, map);
	}
    @Transactional
	public List<Map> queryServiceByAppId(App appBean) {
		return devMgrSqlDAO.queryServiceByAppId(appBean);
	}
	@Transactional
	public Integer updateAppApiListByIdAndState(App appBean) {
		return devMgrSqlDAO.updateAppApiListByIdAndState(appBean);
	}
	@Transactional
	public List<Map> queryApiStatistics(String queryType, Map map) {
		return devMgrSqlDAO.queryApiStatistics(queryType, map);
	}
	@Transactional
	public DevMgrDao getDevMgrSqlDAO() {
		return devMgrSqlDAO;
	}
	@Transactional
	public void setDevMgrSqlDAO(DevMgrDao devMgrSqlDAO) {
		this.devMgrSqlDAO = devMgrSqlDAO;
	}
	@Transactional
	public MainDataDao getMainDataSqlDAO() {
		return mainDataSqlDAO;
	}
	@Transactional
	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}
	@Transactional
	public MainDataTypeDao getMainDataTypeSqlDAO() {
		return mainDataTypeSqlDAO;
	}
	@Transactional
	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}
	@Transactional
	public OrgDao getOrgSqlDAO() {
		return orgSqlDAO;
	}
	@Transactional
	public void setOrgSqlDAO(OrgDao orgSqlDAO) {
		this.orgSqlDAO = orgSqlDAO;
	}
	@Transactional
	public Integer queryAppApiCount(Map map) {
		
		Integer num = 0;
		try {
			num = devMgrSqlDAO.queryAppApiCount(map);
		} catch (EAAPException e) {
			log.error(e.getStackTrace());
		}
		return num;
	}
	@Transactional
	public List<Map> queryAppApiList(Map map) {
		
		List<Map> appApiList = new ArrayList();
		try {

			appApiList = devMgrSqlDAO.queryAppApiList(map);

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return appApiList;
	}
	@Transactional
	public List<Map> selectApiInfoNew(Map map) throws EAAPException {
		List<Map> appApiList = new ArrayList();
		try {

			appApiList = devMgrSqlDAO.selectApiInfoNew(map);

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return appApiList;
	}
	@Transactional
	public Integer selectApiInfoNewCount(Map map) throws EAAPException {
		
		Integer num = 0;
		try {
			num = devMgrSqlDAO.selectApiInfoNewCount(map);
		} catch (EAAPException e) {
			log.error(e.getStackTrace());
		}
		return num;
	}
	@Transactional
	public List<Map> queryApiIds(Map map) throws EAAPException {
		List<Map> appApiList = new ArrayList();
		try {

			appApiList = devMgrSqlDAO.queryApiIds(map);

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return appApiList;
	}
	@Transactional
	public List<Map> getCategory(Map map) throws EAAPException {
		List<Map> getCategorys = new ArrayList();
		try {

			getCategorys = devMgrSqlDAO.getCategory(map);

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return getCategorys;
	}
	@Transactional
	public List<Map> queryOffersInfo(Map map) throws EAAPException {
		return devMgrSqlDAO.queryOffersInfo(map); 
	}
	@Transactional
	public List<Map> getOffersIds(Map map) throws EAAPException {
		 return devMgrSqlDAO.getOffersIds(map);
	}
	@Transactional
	public Integer insertOrgOfferRel(Map map) {
		return devMgrSqlDAO.insertOrgOfferRel(map);

	}
	@Transactional
	public void updateAPPwithApi(Map map) {
		devMgrSqlDAO.updateAPPwithApi(map);
	}
	@Transactional
	public void updateOrgwithOffer(Map map) {
		devMgrSqlDAO.updateOrgwithOffer(map);
	}
	@Transactional
	public List<Map<String, Object>> getPricingPlanCycle(Map map) {
		return devMgrSqlDAO.getPricingPlanCycle(map);
	}
	@Transactional
	public Integer insertPricingCase(Map map) {
		return devMgrSqlDAO.insertPricingCase(map);
	}
	@Transactional
	public List<Map<String, Object>> checkOfferhasBooks(Map map) {
		return devMgrSqlDAO.checkOfferhasBooks(map);
	}
	@Transactional
	public List<Map> selectServiceById(com.ailk.eaap.op2.bo.Service service) {
		return devMgrSqlDAO.selectServiceById(service);
	}

	/**
	 * 由服务调用实例得到webservice的调用地址
	 * 
	 * @param serInvokeInsId
	 * @return
	 */
	@Override
	@Transactional
	public List<Map<String, String>> getWebServiceMsg(String serInvokeInsId) {
		Map paramMap=new HashMap(); 
		paramMap.put("serInvokeInsId", serInvokeInsId);
		return devMgrSqlDAO.getWebServiceMsg(paramMap);
	}

	/**
	 * 由服务调用实例得到webservice api的调用地址
	 * 
	 * @param serInvokeInsId
	 * @return
	 */
	@Override
	@Transactional
	public List<Map<String, String>> getWebServiceApiMsg(String serInvokeInsId) {
		Map paramMap=new HashMap(); 
		paramMap.put("serInvokeInsId", serInvokeInsId);
		return devMgrSqlDAO.getWebServiceApiMsg(paramMap);
	}
	@Transactional
	public List<Map<String, String>> getSerInvokeInsId(Map map) {
		return devMgrSqlDAO.getSerInvokeInsId(map);
	}
	@Transactional
	public List<Map> queryChargeInfo(Map map) {
		return devMgrSqlDAO.queryChargeInfo(map);
	}
	@Transactional
	public List<Map<String, Object>> querydevAPICharges(Map map) {
		return (List<Map<String, Object>>) devMgrSqlDAO.querydevAPICharges(map);
		 
	}
	@Transactional
	public Integer querydevAPIChargesCou(Map map) {
		return (Integer) devMgrSqlDAO.querydevAPIChargesCou(map);
	}
	@Transactional
	public List<Map<String, Object>> getAPPList(Map map) {
		return (List<Map<String, Object>>) devMgrSqlDAO.getAPPList(map);
	}
	@Transactional
	public List<Map<String, Object>> getAPIList(Map map) {
		return (List<Map<String, Object>>) devMgrSqlDAO.getAPIList(map);
	}
	@Transactional
	public List<Map<String, Object>> detaildevAPICharges(Map map) {
		return (List<Map<String, Object>>) devMgrSqlDAO.detaildevAPICharges(map);
	}
	@Transactional
	public Integer detaildevAPIChargesCou(Map map) {
		return (Integer) devMgrSqlDAO.detaildevAPIChargesCou(map);
	}
	@Transactional
	public Integer queryOffersInfoCount(Map map){
		return (Integer) devMgrSqlDAO.queryOffersInfoCount(map);
	}
	
	public String delOfferofApp(Map map){
		Map temp = map;
		devMgrSqlDAO.updateOrgwithOffer(temp);
		//获取offer 下api 然后 状态置为D
		
		List apis = null;
		apis = devMgrSqlDAO.queryApiIds(temp);
		if(apis.size()>0){
			Map offerApi = null;
			for(int m=0;m<apis.size();m++){
				offerApi = (Map)apis.get(m);
				temp.put("apiId", offerApi.get("API_ID"));
				devMgrSqlDAO.updateAPPwithApi(temp);
				return "1";
			}
			
		}
		return "0";
	}
}

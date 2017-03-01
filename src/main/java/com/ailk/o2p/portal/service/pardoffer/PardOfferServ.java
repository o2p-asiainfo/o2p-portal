package com.ailk.o2p.portal.service.pardoffer;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ailk.eaap.op2.bo.Channel;
import com.ailk.eaap.op2.bo.Component;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.SettleSpBusiDef;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.web.bo.Org;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.ProdOfferChannel;
import com.ailk.eaap.op2.bo.ProdOfferRel;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.o2p.portal.dao.org.IOrgDao;
import com.ailk.o2p.portal.dao.pardoffer.IPardOfferDao;
import com.ailk.o2p.portal.dao.pardproduct.PardProdDao;
import com.ailk.o2p.portal.dao.tenant.ITenantDao;
import com.ailk.o2p.portal.service.message.MessageServ;
import com.ailk.o2p.portal.utils.PropertiesLoader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asiainfo.integration.o2p.web.util.O2pWebCommonUtil;
import com.asiainfo.integration.o2p.web.util.WebPropertyUtil;
import com.asiainfo.integration.o2p.web.util.WorkRestUtils;
import com.linkage.rainbow.util.StringUtil;
@Service
@Transactional
public class PardOfferServ implements IPardOfferServ {
	@Autowired
	private IPardOfferDao pardOfferDao ;
	@Autowired
	private PardProdDao pardProdDao;
	@Autowired
    private MainDataDao mainDataSqlDAO ;
	@Autowired
	private MainDataTypeDao mainDataTypeSqlDAO;
	@Autowired
	private MessageServ messageServ;
	@Autowired
	private IOrgDao orgSqlDAO;
	@Autowired
	private ITenantDao tenantDao;
	
	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}
	
	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}

	public IPardOfferDao getPardOfferDao() {
		return pardOfferDao;
	}

	public void setPardOfferDao(IPardOfferDao pardOfferDao) {
		this.pardOfferDao = pardOfferDao;
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

	public ProdOffer selectProdOffer(ProdOffer prodOffer){
		return pardOfferDao.selectProdOffer(prodOffer);
	}
	
	public List<Map> queryProductList(Map map){
		return pardOfferDao.queryOfferList(map);
	}
	public Integer countProductList(Map map){
		return pardOfferDao.countOfferList(map);
	}
	public List<Map> selectOfferProdRel(OfferProdRel offerProdRel){
		return pardOfferDao.selectOfferProdRel(offerProdRel);
	}
	
	@Override
	public OfferProdRel queryOfferProdRel(OfferProdRel offerProdRel) {
		// TODO Auto-generated method stub
		return pardOfferDao.queryOfferProdRel(offerProdRel);
	}
	
	@Override
	public Integer updateOfferProdRel(OfferProdRel offerProdRel) {
		// TODO Auto-generated method stub
		return pardOfferDao.updateOfferProdRel(offerProdRel);
	}

	
	public void delOfferProdRel(OfferProdRel offerProdRel){
		pardOfferDao.delOfferProdRel(offerProdRel);
	}
	public Integer updateProdOffer(ProdOffer prodOffer){
		return pardOfferDao.updateProdOffer(prodOffer);
	}
	public void deleteprodOfferRel(ProdOfferRel prodOfferRel){
		pardOfferDao.deleteprodOfferRel(prodOfferRel);
	}
	public Integer updateProdOfferRel(ProdOfferRel prodOfferRel){
		return pardOfferDao.updateProdOfferRel(prodOfferRel);
	}
	public Integer compOfferCode(ProdOffer prodOffer){
		return pardOfferDao.compOfferCode(prodOffer);
	}
	public List<ProdOfferRel> selectProdOfferList(ProdOfferRel prodOfferRel){
		return pardOfferDao.selectProdOfferList(prodOfferRel);
	}
	public ProdOfferRel queryProdOfferRel(ProdOfferRel prodOfferRel){
		return pardOfferDao.queryProdOfferRel(prodOfferRel);
	}
	public String insertProdOfferRel(ProdOfferRel prodOfferRel){
		return pardOfferDao.insertProdOfferRel(prodOfferRel)+"";
	}
	public void delProdOfferRel(ProdOfferRel prodOfferRel){
		pardOfferDao.delProdOfferRel(prodOfferRel);
	}
	public List<Map> selectProdOfferList(Map map){
		return pardOfferDao.selectProdOfferList(map) ;
	}
	public BigDecimal insertProdOffer(ProdOffer prodOffer){
		return pardOfferDao.insertProdOffer(prodOffer);
    }
	public Integer insertOfferProdRel(OfferProdRel offerProdRel){
		return pardOfferDao.insertOfferProdRel(offerProdRel) ;
    }
	public List<Channel> getChannelBasicTree(Channel channel){
		return pardOfferDao.getChannelBasicTree(channel);
	}
	public List<Channel> getProdOfferChannel(ProdOfferChannel poChannel){
		return pardOfferDao.getProdOfferChannel(poChannel);
	}
	public Integer insertProdOfferChannel(ProdOfferChannel poChannel){
		return pardOfferDao.insertProdOfferChannel(poChannel);
	}
	public void deleteProdOfferChannel(ProdOfferChannel poChannel){
		pardOfferDao.deleteProdOfferChannel(poChannel);
	}
	public String queryActivityId(String prodOfferId){
		return pardOfferDao.queryActivityId(prodOfferId);
	}
	
	private String getOPerateCode(String componentId){
		if(EAAPConstants.isCloud()){
			Component component = new Component();
			component.setComponentId(componentId);
			component.setIsMain("Y");
			component = orgSqlDAO.selectComponentOne(component);
			if(null == component){
				throw new BusinessException(ExceptionCommon.WebExceptionCode,
						"checkFixOfferOpearte getOPerateCode ==========> Operator is null!" ,null);
			}
			return String.valueOf(component.getOrgId());
		}
		
		return null;
	}
	
	public void updateProdOffer(ProdOffer prodOffer,String itemIds,String offerProdStr,String offerStr, String offerMutualExclusionStr)throws Exception{
		this.updateProdOffer(prodOffer);
		
		String operateCode = this.getOPerateCode(prodOffer.getOperateCode());
		
		//渠道
		ProdOfferChannel poChannel = new ProdOfferChannel();
		poChannel.setProdOfferId(prodOffer.getProdOfferId());
		this.deleteProdOfferChannel(poChannel);
		if(StringUtils.isNotEmpty(itemIds)){
			poChannel.setStatusCd("1000");
			poChannel.setEffDate(prodOffer.getEffDate());
			poChannel.setExpDate(prodOffer.getExpDate());
			for(String id : itemIds.split(",")){
				poChannel.setChannelId(Integer.parseInt(id));
				this.insertProdOfferChannel(poChannel);
			}
		}
		
		//产品添加
		if(!"".equals(offerProdStr)&&null!=offerProdStr){
			String[] OfferProdArr = offerProdStr.split(";");
			String[] offerProdAttArr ; 
			OfferProdRel offerProdRel=null;
			for(String str:OfferProdArr){
				offerProdRel=new OfferProdRel();
				offerProdRel.setRoleCd(Integer.parseInt(EAAPConstants.OFFER_PROD_REL_ROLE));
				offerProdRel.setCompoentType("10") ;
				offerProdAttArr =str.split(",");
				if(null!=offerProdAttArr&&offerProdAttArr.length==3){
					if(this.checkFixOfferOpearte(operateCode,offerProdAttArr[0],EAAPConstants.LOCAL_OBJECT_PRODUCT)){
						offerProdRel.setProdOfferId(prodOffer.getProdOfferId()) ;
						offerProdRel.setProductId(BigDecimal.valueOf(Long.valueOf(offerProdAttArr[0]))) ;
						offerProdRel.setMinCount(Integer.valueOf(offerProdAttArr[1])) ;
						offerProdRel.setMaxCount(Integer.valueOf(offerProdAttArr[2])) ;
						
						OfferProdRel offerProdRelquery=this.queryOfferProdRel(offerProdRel);
						if(offerProdRelquery==null){
							this.insertOfferProdRel(offerProdRel);
						}else{
							this.updateOfferProdRel(offerProdRel);
						}
					}
				}else{
					throw new BusinessException(ExceptionCommon.WebExceptionCode,
							"update ProdOffer ==========> Select product is error! " ,null);
				}
			}
		}
		//销售品添加------组合/9999
		if(!"".equals(offerStr)&&null!=offerStr){
			String[] Offer1Arr = offerStr.split(";");
			String[] offer1AttArr = null ; 
			ProdOfferRel prodOfferRel=null;
			for(String str:Offer1Arr){
				offer1AttArr = str.split(",");
				if(null!=offer1AttArr&&offer1AttArr.length==3){
					if(this.checkFixOfferOpearte(operateCode,offer1AttArr[0],EAAPConstants.LOCAL_OBJECT_OFFER)){
						prodOfferRel=new ProdOfferRel();
						prodOfferRel.setOfferAId(prodOffer.getProdOfferId());
						prodOfferRel.setRoleId(Integer.parseInt(EAAPConstants.OFFER_PROD_REL_ROLE));
						prodOfferRel.setOfferZId(BigDecimal.valueOf(Long.valueOf(offer1AttArr[0])));
						prodOfferRel.setMinCount(Integer.valueOf(offer1AttArr[1]));
						prodOfferRel.setMaxCount(Integer.valueOf(offer1AttArr[2]));
						prodOfferRel.setRelTypeCd("9999");
						ProdOfferRel prodOfferRelquery = this.queryProdOfferRel(prodOfferRel);
						if(prodOfferRelquery==null){
							this.insertProdOfferRel(prodOfferRel);
						}else{
							this.updateProdOfferRel(prodOfferRel);
						}
					}
				}else{
					throw new BusinessException(ExceptionCommon.WebExceptionCode,
							"update ProdOffer ==========> Include ProdOffer is error! " ,null);
				}
			}
		}
		//销售品添加------互斥/200000
		if(!"".equals(offerMutualExclusionStr)&&null!=offerMutualExclusionStr){
			String[] Offer2Arr = offerMutualExclusionStr.split(";");
			String[] offer2AttArr = null ; 
			ProdOfferRel prodOfferRel=null;
			for(String str:Offer2Arr){
				if(!StringUtils.isEmpty(str)){
					offer2AttArr = str.split(",");
					if(null!=offer2AttArr&&offer2AttArr.length==1){
						if(this.checkFixOfferOpearte(operateCode,offer2AttArr[0],EAAPConstants.LOCAL_OBJECT_OFFER)){
							prodOfferRel=new ProdOfferRel();
							prodOfferRel.setOfferAId(prodOffer.getProdOfferId());
							prodOfferRel.setRoleId(Integer.parseInt(EAAPConstants.OFFER_PROD_REL_ROLE));
							prodOfferRel.setOfferZId(BigDecimal.valueOf(Long.valueOf(offer2AttArr[0])));
							prodOfferRel.setRelTypeCd("200000");
							ProdOfferRel prodOfferRelquery = this.queryProdOfferRel(prodOfferRel);
							if(prodOfferRelquery==null){
								this.insertProdOfferRel(prodOfferRel);
							}else{
								this.updateProdOfferRel(prodOfferRel);
							}
						}
					}else{
						throw new BusinessException(ExceptionCommon.WebExceptionCode,
								"update ProdOffer ==========> Exclusion ProdOffer is error! " ,null);
					}
				}
			}
		}
	}
	
	public void deleteProdOffer(ProdOffer prodOffer,OfferProdRel offerProdRel)throws Exception{
		//删除offer-product
		List<OfferProdRel> offerProdRelList =  pardProdDao.queryOfferProdRelList(offerProdRel);
		if(null!=offerProdRelList&&offerProdRelList.size()>0){
			for(int i=0;i<offerProdRelList.size();i++){
				this.delOfferProdRel(offerProdRelList.get(i));
			}
		}
		//删除offer-offer
		ProdOfferRel prodOfferRel = new ProdOfferRel();
		prodOfferRel.setOfferAId(prodOffer.getProdOfferId());
		List<ProdOfferRel> prodOfferRelList1 = this.selectProdOfferList(prodOfferRel);
		if(null!=prodOfferRelList1&&prodOfferRelList1.size()>0){
			for(int i=0;i<prodOfferRelList1.size();i++){
				this.deleteprodOfferRel(prodOfferRelList1.get(i));
			}
		}
		//更新产品状态(删除状态)
		prodOffer.setStatusCd("1300");
		this.updateProdOffer(prodOffer);
	}
	
	public void doPardOffertSubmitCheck(ProdOffer prodOffer,String orgId,Object switchUserRole)throws Exception{
		// 审批流程
		RestTemplate rest = new RestTemplate();
		JSONObject retJson = null;
		JSONObject jsonObject = new JSONObject();
		
		String o2pWebDomain = WebPropertyUtil.getPropertyValue("o2p_web_domin");
		if(null==o2pWebDomain || "".equals(o2pWebDomain.trim())){
			o2pWebDomain = EAAPConstants.O2P_WEB_DOMAIN_LOCAL;
		}
		
		if(!"".equals(StringUtil.valueOf(prodOffer.getProdOfferId()))){
			if("1200".equals(prodOffer.getStatusCd())){
				jsonObject.put("contentId", String.valueOf(prodOffer.getProdOfferId()));
				UserRoleInfo userRoleInfo = OperateActContext.getUserInfo();
				String userId = EAAPConstants.PROCESS_AUTHENTICATED_USER_ID;
				Integer tenantId = O2pWebCommonUtil.getDefalutTenantId();
				if(userRoleInfo != null){
					userId = tenantDao.qryAdminIdByTenantId(userRoleInfo.getTenantId());
					tenantId = userRoleInfo.getTenantId();
				}
				retJson = WorkRestUtils.startWorkflowAndVal(EAAPConstants.PROCESS_MODEL_ID_PRODOFFER, "process Offer Audit Name:"+prodOffer.getProdOfferName(),
						userId,tenantId, isNeedO2pAudit(jsonObject,switchUserRole,prodOffer,orgId,o2pWebDomain).toJSONString());
				
				String returnCode = retJson.getString("code");
				if ("0000".equals(returnCode)) {
					// 申请审批成功
					prodOffer.setStatusCd("1299");
					prodOffer.setAuditFlowId(retJson.getString("desc"));
					this.updateProdOffer(prodOffer);
				}
			}else if("1298".equals(prodOffer.getStatusCd())||"1288".equals(prodOffer.getStatusCd())||"1278".equals(prodOffer.getStatusCd())){
				Map<String, Object> taskVariables = new HashMap<String, Object>();
				JSONArray jsons = null;
				//获取任务节点
				ProdOffer offer = new ProdOffer();
				offer.setProdOfferId(prodOffer.getProdOfferId());
				prodOffer = this.selectProdOffer(offer);
				String message = WorkRestUtils.taskListByProcessId(prodOffer.getAuditFlowId());
						
				if(!StringUtils.isEmpty(message)){
					jsons = JSON.parseArray(message);
					if(null!=jsons&&jsons.size()==1){
						retJson = jsons.getJSONObject(0);
						String taskId = retJson.getString("taskId");
						if (null!=taskId&&!"".equals(taskId)) {
							taskVariables.put("taskId", taskId);
							
							taskVariables.put("varJson", isNeedO2pAudit(jsonObject,switchUserRole,prodOffer,orgId,o2pWebDomain).toJSONString());
							String ret = rest.postForObject(WebPropertyUtil.getPropertyValue("work_flow_pro_url")+"/task/completeTask?taskId={taskId}&varJson={varJson}",
						    		null, String.class,taskVariables);
							if(!StringUtils.isEmpty(ret)){
								retJson = JSON.parseObject(ret);
								String returnCode = retJson.getString("code");
								if ("0000".equals(returnCode)) {
									//置待办消息的状态
									BigDecimal msgId = messageServ.getMsgIdByQueryTitle(
											EAAPConstants.WORK_FLOW_MESSAGE_QUERY.replace("#id", String.valueOf(prodOffer.getProdOfferId())));
									messageServ.lookMsgById(orgId,String.valueOf(msgId));
									messageServ.updateMsgForWorkFlow(msgId);
									
									prodOffer.setStatusCd("1299");
									this.updateProdOffer(prodOffer);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
     * 判断是否需要O2P审核 
     * 判断是否服务已经同步
     * @param jsonObject
     * @param switchUserRole 超级用户权限，不用O2P审核
     * @return
     */
    private JSONObject isNeedO2pAudit(JSONObject jsonObject,Object switchUserRole ,ProdOffer offer,String orgId,String o2pWebDomain){
    	if(EAAPConstants.O2P_WEB_DOMAIN_LOCAL.equals(o2pWebDomain)){
    		jsonObject.put("isLocal", Boolean.TRUE);
    	}else{
    		jsonObject.put("isLocal", Boolean.FALSE);
    	}
    	if(switchUserRole != null && (Boolean) switchUserRole){
    		jsonObject.put("O2P_examime", Boolean.FALSE);
			jsonObject.put("offer_audit_result", "3");
			
			offer.setProdOfferPublisher(orgId);
    	}else{
    		String o2pAudit = WebPropertyUtil.getPropertyValue("process_offer_audit_o2p_audit");
			if("1".equals(o2pAudit)){
				jsonObject.put("O2P_examime", Boolean.TRUE);
			}else{
				jsonObject.put("O2P_examime", Boolean.FALSE);
				jsonObject.put("offer_audit_result", "3");
			}
    	}
		return jsonObject;
    }
	
	
	public void lookMsgById(Integer userId,String titleQuery){
		messageServ.lookMsgById(String.valueOf(userId), String.valueOf(messageServ.getMsgIdByQueryTitle(titleQuery)));
	}
	
//	protected String getRequestValue(final HttpServletRequest request,
//			String paramName, boolean escape) {
//		String paramValue = request.getParameter(paramName);
//		if (null != paramValue) {
//			paramValue = StringUtils.trim(paramValue);
//			if (escape) {
//				paramValue = StringEscapeUtils.escapeHtml(paramValue);
//				paramValue = StringEscapeUtils.escapeSql(paramValue);
//			}
//			return paramValue;
//		} else {
//			return StringUtils.EMPTY;
//		}
//	}
//	protected String getRequestValue(final HttpServletRequest request,
//			String paramName) {
//		return getRequestValue(request, paramName, true);
//	}
	private Boolean checkFixOfferOpearte(String operateCode,String objId,String objType){
		if(EAAPConstants.isCloud()){
			if(EAAPConstants.LOCAL_OBJECT_PRODUCT.equals(objType)){
				Product product = new Product();
				product.setProductId(new BigDecimal(objId));
				product = this.pardProdDao.selectProduct(product);
				
				if(null == product){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,
							"checkFixOfferOpearte ==========> Product " + objId + " is null!" ,null);
				}
				
				String prodProvider = String.valueOf(product.getProductProviderId());
				if(null == product.getProductProviderId() || "".equals(prodProvider)){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,
							"checkFixOfferOpearte ==========> Product " + product.getProductName() + " 's provider is null!" ,null);
				}
				
				if("10".equals(product.getCooperationType()) && !prodProvider.equals(operateCode)){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,
							"checkFixOfferOpearte ==========> Product " + product.getProductName() + " is not provide by Operate " + operateCode +"!" ,null);
				}
				
				prodProvider = null;
			}else if(EAAPConstants.LOCAL_OBJECT_OFFER.equals(objType)){
				ProdOffer prodOffer = new ProdOffer();
				prodOffer.setProdOfferId(new BigDecimal(objId));
				prodOffer = pardOfferDao.selectProdOffer(prodOffer);
				
				if(null == prodOffer){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,
							"checkFixOfferOpearte ==========> ProdOffer " + objId + " is null!" ,null);
				}
				
				if(null == prodOffer.getOfferProviderId() || "".equals(prodOffer.getOfferProviderId())){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,
							"checkFixOfferOpearte ==========> ProdOffer " + prodOffer.getProdOfferName() + " 's provider is null!" ,null);
				}
				
				if("10".equals(prodOffer.getCooperationType()) && !prodOffer.getOfferProviderId().equals(operateCode)){
					throw new BusinessException(ExceptionCommon.WebExceptionCode,
							"checkFixOfferOpearte ==========> ProdOffer " + prodOffer.getProdOfferName() + " is not provide by Operate " + operateCode +"!" ,null);
				}
				
			}else{
				throw new BusinessException(ExceptionCommon.WebExceptionCode,
						"checkFixOfferOpearte ==========> Object Type is error! " ,null);
			}
		}
		return Boolean.TRUE;
	}
	
	public BigDecimal addProdOffer(final HttpServletRequest request,
			final HttpServletResponse response,ProdOffer prodOffer,String offerProdStr,String offerStr,String offerMutualExclusionStr,String offerChannelStr,PropertiesLoader i18nLoader,Org orgBean)throws Exception{
		
		BigDecimal offId = this.insertProdOffer(prodOffer);
		String operateCode = this.getOPerateCode(prodOffer.getOperateCode());
		
		prodOffer.setProdOfferId(offId);
		String cooperationType = StringUtil.valueOf(prodOffer.getCooperationType());
		if(!"".equals(cooperationType)){
			//产品添加
			if(!"".equals(offerProdStr)&&null!=offerProdStr){
				String[] OfferProdArr = offerProdStr.split(";");
				String[] offerProdAttArr ; 
				OfferProdRel offerProdRel=null;
				for(String str:OfferProdArr){
					offerProdRel = new OfferProdRel();
					offerProdRel.setRoleCd(Integer.parseInt(EAAPConstants.OFFER_PROD_REL_ROLE));
					offerProdRel.setCompoentType("10") ;
					offerProdAttArr = str.split(",");
					if(null != offerProdAttArr && offerProdAttArr.length == 3){
						if(this.checkFixOfferOpearte(operateCode,offerProdAttArr[0],EAAPConstants.LOCAL_OBJECT_PRODUCT)){
//							product.setParentProductId(Integer.valueOf(offerProdAttArr[0]));
							offerProdRel.setProdOfferId(offId) ;
							offerProdRel.setProductId(BigDecimal.valueOf(Long.valueOf(offerProdAttArr[0]))) ;
							offerProdRel.setMinCount(Integer.valueOf(offerProdAttArr[1])) ;
							offerProdRel.setMaxCount(Integer.valueOf(offerProdAttArr[2])) ;
							this.insertOfferProdRel(offerProdRel);
						}
					}else{
						throw new BusinessException(ExceptionCommon.WebExceptionCode,
								"add ProdOffer ==========> include product is error! " ,null);
					}
				}
			}
			//销售品添加------组合/9999
			if(!"".equals(offerStr)&&null!=offerStr){
				String[] Offer1Arr = offerStr.split(";");
				String[] offer1AttArr = null ; 
				ProdOfferRel prodOfferRel=null;
				for(String str:Offer1Arr){
					offer1AttArr = str.split(",");
					if(null!=offer1AttArr&&offer1AttArr.length==3){
						if(this.checkFixOfferOpearte(operateCode,offer1AttArr[0],EAAPConstants.LOCAL_OBJECT_OFFER)){
							prodOfferRel=new ProdOfferRel();
							prodOfferRel.setOfferAId(offId);
							prodOfferRel.setRoleId(Integer.parseInt(EAAPConstants.OFFER_PROD_REL_ROLE));
							prodOfferRel.setOfferZId(BigDecimal.valueOf(Long.valueOf(offer1AttArr[0])));
							prodOfferRel.setMinCount(Integer.valueOf(offer1AttArr[1]));
							prodOfferRel.setMaxCount(Integer.valueOf(offer1AttArr[2]));
							prodOfferRel.setRelTypeCd("9999");
							this.insertProdOfferRel(prodOfferRel);
						}
					}else{
						throw new BusinessException(ExceptionCommon.WebExceptionCode,
								"add ProdOffer ==========> include ProdOffer is error! " ,null);
					}
				}
			}
			//销售品添加------互斥/200000
			if(!"".equals(offerMutualExclusionStr)&&null!=offerMutualExclusionStr){
				String[] Offer2Arr = offerMutualExclusionStr.split(";");
				String[] offer2AttArr = null ; 
				ProdOfferRel prodOfferRel=null;
				for(String str:Offer2Arr){
					offer2AttArr = str.split(",");
					if(null!=offer2AttArr&&offer2AttArr.length==1){
						if(this.checkFixOfferOpearte(operateCode,offer2AttArr[0],EAAPConstants.LOCAL_OBJECT_OFFER)){
							prodOfferRel=new ProdOfferRel();
							prodOfferRel.setOfferAId(offId);
							prodOfferRel.setRoleId(Integer.parseInt(EAAPConstants.OFFER_PROD_REL_ROLE));
							prodOfferRel.setOfferZId(BigDecimal.valueOf(Long.valueOf(offer2AttArr[0])));
							prodOfferRel.setRelTypeCd("200000");
							this.insertProdOfferRel(prodOfferRel);
						}
					}else{
						throw new BusinessException(ExceptionCommon.WebExceptionCode,
								"add ProdOffer ==========> Exclusion ProdOffer is error! " ,null);
					}
				}
			}
			//渠道
			if(StringUtils.isNotEmpty(offerChannelStr)){
				ProdOfferChannel poChannel=new ProdOfferChannel();
				poChannel.setProdOfferId(offId);
				poChannel.setStatusCd("1000");
				poChannel.setEffDate(prodOffer.getEffDate());
				poChannel.setExpDate(prodOffer.getExpDate());
				for(String id : offerChannelStr.split(",")){
					poChannel.setChannelId(Integer.parseInt(id));
					this.insertProdOfferChannel(poChannel);
				}
			}
		}
		return offId;
		//form1_type1,form1_type2;form2_t1
		//1_1,1_2;2_1
//		String formStr = getRequestValue(request, "formStr"); 
//		String[] formAry=formStr.split(";");
//		for(int i=0;i<formAry.length;i++){
//			saveOrUpdatePricePlan(request,response,formAry[i],i18nLoader);//price plan
//			saveOrUpdateSettle(request,response,formAry[i],orgBean);//settle
//		}
	}

	@Override
	public Org queryOrg(Org paramOrg) {
		// TODO Auto-generated method stub
		return orgSqlDAO.queryOrg(paramOrg);
	}

	@Override
	public Integer checkOfferCode(ProdOffer prodOffer){
		// TODO Auto-generated method stub
		return pardOfferDao.checkOfferCode(prodOffer);
	}
	
	public List<Map<String,Object>> queryOrgCountry(Map map) {
		return orgSqlDAO.queryOrgCountry(map);
	}
	
	
	public List<Map<String, Object>> queryOperatorUnderCountry(Org orgBean) {
		return orgSqlDAO.queryOperatorUnderCountry(orgBean);
	}

	@Override
	public Integer querySettleRuleInfo(SettleSpBusiDef settleSpBusiDef) {
		// TODO Auto-generated method stub
		return pardOfferDao.querySettleRuleInfo(settleSpBusiDef);
	}

	@Override
	public Integer queryPrcingPlanInfo(OfferProdRel offerProdRel) {
		// TODO Auto-generated method stub
		return pardOfferDao.queryPrcingPlanInfo(offerProdRel);
	}

	public Org getOrgInfoByComponentId(String componentId){
		Org orgBean = new Org();
		Component component = new Component();
		component.setComponentId(componentId);
		component = this.orgSqlDAO.selectComponentOne(component);
		if(null != component){
			orgBean.setOrgId(component.getOrgId());
			orgBean = this.orgSqlDAO.queryOrg(orgBean);
		}
		
		return orgBean;
	}

}

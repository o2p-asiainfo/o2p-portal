package com.ailk.o2p.portal.service.provider;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ailk.eaap.op2.bo.Api;
import com.ailk.eaap.op2.bo.BillingDiscount;
import com.ailk.eaap.op2.bo.ComponentPrice;
import com.ailk.eaap.op2.bo.Contract;
import com.ailk.eaap.op2.bo.ContractFormat;
import com.ailk.eaap.op2.bo.ContractVersion;
import com.ailk.eaap.op2.bo.DirSerList;
import com.ailk.eaap.op2.bo.FileShare;
import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.NodeDesc;
import com.ailk.eaap.op2.bo.OfferProdRel;
import com.ailk.eaap.op2.bo.ProdOffer;
import com.ailk.eaap.op2.bo.Product;
import com.ailk.eaap.op2.bo.RatingCurverDetail;
import com.ailk.eaap.op2.bo.ServiceAtt;
import com.ailk.eaap.op2.bo.ServiceProductRela;
import com.ailk.eaap.op2.bo.TechImpAtt;
import com.ailk.eaap.op2.bo.TechImpl;
import com.ailk.eaap.op2.bo.WorkTaskConf;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.common.EAAPException;
import com.ailk.o2p.portal.bo.Component;
import com.ailk.o2p.portal.bo.ServiceOwner;
import com.ailk.o2p.portal.bo.UserDefined;
import com.ailk.o2p.portal.dao.provider.IProviderDao;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.StringUtil;

@Service
public class ProviderServiceImpl implements IProviderService {

	private Logger log = Logger.getLog(this.getClass());
	private static List<String> list = new ArrayList<String>();
	@Autowired
	private IProviderDao providerDao;
	@Override
	public List<Map<String, Object>> findApply(Map<String, String> map) {
		List<Map<String, Object>> findApplyList;
		findApplyList = providerDao.findApply(map);
		return findApplyList;
	}
	@Override
	public List<Map<String, Object>> selectFileShare(String requestValue) {
		List<Map<String, Object>> fileShare = new ArrayList();
		try {
			Map paramMap=new HashMap();
			paramMap.put("requestValue", requestValue);
			fileShare = providerDao.selectFileShare(paramMap);
		} catch (Exception e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return fileShare;
	}
	@Override
	public Map<String, String> queryProvSystem(String componentId) {
		Map<String, String> map;
		Map paramMap=new HashMap();
		paramMap.put("componentId", componentId);
		map = providerDao.queryProvSystem(paramMap);
		return map;
	}
	@Override
	public Integer saveFileShare(FileShare file) {
		return providerDao.saveFileShare(file);
	}
	@Override
	public boolean isExitCode(String code) {
		Map paramMap=new HashMap();
		paramMap.put("code", code);
		return providerDao.isExitCode(paramMap);
	}
	@Override
	public String saveOrUpdate(String pageActionType, Component component) {
		if("add".equals(pageActionType)){
			component.setComponentTypeId(Integer.valueOf(EAAPConstants.COMM_STATE_TYPE_ID));
			component.setState(EAAPConstants.COMM_STATE_NEW);
			component.setPassword(EAAPConstants.COMM_STATE_PASSWEORD);
			return providerDao.saveComponent(component);
		}else{
			return providerDao.updateComponent(component);
		}
	}
	@Override
	public List<Map<String, String>> provAbility(Map map) {
		List<Map<String, String>> provAbility = null;
		try {
			provAbility = providerDao.provAbility(map);
		} catch (EAAPException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}	
	return provAbility;
	}
	@Override
	public List<Map<String, Object>> userDefinedList(String componentId) {
		List<Map<String, Object>> userDefined = null;
		try {
			Map paramMap=new HashMap();
			paramMap.put("componentId", componentId);
			userDefined = providerDao.userDefinedList(paramMap);
		} catch (EAAPException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}	
	return userDefined;
	}
	@Override
	public void addTechImpAtt(Map<String, String> map) {
		Map paramMap=new HashMap();
		providerDao.addTechImpAtt(map,paramMap);
	}
	@Override
	public List<Map<String, Object>> selectCommProtocal() {
		return providerDao.selectCommProtocal();
	}
	@Override
	public List<Map<String, Object>> selectDirectory(String fadiridapi) {
		Map paramMap=new HashMap();
		paramMap.put("faDirId",fadiridapi);
		return providerDao.selectDirectory(paramMap);
	}
	@Override
	public List<Map<String, Object>> selectConType(String maindatacontype) {
		Map paramMap=new HashMap();
		paramMap.put("mainDataConType",maindatacontype);
		return providerDao.selectConType(paramMap);
	}
	@Override
	public boolean checkCode(String pageProCode) {
		Map paramMap=new HashMap();
		paramMap.put("code",pageProCode);
		return providerDao.checkCode(paramMap);
	}
	@Override
	public boolean checkVersion(String pageProVersionCode) {
		Map paramMap=new HashMap();
		paramMap.put("pageProVersionCode",pageProVersionCode);
		return providerDao.checkVersion(paramMap);
	}
	@Override
	public Map<String, String> addUserDefined(UserDefined userDefined) {
		Map<String,String> mapParam = new HashMap<String,String>();
		Contract contract = new Contract();
		Integer techImpAttId =  null;
		contract.setName(userDefined.getServiceName());
		contract.setState(EAAPConstants.COMM_STATE_VALID);
		contract.setCode(userDefined.getContractCode());
		contract.setDescriptor(userDefined.getDescriptor());
		ContractVersion newContractVersion =  null;
		
		ServiceOwner newService = null;
		ServiceAtt serviceAtt = new ServiceAtt();
		try {
			Contract newContract = providerDao.addContract(contract);
			if (newContract != null){
				ContractVersion contractVersion = new ContractVersion();
				contractVersion.setContractId(newContract.getContractId().toString());
				contractVersion.setVersion(userDefined.getContractVersionCode());
				contractVersion.setDescriptor(userDefined.getDescriptor());
				contractVersion.setState(EAAPConstants.COMM_STATE_VALID);
				mapParam.put("contractId", newContract.getContractId().toString());//返加参数
				newContractVersion = providerDao.addContractVersion(contractVersion);
				if(newContractVersion != null){
					
					ServiceOwner service = new ServiceOwner();
					service.setContractVersionId(String.valueOf(newContractVersion.getContractVersionId()));
					mapParam.put("contractVersionId", newContractVersion.getContractVersionId().toString());//返加参数
					service.setServiceCnName(userDefined.getServiceName());
					service.setServiceEnName(newContractVersion.getVersion());
					service.setServiceCode(newContractVersion.getVersion());
					service.setServiceType("0");
					service.setServiceVersion(newContractVersion.getVersion());
					service.setState(EAAPConstants.COMM_STATE_VALID);
					service.setIsPublished("Y");
					service.setServiceDesc(userDefined.getDescriptor());
					
					newService = providerDao.addService(service); 
					if(newService != null){	//api配置 api 方法名是写协议版本号。
						Api api = new Api();

						api.setApiMethod(service.getServiceVersion());
						api.setApiName(service.getServiceCnName());
						api.setApiState("D");
						Integer newId = (Integer)newService.getServiceId();
						api.setServiceId(newId);
						 
						providerDao.addApi(api);
						String dirSerlistIdStr = "";
						//共享
						if("1".equals(userDefined.getIsPublic())){
							List<String> dirSerlistList = new ArrayList<String>();
							String publicDirectoryId = userDefined.getPublicDirectoryId();
							if(!StringUtil.isEmpty(publicDirectoryId)){
								String[] publicDirectoryArray = publicDirectoryId.split(",");
								for(String publicDirectory : publicDirectoryArray){
									DirSerList dirSerList = new DirSerList();
									dirSerList.setServiceId(newService.getServiceId());
									dirSerList.setDirId(Integer.valueOf(publicDirectory));
									dirSerList.setState(EAAPConstants.COMM_STATE_VALID);
									Integer dirSerlistId = providerDao.addDirServiceList(dirSerList);
									dirSerlistList.add(String.valueOf(dirSerlistId));
								}
								dirSerlistIdStr = StringUtils.collectionToDelimitedString(dirSerlistList, ",");
							}
						}else{
							DirSerList dirSerList = new DirSerList();
							dirSerList.setServiceId(newService.getServiceId());
							dirSerList.setDirId(Integer.valueOf(userDefined.getDirectoryId()));
							dirSerList.setState(EAAPConstants.COMM_STATE_VALID);
							Integer dirSerlistId = providerDao.addDirServiceList(dirSerList);
							dirSerlistIdStr = String.valueOf(dirSerlistId);
						}
						mapParam.put("dirSerlistId", dirSerlistIdStr);//返加参数
						mapParam.put("serviceId", newService.getServiceId()+"");//返加参数
						serviceAtt.setServiceId(newService.getServiceId());
						serviceAtt.setState(EAAPConstants.COMM_STATE_VALID);
						serviceAtt.setSerSpecVa("Y");
						serviceAtt.setAttrSpecId(Integer.valueOf(EAAPConstants.ATTR_SPEC_ID));						
						providerDao.addServiceAtt(serviceAtt);
						Map map = new HashMap();
						map.put("service", newService.getServiceId());
						map.put("attrspecvalue", userDefined.getAttrSpecValue());
						map.put("componentId", userDefined.getComponentId());
						map.put("commProCd", userDefined.getCommProcd());
						techImpAttId = providerDao.addUserDefineTechImpAtt(map,mapParam,userDefined);
						mapParam.put("techImpAttId", techImpAttId+"");//返加参数
					}				
				}				
			}

		} catch (EAAPException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return mapParam;
	}
	@Override
	public void updateUserDefined(UserDefined userDefined,JSONObject json) {
		json.put("dirSerlistId", userDefined.getDirSerlistId()); 
		Contract contract = new Contract();
		contract.setContractId(Integer.valueOf(userDefined.getContractId()));
		contract.setName(userDefined.getServiceName());
		contract.setDescriptor(userDefined.getDescriptor());
		providerDao.editContract(contract);
		//先删除原有数据
		Map paramMap=new HashMap(); 
		paramMap.put("service_id", userDefined.getServiceId());
		providerDao.deleteDirSerList(paramMap);
		//共享
		if("1".equals(userDefined.getIsPublic())){
			//再添加数据
			List<String> dirSerlistList = new ArrayList<String>();
			String publicDirectoryId = userDefined.getPublicDirectoryId();
			if(!StringUtil.isEmpty(publicDirectoryId)){
				String[] publicDirectoryArray = publicDirectoryId.split(",");
				for(String publicDirectory : publicDirectoryArray){
					DirSerList dirSerList = new DirSerList();
					dirSerList.setServiceId(Integer.valueOf(userDefined.getServiceId())); 
					dirSerList.setDirId(Integer.valueOf(publicDirectory));
					dirSerList.setState(EAAPConstants.COMM_STATE_VALID);
					Integer dirSerlistId = providerDao.addDirServiceList(dirSerList);
					dirSerlistList.add(String.valueOf(dirSerlistId));
				}
				String dirSerlistIdStr = StringUtils.collectionToDelimitedString(dirSerlistList, ",");
				json.put("dirSerlistId", dirSerlistIdStr); 
			}
		}else{
			DirSerList dirSerList = new DirSerList();
			dirSerList.setServiceId(Integer.valueOf(userDefined.getServiceId())); 
			dirSerList.setDirId(Integer.valueOf(userDefined.getDirectoryId()));
			dirSerList.setState(EAAPConstants.COMM_STATE_VALID);
			Integer dirSerlistId = providerDao.addDirServiceList(dirSerList);
			json.put("dirSerlistId", String.valueOf(dirSerlistId));  
		}
		
		ServiceOwner service = new ServiceOwner();
		service.setServiceId(Integer.valueOf(userDefined.getServiceId()));
		service.setServiceCnName(userDefined.getServiceName());
		service.setServiceDesc(userDefined.getDescriptor());
		providerDao.editService(service);
		
		TechImpl techImpl = new TechImpl();
		techImpl.setTechImplId(Integer.valueOf(userDefined.getTechImpId()));
		techImpl.setCommProCd(userDefined.getCommProcd());
		providerDao.editTechImpl(techImpl);
		//改变服务技术实现的状态
		///providerDao.updateSerTechImpl(userDefined.getTechImpId());
		
		try {
			String[] dyAttrSpecId = userDefined.getDyAttrSpecIds();
			String[] dyAttrSpecValue = userDefined.getDyAttrSpecValues();
			if(null != dyAttrSpecId && dyAttrSpecId.length>0){
				for(int i=0;i<dyAttrSpecId.length;i++){
					TechImpAtt tech = new TechImpAtt();
					tech.setTechImplId(Integer.valueOf(userDefined.getTechImpId()));
					tech.setAttrSpecId(Integer.parseInt(dyAttrSpecId[i]));
					tech.setAttrSpecValue(dyAttrSpecValue[i]);
					tech.setState(EAAPConstants.COMM_STATE_VALID);
					boolean flag = providerDao.getIsExit(tech);
					if(flag){
						providerDao.editTechImplAtt(tech);
					}else{
						providerDao.addTechImplAtt(tech);
					}
				}
			}
		} catch (EAAPException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
	}
	@Override
	public Integer addContractFormat(UserDefined userDefined) {
		String operator="Add";
		Integer cFormatId = null;
		NodeDesc nodeDesc = new NodeDesc();
		ContractFormat cf = new ContractFormat();
		cf.setContractVersionId(Integer.valueOf(userDefined.getContractVersionId()));
		cf.setConType(userDefined.getConType());
		cf.setReqRsp(userDefined.getReqRsp());

		cf.setXsdDemo(userDefined.getXsdDemo());
		cf.setDescriptor(userDefined.getDescriptor());
		cf.setState(EAAPConstants.COMM_STATE_VALID);
		cf.setXsdHeaderFor(userDefined.getXsdHeadFormat());
			
		String blobString = null;
		java.sql.Blob blob = null;
			if(userDefined.getFileShareId()!= null && !userDefined.getFileShareId().equals("")){				
				try {		 
					Map paramMap=new HashMap();
					paramMap.put("requestValue", userDefined.getFileShareId());
					List<Map<String, Object>> fileShareList = providerDao.selectFileShare(paramMap);
					if(fileShareList != null){
						for(Map<String, Object> item : fileShareList){
							if(item.get("SFILECONTENT").getClass().getName().equals("oracle.sql.BLOB")){
								 blob = (Blob) item.get("SFILECONTENT");
								 blobString = new String(blob.getBytes(1, (int) blob.length()),"UTF-8");//String ת blob 
							}
							if(item.get("SFILECONTENT").getClass().getName().equals("[B")){
								blobString =(String)item.get("SFILECONTENT");
							}									
							}
						}	
					} catch (Exception e) {
						log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
					}
			}
			else{
				blobString = userDefined.getXsdFormat();
			}	
			
			try {
				cf.setXsdFormat(blobString);
				cFormatId = providerDao.addContractFormat(cf);
			} catch (EAAPException e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
		
		nodeDesc.setTcpCtrFId(cFormatId);
		
		nodeDesc.setState(EAAPConstants.COMM_STATE_FAIL);
		providerDao.editNodeDesc(nodeDesc);
		
		nodeDesc.setState(EAAPConstants.COMM_STATE_VALID);
		nodeDesc.setNodeType(EAAPConstants.NODE_TYPE_BODY);
		nodeDesc.setIsNeedCheck(EAAPConstants.IS_NEED_CHECK_NO);
		nodeDesc.setIsNeedSign(EAAPConstants.IS_NEED_SIGN_NO);
		nodeDesc.setNodeTypeCons("1");
		nodeDesc.setNodeNumberCons("1");
		if(userDefined.getConType().equals("1") || userDefined.getConType().equals("6")){			
			if(userDefined.getXsdHeadFormat()!=null){
				getXsdFormat(userDefined.getConType(),new StringBuilder("<").append(o2pRoot).append(">").append(userDefined.getXsdHeadFormat()).append("</").append(o2pRoot).append(">").toString(),blobString,nodeDesc,operator);
			}
		}else if(userDefined.getConType().equals("3")||userDefined.getConType().equals("10")) {
			String xmlHead = "";
			if(!"".equals(userDefined.getXsdHeadFormat())){//hEad还是XML格式的
				xmlHead=new StringBuilder("<").append(o2pRoot).append(">").append(userDefined.getXsdHeadFormat()).append("</").append(o2pRoot).append(">").toString();
			}
			getXsdFormat(userDefined.getConType(),xmlHead,"",nodeDesc,operator);
			getUrlFormat(userDefined.getXsdFormat(),nodeDesc,operator);
		}else if(userDefined.getConType().equals("2")) {//JSON
			String xml = "";
			XMLSerializer xmlSerializer = new XMLSerializer();
			if(!"".equals(blobString)){
				JSONObject json = JSONObject.fromObject(blobString);
				xml = xmlSerializer.write(json);
			}
			String xmlHead = "";
			if(!"".equals(userDefined.getXsdHeadFormat())){//hEad还是XML格式的
//				JSONObject jsonHead = JSONObject.fromObject(userDefined.getXsdHeadFormat());
//				xmlHead = xmlSerializer.write(jsonHead);
				xmlHead=new StringBuilder("<").append(o2pRoot).append(">").append(userDefined.getXsdHeadFormat()).append("</").append(o2pRoot).append(">").toString();
			}
			getXsdFormat(userDefined.getConType(),xmlHead,xml,nodeDesc,operator);
		}
		return cFormatId;
	}
	private void iteratorXml(String conType,String type,Element element,NodeDesc nodeDesc,Integer partentId,String cdataPrePath){
		for(Iterator<Element> sonElement = element.elementIterator();sonElement.hasNext();){
			 Element son = sonElement.next();
			 if("2".equals(conType)||"10".equals(conType)){//JSON
				 if("e".equals(son.getQualifiedName())){
					 iteratorXml(conType,type,son,nodeDesc,partentId,cdataPrePath);
					 continue;
				 }
			 }
			 Integer parId = 0;
			 if(!list.contains(cdataPrePath+son.getPath())){
				 list.add(cdataPrePath+son.getPath());
				 nodeDesc.setNodeName(son.getQualifiedName());
				 nodeDesc.setNodeCode(son.getQualifiedName());
				 String sonPath=null;
				 if("head".equals(type)){
					 nodeDesc.setNodePath(cdataPrePath+son.getPath().replace("/"+o2pRoot+"/", "/")); 
					 if(son.selectSingleNode("@field")!=null){
						 String field=son.selectSingleNode("@field").getText();
						 if("1".equals(field)){
							 nodeDesc.setJavaField("HTTPSTATUSCODE");
							 nodeDesc.setNodeCode("HTTPSTATUSCODE");
						 }else if("2".equals(field)){
							 nodeDesc.setJavaField("ReqTime");
							 nodeDesc.setNodeCode("ReqTime");
						 }else if("3".equals(field)){
							 nodeDesc.setJavaField("RspTime");
							 nodeDesc.setNodeCode("RspTime");
						 }else if("4".equals(field)){
							 nodeDesc.setJavaField("AppKey");
							 nodeDesc.setNodeCode("AppKey");
						 }else{
							 nodeDesc.setJavaField(field);
							 nodeDesc.setNodeCode(field);
						 }
					 }else{
						 nodeDesc.setJavaField("");
					 }
					 sonPath=cdataPrePath+son.getPath().replace("/"+o2pRoot+"/", "/");
				 }else{
					 if("1".equals(conType)||"6".equals(conType)){//XML
						 sonPath=parseXpath(cdataPrePath+son.getPath());
						 nodeDesc.setNodePath(sonPath); 
					 }else if("2".equals(conType)||"10".equals(conType)){//JSON
						 sonPath=cdataPrePath+son.getPath().replace("/o/", "$.").replace("/e/", "/").replace("/", ".");
						 nodeDesc.setNodePath(sonPath); 
					 }
				 }
				 nodeDesc.setParentNodeId(partentId);
				 nodeDesc.setState(EAAPConstants.COMM_STATE_VALID);
				 nodeDesc.setNodeType(getNodeType(type,son));
				 nodeDesc.setIsNeedCheck(EAAPConstants.IS_NEED_CHECK_NO);
				 nodeDesc.setIsNeedSign(EAAPConstants.IS_NEED_SIGN_NO);
//				 parId = provideDao.addNodeDesc(nodeDesc);
				 Map paramMap=new HashMap(); 
				 paramMap.put("path", sonPath);
				 paramMap.put("tcpCtrFId", nodeDesc.getTcpCtrFId()+"");
				 String flagId = providerDao.getNodeIdByPath(paramMap);
				 if(null == flagId){
					 parId = providerDao.addNodeDesc(nodeDesc);
				 }else{
					 parId = Integer.parseInt(flagId);
				 }
				 if("1".equals(conType)||"6".equals(conType)){//XML
					 List<DefaultAttribute> listAttribute = son.attributes();
					 if(listAttribute.size()>0){
						 for(Iterator<DefaultAttribute> sonAttr = listAttribute.iterator(); sonAttr.hasNext();){
							 DefaultAttribute attr = sonAttr.next();
							 nodeDesc.setNodeName(attr.getQualifiedName());
							 nodeDesc.setNodeCode(attr.getQualifiedName());
							 nodeDesc.setNodePath(parseXpath(cdataPrePath+attr.getPath())); 
							 nodeDesc.setNodeType(getNodeType(type,son));
							 nodeDesc.setNodeType("7");
							 nodeDesc.setParentNodeId(parId);
							 providerDao.addNodeDesc(nodeDesc);
						 }
					 }
					 parseCData(son,nodeDesc,parId, son.getPath(),conType);
				 }
			 }
			 if(0 != parId){
				 iteratorXml(conType,type,son,nodeDesc,parId,cdataPrePath);
			 }
			 
		 }
	}
	
	private void parseCData(Element element, NodeDesc nodeDesc, Integer parentId, String cdataPrePath,String conType){
		String text = element.getText();
		if(!StringUtils.isEmpty(text) && text.contains("<")) {
			if(text.trim().startsWith("<![CDATA")) {
				text = text.substring(9, text.length()-3);
			}
			readXml(conType,"",text.trim(),nodeDesc,null,cdataPrePath+"<XML_SPLIT>");
		}
	}
	private String getNodeType(String type,Element son){
		if("head".equals(type)){
			 if(son.selectSingleNode("@type")==null){
				 return EAAPConstants.NODE_TYPE_HEAD;
			 }else{
				 if("head".equals(son.selectSingleNode("@type").getText())){
					 return EAAPConstants.NODE_TYPE_HEAD;
				 }else if("url".equals(son.selectSingleNode("@type").getText())){
					 return "4";
				 }
			 }
		 }else if("body".equals(type)){
			 return EAAPConstants.NODE_TYPE_BODY;
		 }
		return EAAPConstants.NODE_TYPE_BODY;
	}
	private void readXml(String conType,String headXml,String xml,NodeDesc nodeDesc,String operator,String cdataPrePath){
		try {
			if(!"".equals(xml)){
				Document doc  = DocumentHelper.parseText(xml);
				Element root = doc.getRootElement();
				nodeDesc.setNodeName(root.getQualifiedName());
				nodeDesc.setNodeCode(root.getQualifiedName());
				nodeDesc.setNodePath(parseXpath(cdataPrePath+root.getPath())); 
				nodeDesc.setState(EAAPConstants.COMM_STATE_VALID);
				nodeDesc.setNodeType(EAAPConstants.NODE_TYPE_BODY);
				nodeDesc.setIsNeedCheck(EAAPConstants.IS_NEED_CHECK_NO);
				nodeDesc.setIsNeedSign(EAAPConstants.IS_NEED_SIGN_NO);
				Integer parentID = null;//provideDao.addNodeDesc(nodeDesc);
				if("1".equals(conType)||"6".equals(conType)){//XML
					 Map paramMap=new HashMap(); 
					 paramMap.put("path", parseXpath(cdataPrePath+root.getPath()));
					 paramMap.put("tcpCtrFId", nodeDesc.getTcpCtrFId()+"");
					String flagId = providerDao.getNodeIdByPath(paramMap);
					if(null == flagId){
						parentID = providerDao.addNodeDesc(nodeDesc);
					}else{
						parentID = Integer.parseInt(flagId);
					}
					 
					//属性
					NodeDesc nodeDescAttr=null;
					List<Attribute> listAttr=root.attributes();//当前节点的所有属性的list  
					String attrPath=null;
					for(Attribute attr:listAttr){//遍历当前节点的所有属性  
						nodeDescAttr=new NodeDesc();
						nodeDescAttr.setTcpCtrFId(nodeDesc.getTcpCtrFId());
						nodeDescAttr.setNodeTypeCons("1");
						nodeDescAttr.setNodeNumberCons("1");
						attrPath=new StringBuilder(cdataPrePath).append(root.getPath()).append("/@").append(attr.getName()).toString();
						 Map paramNodeMap=new HashMap(); 
						 paramNodeMap.put("path", parseXpath(attrPath));
						 paramNodeMap.put("tcpCtrFId", nodeDescAttr.getTcpCtrFId()+"");
						flagId = providerDao.getNodeIdByPath(paramNodeMap);
						if(null == flagId){
							nodeDescAttr.setNodeName(attr.getName());
							nodeDescAttr.setNodeCode(attr.getName());
							nodeDescAttr.setNodePath(parseXpath(attrPath)); 
							nodeDescAttr.setState(EAAPConstants.COMM_STATE_VALID);
							nodeDescAttr.setNodeType("7");
							nodeDescAttr.setIsNeedCheck(EAAPConstants.IS_NEED_CHECK_NO);
							nodeDescAttr.setIsNeedSign(EAAPConstants.IS_NEED_SIGN_NO);
							nodeDescAttr.setParentNodeId(parentID);
							providerDao.addNodeDesc(nodeDescAttr);
						}
				    }  
				 }
				 list.clear();
					 
				 iteratorXml(conType,"body",root,nodeDesc,parentID,cdataPrePath);
			}
			
			 
			 if(!"".equals(headXml)){
				 Document docHead  = DocumentHelper.parseText(headXml);
					Element rootHead = docHead.getRootElement();
					list.clear();
					 iteratorXml(conType,"head",rootHead,nodeDesc,null,cdataPrePath);
			 }
			
		} catch (DocumentException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
	}
	private String o2pRoot="o2p_root_format";
	@SuppressWarnings("unchecked")
	public void getXsdFormat(String conType,String headXml,String xml,NodeDesc nodeDesc,String operator) {  
		readXml(conType,headXml,xml,nodeDesc,operator,"");
	}
	@SuppressWarnings("unchecked")
	public void getUrlFormat(String url,NodeDesc nodeDesc,String operator){
		String[] arrSplit=null;
		String strUrlParam=TruncateUrlPage(url);
		arrSplit=strUrlParam.split("[&]");
		List<Map<String, Object>> nodeDescList = null;
		nodeDesc.setNodeType(EAAPConstants.NODE_TYPE_BODY);
	    for(String strSplit:arrSplit)
	    {
	          String[] arrSplitEqual=null;         
	          arrSplitEqual= strSplit.split("[=]");	         
	          if(arrSplitEqual.length>1)
	          {   	             
  					nodeDesc.setNodeName(arrSplitEqual[0]);
  					nodeDesc.setNodeCode(arrSplitEqual[0]);
  					nodeDesc.setNodePath("/"+arrSplitEqual[0]);
  					if("Add".equals(operator)){
  						providerDao.addNodeDesc(nodeDesc);
  					}
  					else
  					{
		  				Map map = new HashMap();
		  				map.put("tcpCtrFId", nodeDesc.getTcpCtrFId());
		  				map.put("nodeName", nodeDesc.getNodeName());
		  				map.put("state", EAAPConstants.COMM_STATE_VALID);
		  				nodeDescList = providerDao.queryNodeDesc(map);
		  				if(nodeDescList.size()<1){
		  					providerDao.addNodeDesc(nodeDesc);
		  				}
  					}
  			  }
	          else
	          {
	              if(!"".equals(arrSplitEqual[0]))
	              {
	              nodeDesc.setNodeName(arrSplitEqual[0]);
	  			  nodeDesc.setNodeCode(arrSplitEqual[0]);
	  			  nodeDesc.setNodePath("/"+arrSplitEqual[0]);
	  			  if("Add".equals(operator)){
	  				providerDao.addNodeDesc(nodeDesc);
	  			  }
	  			  else{
	  				Map map = new HashMap();
	  				map.put("tcpCtrFId", nodeDesc.getTcpCtrFId());
	  				map.put("nodeName", nodeDesc.getNodeName());
	  				map.put("state", EAAPConstants.COMM_STATE_VALID);
	  				nodeDescList = providerDao.queryNodeDesc(map);
	  				if(nodeDescList.size()<1){
	  					providerDao.addNodeDesc(nodeDesc);
	  				}
	  			  }
	           }
	        } 
	     }
	}
	  private static String TruncateUrlPage(String strURL)
	    {
		  String strAllParam=null;
	      String[] arrSplit=null;	     
//	      strURL=strURL.trim().toLowerCase();	     
	      arrSplit=strURL.split("[?]");
	      if(strURL.length()>1)
	      {
	          if(arrSplit.length>1)
	          {
                if(arrSplit[1]!=null)
                {
                strAllParam=arrSplit[1];
                }
	          }else if(arrSplit.length==1){
	        	  strAllParam=arrSplit[0];
	          }
	      }     
	    return strAllParam;   
	    }
	@Override
	public List<Map<String, Object>> queryNodeDesc(String string) {
		List<Map<String, Object>> nodeDescList = null;
		Map map = new HashMap();
		map.put("tcpCtrFId", string);
		map.put("state", EAAPConstants.COMM_STATE_VALID);
		nodeDescList = providerDao.queryNodeDesc(map);	
		return nodeDescList;
	}
	@Override
	public Integer updateContractFormat(UserDefined userDefined) {
		String operator="Edit";
		ContractFormat cf = new ContractFormat();
		cf.setTcpCtrFId(Integer.valueOf(userDefined.getTcpCtrFid()));
		cf.setDescriptor(userDefined.getDescriptor());
		cf.setXsdDemo(userDefined.getXsdDemo());
		
		String blobString = null;
		java.sql.Blob blob = null;
			if(userDefined.getFileShareId()!= null && !userDefined.getFileShareId().equals("")){				
				try {		 
					Map paramMap=new HashMap();
					paramMap.put("requestValue", userDefined.getFileShareId());
					List<Map<String, Object>> fileShareList = providerDao.selectFileShare(paramMap);
					if(fileShareList != null){
						for(Map<String, Object> item : fileShareList){
							if(item.get("SFILECONTENT").getClass().getName().equals("oracle.sql.BLOB")){
								 blob = (Blob) item.get("SFILECONTENT");
								 blobString = new String(blob.getBytes(1, (int) blob.length()),"UTF-8");//String ת blob 
							}
							if(item.get("SFILECONTENT").getClass().getName().equals("[B")){
								blobString =(String)item.get("SFILECONTENT");
							}									
							}
						}	
					} catch (Exception e) {
						log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
					}
			}
			else{
				blobString = userDefined.getXsdFormat();
			}	
		 cf.setXsdHeaderFor(userDefined.getXsdHeadFormat());//nzh
		 cf.setXsdFormat(blobString);
		 providerDao.editContractFormat(cf);
		
		NodeDesc nodeDesc = new NodeDesc();
		nodeDesc.setTcpCtrFId(cf.getTcpCtrFId());
		nodeDesc.setState(EAAPConstants.COMM_STATE_VALID);
		nodeDesc.setNodeTypeCons("1");
		nodeDesc.setNodeNumberCons("1");
		if(userDefined.getConType().equals("1") || userDefined.getConType().equals("6")){	
			Map paramMap=new HashMap();
			paramMap.put("tcpCtrFid", userDefined.getTcpCtrFid());
			List <Map<String,Object>> nodeDescId = providerDao.getNodeDesc(paramMap);
			if(nodeDescId !=null && nodeDescId.size()>0){
				for(Map<String,Object> items : nodeDescId){	
					Map paramNodeMap=new HashMap();
					providerDao.deleteNodeDesc(items.get("NODEDESCID").toString(),paramNodeMap);
				}
			}
			getXsdFormat(userDefined.getConType(),new StringBuilder("<").append(o2pRoot).append(">").append(userDefined.getXsdHeadFormat()).append("</").append(o2pRoot).append(">").toString(),blobString,nodeDesc,operator);
		}
		else if(userDefined.getConType().equals("3")||userDefined.getConType().equals("10")) {
			String xmlHead = "";
			if(!"".equals(userDefined.getXsdHeadFormat())){//hEad还是XML格式的
				xmlHead=new StringBuilder("<").append(o2pRoot).append(">").append(userDefined.getXsdHeadFormat()).append("</").append(o2pRoot).append(">").toString();
			}
			getXsdFormat(userDefined.getConType(),xmlHead,"",nodeDesc,operator);
			getUrlFormat(userDefined.getXsdFormat(),nodeDesc,operator);
		}else if(userDefined.getConType().equals("2")) {//JSON
			String xml = "";
			XMLSerializer xmlSerializer = new XMLSerializer();
			if(!"".equals(blobString)){
				JSONObject json = JSONObject.fromObject(blobString);
				xml = xmlSerializer.write(json);
			}
			String xmlHead = "";
			if(!"".equals(userDefined.getXsdHeadFormat())){//hEad还是XML格式的
//				JSONObject jsonHead = JSONObject.fromObject(userDefined.getXsdHeadFormat());
//				xmlHead = xmlSerializer.write(jsonHead);
				xmlHead=new StringBuilder("<").append(o2pRoot).append(">").append(userDefined.getXsdHeadFormat()).append("</").append(o2pRoot).append(">").toString();
			}
			getXsdFormat(userDefined.getConType(),xmlHead,xml,nodeDesc,operator);
		}
		return cf.getTcpCtrFId();
	}
	@Override
	public void deleteNodeDesc(String pageNodeDescId) {
		Map paramMap=new HashMap();
		providerDao.deleteNodeDesc(pageNodeDescId,paramMap);
	}
	@Override
	public List<Map<String, Object>> queryUserFine(String editvalue) {
		Map paramMap=new HashMap();
		paramMap.put("techimpAttId",editvalue);
		return providerDao.queryUserFine(paramMap);
	}
	@Override
	public List<Map<String, Object>> queryDirSerList(String editvalue) {
		Map paramMap=new HashMap();
		paramMap.put("editValue", editvalue);
		return providerDao.queryDirSerList(paramMap);
	}
	@Override
	public List<Map<String, Object>> queryContractFormat(String techImpAttId) {
		Map paramMap=new HashMap();
		paramMap.put("techImpAttId", techImpAttId);
		return providerDao.queryContractFormat(paramMap);
	}
	@Override
	public void editAbility(TechImpAtt techImpAtt) {
		Map paramMap=new HashMap();
		providerDao.editAbility(techImpAtt,paramMap);
	}
	@Override
	public void operatorSerTechImpl(String techimpAttId, String state,String componentId) {
		Map paramMap=new HashMap();
		paramMap.put("techimpAttId", techimpAttId);
		paramMap.put("state",  state);
		paramMap.put("componentId", componentId);
		providerDao.operatorSerTechImpl(paramMap);
	}
	
	public WorkTaskConf queryWorkTaskConf(String componentId,String  serviceId){
		Map paramMap=new HashMap();
		paramMap.put("componentId", componentId);
		paramMap.put("serviceId",  serviceId);
		return providerDao.queryWorkTaskConf(paramMap);
	}
	
	public ServiceProductRela queryServiceProductRela(String componentId,String  serviceId){
		Map paramMap=new HashMap();
		paramMap.put("componentId", componentId);
		paramMap.put("serviceId", serviceId);
		return providerDao.queryServiceProductRela(paramMap);
	}
	@Override
	public String getComponentSeq() {
		return providerDao.getComponentSeq();
	}
	@Override
	public int countProvAbility(Map map) {
		return providerDao.countProvAbility(map);
	}
	@Override
	public List<Map<String, Object>> showAbility(Map map) {
		List<Map<String, Object>> showAbility = null;
		try {
			showAbility = (List<Map<String, Object>>) providerDao.showAbility(map);
		} catch (EAAPException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}	
		return showAbility;
	}
	@Override
	public int countShowAbility(Map map) {
		return providerDao.countShowAbility(map);
	}
 
	@Override
	public List<Map<String, Object>> showAbilityByName(Map map){
		return (List<Map<String, Object>>) providerDao.showAbilityByName(map);
	}
	@Override
	public BigDecimal insertProdOffer(ProdOffer prodOffer){
		return providerDao.insertProdOffer(prodOffer);
	}
	@Override
	public Integer insertOfferProdRel(OfferProdRel offerProdRel){
		return providerDao.insertOfferProdRel(offerProdRel);
	}
	@Override
	public List<Map<String, String>> queryService(String service) {
		// TODO Auto-generated method stub
		Map paramMap=new HashMap();
		return providerDao.queryService(service,paramMap);
		 
	}
	@Override
	public BigDecimal getProductbyCap(Map map){
		return providerDao.getProductbyCap(map);
	}
	@Override
	public Integer insertServiceProRel(Map map){
		return providerDao.insertServiceProRel(map);
	}
	@Override
	public List<Map<String, Object>> showPackage(String componentId,Map map){
		 return  (List<Map<String, Object>>) providerDao.showPackage(componentId,map);
		 
	}
	@Override
	public BigDecimal insertProduct(Product pro){
		return providerDao.insertProduct(pro);
	}
	  
	@Override
	public String editProvSystem(Component component) {
		String componentId = null;
		try {
		componentId = providerDao.editProvSystem(component);	
//		List<Map<String, Object>> showAbilitys;
//			if(componentId != null && !componentId.equals("")){	
//				showAbilitys = providerDao.showAbility(componentId);			
//				if ( showAbilitys != null && showAbilitys.size()>0 ){
//					for(Map<String,Object> item : showAbilitys ){
//						if(component.getState()!= null && !component.getState().equals("")){
//							if(component.getState().equals(EAAPConstants.COMM_STATE_NEW) 
//									|| component.getState().equals(EAAPConstants.COMM_STATE_WAITAUDI)
//									|| component.getState().equals(EAAPConstants.COMM_STATE_NOPASSAUDI)
//									|| component.getState().equals(EAAPConstants.COMM_STATE_ONLINE)
//									|| component.getState().equals(EAAPConstants.COMM_STATE_DOWNLINE)
//									|| component.getState().equals(EAAPConstants.COMM_STATE_DELETE)){							
//								providerDao.operatorSerTechImpl(item.get("TECHIMPATTID").toString(),component.getState());							
//								}	
//							}								
//						}					
//					}
//			}
		} catch (EAAPException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return componentId;
	}
	@Override
	public void updateNodeDesc(UserDefined userDefined) {
		NodeDesc nodeDesc = new NodeDesc();
		if (null != userDefined.getNodeDescId()) {
			for(int i=0;i<userDefined.getNodeDescId().length;i++){
				nodeDesc.setNodeDescId(Integer.valueOf(userDefined.getNodeDescId()[i]));
				nodeDesc.setNevlConsValue(userDefined.getNelConsValue()==null?null:userDefined.getNelConsValue()[i]);
				nodeDesc.setNodeTypeCons(userDefined.getNodeTypeCons()==null?null:userDefined.getNodeTypeCons()[i]);
				nodeDesc.setNodeNumberCons(userDefined.getNodeNumber()==null?null:userDefined.getNodeNumber()[i]);
				nodeDesc.setNodeLengthCons(userDefined.getNodeLength()==null?null:userDefined.getNodeLength()[i]);
				nodeDesc.setNevlConsType(userDefined.getNevlConsType()==null?null:userDefined.getNevlConsType()[i]);
				nodeDesc.setIsNeedCheck(userDefined.getIsNeedCheck()==null?null:userDefined.getIsNeedCheck()[i]);
				nodeDesc.setNodeType(userDefined.getNodeType()==null?null:userDefined.getNodeType()[i]);
				nodeDesc.setJavaField(userDefined.getJavaField()==null?null:userDefined.getJavaField()[i]);
				providerDao.editNodeDesc(nodeDesc);
			}	
		}
	}
	@Override
	public Integer updateProdOffer(ProdOffer prodOffer){
		return providerDao.updateProdOffer(prodOffer);
	}
	@Override
	public List<MainData> selectMainData(MainData mainData) {
		return providerDao.selectMainData(mainData);
	}
	@Override
	public List<MainDataType> selectMainDataType(MainDataType mainDataType) {
		return providerDao.selectMainDataType(mainDataType);
	}
	@Override
	public Integer addComponentPrice(ComponentPrice componentPrice) {
		return providerDao.addComponentPrice(componentPrice);
	}
	@Override
	public Integer addBillingDiscount(BillingDiscount billingDiscount) {
		return providerDao.addBillingDiscount(billingDiscount);
	}
	@Override
	public Integer addRatingCurveDetail(RatingCurverDetail ratingCurverDetail) {
		return providerDao.addRatingCurveDetail(ratingCurverDetail);
	}
	@Override
	public Integer updateComponentPrice(ComponentPrice componentPrice) {
		return providerDao.updateComponentPrice(componentPrice);
	}
	@Override
	public Integer updateBillingDiscount(BillingDiscount billingDiscount) {
		return providerDao.updateBillingDiscount(billingDiscount);
	}
	@Override
	public List<RatingCurverDetail> queryRatingCurveDetail(
			RatingCurverDetail ratingCurverDetail) {
		return providerDao.queryRatingCurveDetail(ratingCurverDetail);
	}
	@Override
	public void delRatingCurveDetail(RatingCurverDetail r) {
		providerDao.delRatingCurveDetail(r);
	}
	@Override
	public List<ComponentPrice> queryComponentPrice(Map<String, Object> map) {
		return providerDao.queryComponentPrice(map);
	}
	@Override
	public BillingDiscount queryBillingDiscount(BillingDiscount billingDiscount) {
		return providerDao.queryBillingDiscount(billingDiscount);
	}
	@Override
	public void updateContractFormat2(UserDefined userDefined) {
		ContractFormat cf = new ContractFormat();
		cf.setTcpCtrFId(Integer.valueOf(userDefined.getTcpCtrFid()));
		cf.setDescriptor(userDefined.getDescriptor());
		cf.setXsdDemo(userDefined.getXsdDemo());
		cf.setConType(userDefined.getConType());
		String blobString = null;
		java.sql.Blob blob = null;
			if(userDefined.getFileShareId()!= null && !userDefined.getFileShareId().equals("")){				
				try {		 
					Map paramMap=new HashMap();
					paramMap.put("requestValue", userDefined.getFileShareId());
					List<Map<String, Object>> fileShareList = providerDao.selectFileShare(paramMap);
					if(fileShareList != null){
						for(Map<String, Object> item : fileShareList){
							if(item.get("SFILECONTENT").getClass().getName().equals("oracle.sql.BLOB")){
								 blob = (Blob) item.get("SFILECONTENT");
								 blobString = new String(blob.getBytes(1, (int) blob.length()),"UTF-8");//String ת blob 
							}
							if(item.get("SFILECONTENT").getClass().getName().equals("[B")){
								blobString =(String)item.get("SFILECONTENT");
							}									
							}
						}	
					} catch (Exception e) {
						log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
					}
			}
			else{
				blobString = userDefined.getXsdFormat();
			}	
		 cf.setXsdFormat(blobString);
		 providerDao.editContractFormat(cf);
	}
	@Override
	public void addContractFormat2(UserDefined userDefined) {
		ContractFormat cf = new ContractFormat();
		cf.setContractVersionId(Integer.valueOf(userDefined.getContractVersionId()));
		cf.setConType(userDefined.getConType());
		cf.setReqRsp(userDefined.getReqRsp());

		cf.setXsdDemo(userDefined.getXsdDemo());
		cf.setDescriptor(userDefined.getDescriptor());
		cf.setState(EAAPConstants.COMM_STATE_VALID);
			
		String blobString = null;
		java.sql.Blob blob = null;
			if(userDefined.getFileShareId()!= null && !userDefined.getFileShareId().equals("")){				
				try {		 
					Map paramMap=new HashMap();
					paramMap.put("requestValue", userDefined.getFileShareId());
					List<Map<String, Object>> fileShareList = providerDao.selectFileShare(paramMap);
					if(fileShareList != null){
						for(Map<String, Object> item : fileShareList){
							if(item.get("SFILECONTENT").getClass().getName().equals("oracle.sql.BLOB")){
								 blob = (Blob) item.get("SFILECONTENT");
								 blobString = new String(blob.getBytes(1, (int) blob.length()),"UTF-8");//String ת blob 
							}
							if(item.get("SFILECONTENT").getClass().getName().equals("[B")){
								blobString =(String)item.get("SFILECONTENT");
							}									
							}
						}	
					} catch (Exception e) {
						log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
					}
			}
			else{
				blobString = userDefined.getXsdFormat();
			}
			try {
				cf.setXsdFormat(blobString);
				providerDao.addContractFormat(cf);
			} catch (EAAPException e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
	}
	@Override
	public Component queryProv(Component component) {
		Component editComponent = new Component();
		try {
			editComponent = (Component) providerDao.queryProv(component);
		} catch (EAAPException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
		return editComponent;
	}
	@Override
	public void updateComponentById(Component component) {
		providerDao.updateComponent(component);
	}
	@Override
	public void updateProdOfferById(String componentId) {
		
	}
	
	public List<Map<String, Object>> getProcessList(Map<String, Object> map){
		
		return providerDao.getProcessList(map);
	}
	@Override
	public List<Map<String, Object>> queryTechImplAttrSpec(
			Map<String, String> hashMap) {
		return providerDao.queryTechImplAttrSpec(hashMap);
	}
	
	public List<Map<String, Object>> queryComponentState(Map map){
		return providerDao.queryComponentState(map);
	}
	
	@Override
	public Integer addNodeDesc(NodeDesc nodeDesc) {
		return providerDao.addNodeDesc(nodeDesc);
	}
	private String parseXpath(String spath){
		String[] npathAay=spath.split("/");
        StringBuilder newPath=new StringBuilder();
        for(String subpath:npathAay){
        	if(subpath!=null&&!"".equals(subpath)){
        		if(subpath.indexOf("<XML_SPLIT>")>-1){//cdata处理
        			if(subpath.indexOf(":")>-1){//带cdata且有命名空间
            			newPath.append("/*[local-name()='").append(subpath.substring(0,subpath.length()-11).split(":")[1]).append("']<XML_SPLIT>");
            		}else{//带cdata且没有命名空间
            			newPath.append("/").append(subpath);
            		}
        		}else{
        			if(subpath.indexOf(":")>-1){//不带cdata且有命名空间
            			newPath.append("/*[local-name()='").append(subpath.split(":")[1]).append("']");
            		}else{//不带cdata且没有命名空间
            			newPath.append("/").append(subpath);
            		}
        		}
        		
        	}
        }
        return newPath.toString();
	}
}

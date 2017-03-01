package com.ailk.o2p.portal.service.pardSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.CharSpec;
import com.ailk.eaap.op2.bo.CharSpecValue;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.dao.CharSpecDao;
import com.ailk.o2p.portal.service.maindata.MainDataServ;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
@Service
@Transactional
public class PardSpecServ implements IPardSpecServ {
	@Autowired
	private CharSpecDao charSpecDao ;
	@Autowired
	private MainDataServ mainDataServ;

	public CharSpecDao getCharSpecDao() {
		return charSpecDao;
	}
	public void setCharSpecDao(CharSpecDao charSpecDao) {
		this.charSpecDao = charSpecDao;
	}

	public CharSpec qryCharSpecById(String charSpecId){
		if(charSpecId == null){
			throw new BusinessException(ExceptionCommon.WebExceptionCode," query char_spec's id is null!", null);
		}
		Map paramMap=new HashMap();
		paramMap.put("charSpecId", charSpecId);
		return charSpecDao.qryCharSpecById(paramMap);
	}
	
	public List<CharSpec> qryCharSpec(CharSpec charSpec){
		return charSpecDao.qryCharSpec(charSpec);
	}
	
	public String cntCharSpec(CharSpec charSpec){
		return charSpecDao.cntCharSpec(charSpec);
	}
	
	public String addCharSpec(CharSpec charSpec,List<CharSpecValue> charSpecValueList){
		String charSpecId = charSpecDao.getCharSpecSeq();
		String charSpecValueIds = this.addCharSpecValue(charSpecId, charSpecValueList);
		
		charSpec.setCharSpecId(charSpecId);
		charSpec.setStatusCd(EAAPConstants.COMM_STATE_ONLINE);
		if("4".equals(charSpec.getValueType())){
			charSpec.setDefaultValue(charSpecValueIds);
		}
		
		charSpecDao.insertCharSpec(charSpec);
		return charSpecId;
	}
	
	private String addCharSpecValue(String charSpecId,List<CharSpecValue> charSpecValueList){
		StringBuffer sb = new StringBuffer();
		for(CharSpecValue charSpecValue:charSpecValueList){
			String charSpecValueId = charSpecDao.getCharSpecValueSeq();
			charSpecValue.setCharSpecId(charSpecId);
			charSpecValue.setCharSpecValueId(charSpecValueId);
			charSpecValue.setStatusCd(EAAPConstants.COMM_STATE_ONLINE);
			
			charSpecDao.insertCharSpecValue(charSpecValue);
			if("0".equals(charSpecValue.getIsDefault())){
				sb.append(charSpecValueId).append("/");
			}
		}
		
		return sb.toString().length()==0?"":sb.toString().substring(0, sb.toString().length()-1);
	}
	
	public String updateCharSpec(CharSpec charSpec,List<CharSpecValue> charSpecValueList){
		Map paramMap=new HashMap(); 
		paramMap.put("charSpecId", charSpec.getCharSpecId());
		charSpecDao.delCharSpecValue(paramMap);
		String charSpecValueIds = this.addCharSpecValue(charSpec.getCharSpecId(), charSpecValueList);
		if("4".equals(charSpec.getValueType())){
			charSpec.setDefaultValue(charSpecValueIds);
		}
		
		charSpecDao.updateCharSpec(charSpec);
		return charSpec.getCharSpecId();
	}
	
	public List<CharSpecValue> qryCharSpecValue(CharSpecValue charSpecValue){
		if(null==charSpecValue){
			throw new BusinessException(ExceptionCommon.WebExceptionCode," query char_spec_value's params is null!", null);
		}
		return charSpecDao.qryCharSpecValue(charSpecValue);
	}
	
	public void delCharSpec(String charSpecId){
		Map paramMap=new HashMap(); 
		paramMap.put("charSpecId", charSpecId);
		charSpecDao.delCharSpec(paramMap);
		charSpecDao.delCharSpecValue(paramMap);
	}
	
	public Integer isUpdateOrDelete(String charSpecId){
		Map paramMap=new HashMap(); 
		paramMap.put("charSpecId", charSpecId);
		return charSpecDao.isUD(paramMap);
	}
	
	public int checkPageInTypeCount(String pageInType,int orgId){
		String pageInTypeName = getPageInTypeName(pageInType);
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("pageInTypeName", pageInTypeName);
		List<Map<String, Object>> pageInTypeList = charSpecDao.getPageInTypeIds(paraMap);
		if(pageInTypeList!=null&&pageInTypeList.size()>0){
			Map<String, Object> pageInTypeMap = pageInTypeList.get(0);
			int length = Integer.parseInt(String.valueOf(pageInTypeMap.get("LENGTH")));
			paraMap = new HashMap<String, Object>();
			paraMap.put("orgId", orgId);
			paraMap.put("pageInType", pageInType);
			List<Integer> mappingIdList = charSpecDao.getMappingIdList(paraMap);
			int mappingIdSize=mappingIdList.size();
			if(mappingIdSize>=length){
				return -1;//超过总数限制
			}else{
				return 0;//可以配置
			}
		}else{
			return -2;//数据未配置
		}
	}
	
	private String getPageInTypeName(String pageInType){
		String pageInTypeName=null;
		switch(Integer.parseInt(pageInType)){
			case 1:pageInTypeName="SEQ_ATTR_SPEC_TEXT";break;
			case 2:pageInTypeName="SEQ_ATTR_SPEC_PASSWORD";break;
			case 3:pageInTypeName="SEQ_ATTR_SPEC_NUMBER";break;
			case 4:pageInTypeName="SEQ_ATTR_SPEC_COMBOX";break;
			case 5:pageInTypeName="SEQ_ATTR_SPEC_DATE";break;
			case 6:pageInTypeName="SEQ_ATTR_SPEC_EMAIL";break;
		}
		return pageInTypeName;
	}
	
}


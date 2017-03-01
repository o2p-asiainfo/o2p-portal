package com.ailk.o2p.portal.service.message;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.message.Message;
import com.ailk.eaap.op2.bo.message.MessageRecipent;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.eaap.op2.dao.MessageBaseDao;
import com.ailk.o2p.portal.dao.message.MessageDao;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.workflow.application.para.worklists.WorklistsQueryPara;
import com.workflow.remote.WorkflowRemoteInterface;
@Service
public class MessageServ implements IMessageServ{
	@Autowired
	private MainDataDao mainDataSqlDAO ;
	@Autowired
	private MainDataTypeDao mainDataTypeSqlDAO;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private MessageBaseDao msgBaseDao;
	
	public Integer countMessageForLook(Map<String,Object> map){
		Integer cnt = 0;
		Map paramMap=new HashMap(); 
		List<String> roleList = messageDao.getRoleByOrgId(map.get("orgId")+"",paramMap);
		if(null==roleList||roleList.size()==0){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"System Error!==>countMessageForLook method");
		}else{
			List<Message> showMessageList = new ArrayList<Message>();
			for(String roleId:roleList){
				map.put("roleId", roleId);
				showMessageList.addAll(messageDao.showMessageListByRole(map));
			}
			cnt += this.delChongFu(showMessageList).size();
			cnt += messageDao.countMessageForLookByPerson(map);
			
		}
		return cnt;
	}
	
	public List<Message> showMessageList(Map<String,Object> map){
		List<Message> showMessageList = new ArrayList<Message>();
		Map paramMap=new HashMap(); 
		List<String> roleList = messageDao.getRoleByOrgId(map.get("orgId").toString(),paramMap);
		if(null==roleList||roleList.size()==0){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"System Error!");
		}else{
			for(String roleId:roleList){
				map.put("roleId", roleId);
				showMessageList.addAll(messageDao.showMessageListByRole(map));
			}
			
			showMessageList.addAll(messageDao.showMessageListByPerson(map));
		}
		return this.delChongFu(showMessageList);
	}
	
	public List<Message> showMessageListByStatus(Map<String,Object> paramMap){
		return messageDao.showMessageListByStatus(paramMap);
	}
	public Integer cntMessageListByStatus(Map<String,Object> paramMap){
		return messageDao.cntMessageListByStatus(paramMap);
	}
	
	public BigDecimal getMsgIdByQueryTitle(String queryTitle){
		Map paramMap=new HashMap(); 
		Message msg = messageDao.qryMessageByContentId(queryTitle,paramMap).get(0);
		if(null!=msg){
			return msg.getMsgId();
		}
		return null;
	}
	
	public String updateMsgForWorkFlow(BigDecimal msgId){
		MessageRecipent msgRec = new MessageRecipent();
		msgRec.setMsgId(msgId);
		List<MessageRecipent> msgRecList = messageDao.queryMessageRecipentList(msgRec);
		
		if(null==msgRecList||msgRecList.isEmpty()||msgRecList.size()>1){
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"system error!",null);
		}
		msgRec = msgRecList.get(0);
		msgRec.setStatusCd(EAAPConstants.MESSAGE_LOOK_MSG);
		msgRec.setLookTimes(msgRec.getLookTimes()+1);
		return messageDao.updateMessageRecipent(msgRec)+"";
	}
	
	public Map<String,Object> lookMsgById(String orgId,String msgId){
		Map<String,Object> obs = new HashMap<String,Object>();
		Message msg = new Message();
		msg.setMsgId(new BigDecimal(msgId));
		msg = msgBaseDao.getMsgById(msg);
		obs.put("message", msg);
		
		MessageRecipent msgRec = new MessageRecipent();
		msgRec.setRecId(orgId);
		msgRec.setMsgId(new BigDecimal(msgId));
		if(null!=msg&&EAAPConstants.MESSAGE_MSG_TYPE_TODO.equals(msg.getMsgType())){
			msgRec.setStatusCd(EAAPConstants.MESSAGE_LOOK_MSG_FLOW);
		}else{
			msgRec.setStatusCd(EAAPConstants.MESSAGE_LOOK_MSG);
		}
		List<MessageRecipent> msgRecList = messageDao.queryMessageRecipentList(msgRec);
		if(null==msgRecList||msgRecList.size()==0){
			msgRec.setLookTimes(1);
			msgRec.setMsgRecId(new BigDecimal(messageDao.insertMessageRecipent(msgRec)));
		}else if(msgRecList.size()==1){
			msgRec = msgRecList.get(0);
			msgRec.setLookTimes(msgRec.getLookTimes()+1);
			messageDao.updateMessageRecipent(msgRec);
		}else{
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"System Error!==>lookMsgById method : msgRecList size > 1");
		}
			
		msgRecList = null;
		obs.put("msgRec", msgRec);
		msg = null;
		msgRec = null;
		return obs;
	}
	
	public boolean ignoreMessage(String orgId,String msgId){
		Message msg = new Message();
		msg.setMsgId(new BigDecimal(msgId));
		
		MessageRecipent msgRec = new MessageRecipent();
		msgRec.setRecId(orgId);
		msgRec.setMsgId(new BigDecimal(msgId));
		msgRec.setLookTimes(0);
		msgRec.setStatusCd(EAAPConstants.MESSAGE_IGNORE_MSG);
		return null!=messageDao.insertMessageRecipent(msgRec);
	}
	
	public boolean submitMsgDecision(MessageRecipent msgRec){
		List<MessageRecipent> msgRecList = messageDao.queryMessageRecipentList(msgRec);
		if(null!=msgRecList&&msgRecList.size()==1){
			return messageDao.updateMessageRecipent(msgRec)==1;
		}else{
			throw new BusinessException(ExceptionCommon.WebExceptionCode,"System Error!==>lookMsgById method : msgRecList null");
		}
		
		//return messageDao.updateMessageRecipent(msgRec)==1;
		
	}
	
	public String addMessageRecipent(MessageRecipent msgRec){
		msgRec.setStatusCd(EAAPConstants.MESSAGE_LOOK_MSG);
		msgRec.setLookTimes(1);
		return messageDao.insertMessageRecipent(msgRec);
	}
	
	public List<MessageRecipent> queryMessageRecipentList(MessageRecipent msgRec){
		return messageDao.queryMessageRecipentList(msgRec);
	}
	
	public Integer countMessageRecipentList(MessageRecipent msgRec){
		return messageDao.countMessageRecipentList(msgRec);
	}
	
	public Integer updateMessageRecipent(MessageRecipent msgRec){
		return messageDao.updateMessageRecipent(msgRec);
	}
	
	public Integer ignoreMessage(MessageRecipent msgRec){
		msgRec.setStatusCd(EAAPConstants.MESSAGE_IGNORE_MSG);
		return messageDao.updateMessageRecipent(msgRec);
	}
	
	private List<Message> delChongFu(List<Message> msgList){
		List<Message> list = new ArrayList<Message>();
		for(Message i:msgList){  
	        if(!list.contains(i)){  
	        	list.add(i);  
	        }  
	    } 
		return list;
	}
	
	public String getCheckMsgByObjectId(String qryKey,String objId){
		Map paramMap=new HashMap(); 
		List<Message> msgList = messageDao.qryMessageByContentId(qryKey.replace("#id", objId),paramMap);
		if(null == msgList || msgList.size() == 0){
			return "";
		}else{
			Message msg = msgList.get(0);
			return msg.getMsgContent();
		}
	}
	
	public Map getMsgFlowForWork(String receiveUser,Integer pages,Integer rows){
		WorklistsQueryPara queryPara=new WorklistsQueryPara();
		queryPara.setReceive_User(receiveUser);
		
		String[] configLocations= new String[]{"classpath:eaap-op2-workflowRemote-spring.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
		WorkflowRemoteInterface wi = (WorkflowRemoteInterface)ctx.getBean("workflowRemote");
		
		return wi.getNeedDealWorkflistsList(queryPara,pages,rows,false);
	}
	
	
//------------------------------------------------------	
	public MainDataDao getMainDataSqlDAO() {
		return mainDataSqlDAO;
	}
	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}
	public MainDataTypeDao getMainDataTypeSqlDAO() {
		return mainDataTypeSqlDAO;
	}
	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}
	public MessageDao getmessageDao() {
		return messageDao;
	}
	public void setmessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
	public MessageBaseDao getMsgBaseDao() {
		return msgBaseDao;
	}
	public void setMsgBaseDao(MessageBaseDao msgBaseDao) {
		this.msgBaseDao = msgBaseDao;
	}
	
}

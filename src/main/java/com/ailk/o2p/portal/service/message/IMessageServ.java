package com.ailk.o2p.portal.service.message;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.message.Message;
import com.ailk.eaap.op2.bo.message.MessageRecipent;

public interface IMessageServ {

	public Integer countMessageForLook(Map<String,Object> map);
	public List<Message> showMessageList(Map<String,Object> map);
	
	public List<Message> showMessageListByStatus(Map<String,Object> paramMap);
	public Integer cntMessageListByStatus(Map<String,Object> paramMap);
	
	public Map<String,Object> lookMsgById(String orgId,String msgId);
	public boolean ignoreMessage(String orgId,String msgId);
	public boolean submitMsgDecision(MessageRecipent msgRec);
	
	public String addMessageRecipent(MessageRecipent msgRec);
	public List<MessageRecipent> queryMessageRecipentList(MessageRecipent msgRec);
	public Integer countMessageRecipentList(MessageRecipent msgRec);
	public Integer updateMessageRecipent(MessageRecipent msgRec);
	//待办提醒
	public String updateMsgForWorkFlow(BigDecimal msgId);
	public Map getMsgFlowForWork(String receiveUser,Integer pages,Integer rows);
	public BigDecimal getMsgIdByQueryTitle(String queryTitle);
	
	public String getCheckMsgByObjectId(String qryKey,String objId);
}

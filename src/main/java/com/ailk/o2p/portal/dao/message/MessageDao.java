package com.ailk.o2p.portal.dao.message;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.message.Message;
import com.ailk.eaap.op2.bo.message.MessageRecipent;

public interface MessageDao {
	
	public List<String> getRoleByOrgId(String orgId,Map paramMap);
	
	public Integer countMessageForLookByRole(Map<String,Object> paramMap);
	public Integer countMessageForLookByPerson(Map<String,Object> paramMap);
	public List<Message> showMessageListByRole(Map<String,Object> paramMap);
	public List<Message> showMessageListByPerson(Map<String,Object> map);
	
	public List<Message> showMessageListByStatus(Map<String,Object> paramMap);
	public Integer cntMessageListByStatus(Map<String,Object> paramMap);
	public List<Message> qryMessageByContentId(String titleQuery,Map paramMap);

	public String insertMessageRecipent(MessageRecipent msgRec);
	public List<MessageRecipent> queryMessageRecipentList(MessageRecipent msgRec);
	public Integer countMessageRecipentList(MessageRecipent msgRec);
	public Integer updateMessageRecipent(MessageRecipent msgRec);
	
}

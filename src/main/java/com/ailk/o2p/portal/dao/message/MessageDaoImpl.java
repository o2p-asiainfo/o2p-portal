package com.ailk.o2p.portal.dao.message;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.message.Message;
import com.ailk.eaap.op2.bo.message.MessageRecipent;
import com.linkage.rainbow.dao.SqlMapDAO;
@Repository 
public class MessageDaoImpl implements MessageDao{
	@Autowired
	private SqlMapDAO sqlMapDao;

	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}
	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}
	
	//--------------------------------------------
	@SuppressWarnings("unchecked")
	public List<String> getRoleByOrgId(String orgId,Map paramMap){
		paramMap.put("orgId", orgId);
		return sqlMapDao.queryForList("eaap-op2-portal-message.getRoleByOrgId", paramMap);
	}
	
	public Integer countMessageForLookByRole(Map<String,Object> paramMap){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-message.countMessageForLookByRole", paramMap);
	}
	
	public Integer countMessageForLookByPerson(Map<String,Object> paramMap){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-message.countMessageForLookByPerson", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> showMessageListByStatus(Map<String,Object> paramMap){
		return sqlMapDao.queryForList("eaap-op2-portal-message.showMessageListByStatus", paramMap);
	}
	public Integer cntMessageListByStatus(Map<String,Object> paramMap){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-message.cntMessageListByStatus", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> showMessageListByRole(Map<String,Object> paramMap){
		return sqlMapDao.queryForList("eaap-op2-portal-message.showMessageListByRole", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> showMessageListByPerson(Map<String,Object> map){
		return sqlMapDao.queryForList("eaap-op2-portal-message.showMessageListByPerson", map);
	}
	
	public String insertMessageRecipent(MessageRecipent msgRec){
		return sqlMapDao.insert("eaap-op2-portal-message.insertMessageRecipent", msgRec).toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<MessageRecipent> queryMessageRecipentList(MessageRecipent msgRec){
		return sqlMapDao.queryForList("eaap-op2-portal-message.selectMessageRecipent", msgRec);
	}
	
	public Integer countMessageRecipentList(MessageRecipent msgRec){
		return (Integer) sqlMapDao.queryForObject("eaap-op2-portal-message.countMessageRecipent", msgRec);
	}
	
	public Integer updateMessageRecipent(MessageRecipent msgRec){
		return sqlMapDao.update("eaap-op2-portal-message.updateMessageRecipent", msgRec);
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> qryMessageByContentId(String titleQuery,Map paramMap){
		paramMap.put("titleQuery", titleQuery);
		return sqlMapDao.queryForList("eaap-op2-portal-message.qryMessageByContentId", paramMap);
	}
}

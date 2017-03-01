package com.ailk.o2p.portal.dao.forgotpwd;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.OrgAuthCode;
import com.linkage.rainbow.dao.SqlMapDAO;

@Repository
public class ForgotPwdDao implements IForgotPwdDao{
	
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;
	
	public Integer insertOrgAuthCode(OrgAuthCode orgAuthCode) {
		return (Integer)sqlMapDao.insert("orgAuthCode.insertOrgAuthCode", orgAuthCode);
	}
	
	public void updateOrgAuthDisableTime(String authCode){
		sqlMapDao.update("orgAuthCode.updateOrgAuthDisableTime", authCode);
	}

	public List<OrgAuthCode> selectOrgAuthCode(OrgAuthCode orgAuthCode){
	 	return (ArrayList<OrgAuthCode>)sqlMapDao.queryForList("orgAuthCode.selectOrgAuthCode", orgAuthCode) ;
    }
	
	public SqlMapDAO getSqlMapDao() {
		return sqlMapDao;
	}

	public void setSqlMapDao(SqlMapDAO sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

}

package com.ailk.o2p.portal.service.forgotpwd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.OrgAuthCode;
import com.ailk.eaap.op2.dao.OrgDao;
import com.ailk.o2p.portal.dao.forgotpwd.ForgotPwdDao;
import com.ailk.o2p.portal.dao.forgotpwd.IForgotPwdDao;
import com.ailk.o2p.portal.dao.org.IOrgDao;

@Service
public class ForgotPwdService implements IForgotPwdService {

	@Autowired
	private IOrgDao orgSqlDAO;
	@Autowired
	private IForgotPwdDao forgotPwdDao;

	public List<Org> selectOrg(Org org) {
		return orgSqlDAO.selectOrg(org);
	}

	public Integer insertOrgAuthCode(OrgAuthCode orgAuthCode) {
		return forgotPwdDao.insertOrgAuthCode(orgAuthCode);
	}

	public List<OrgAuthCode> selectOrgAuthCode(OrgAuthCode orgAuthCode) {
		return forgotPwdDao.selectOrgAuthCode(orgAuthCode);
	}
	
	public void updateOrgAuthDisableTime(String authCode){
		 forgotPwdDao.updateOrgAuthDisableTime(authCode);
	}

}

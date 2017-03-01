package com.ailk.o2p.portal.dao.forgotpwd;

import java.util.List;

import com.ailk.eaap.op2.bo.OrgAuthCode;

/**
 * @ClassName: IForgotPwdDao
 * @Description: 
 * @author zhengpeng
 * @date 2015-8-6 上午7:39:46
 *
 */
public interface IForgotPwdDao {
	
	public Integer insertOrgAuthCode(OrgAuthCode orgAuthCode);
	
	public List<OrgAuthCode> selectOrgAuthCode(OrgAuthCode orgAuthCode);
	
	public void updateOrgAuthDisableTime(String authCode);

}

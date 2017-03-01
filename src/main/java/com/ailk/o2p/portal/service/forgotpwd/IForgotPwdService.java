package com.ailk.o2p.portal.service.forgotpwd;

import java.util.List;

import com.asiainfo.integration.o2p.web.bo.Org;
import com.ailk.eaap.op2.bo.OrgAuthCode;

/**
 * @ClassName: IForgotPwdService
 * @Description: 
 * @author zhengpeng
 * @date 2015-8-6 上午7:38:38
 *
 */
public interface IForgotPwdService {
	
	public List<Org> selectOrg(Org org);

	public Integer insertOrgAuthCode(OrgAuthCode orgAuthCode);

	public List<OrgAuthCode> selectOrgAuthCode(OrgAuthCode orgAuthCode);
	
	public void updateOrgAuthDisableTime(String authCode);

}

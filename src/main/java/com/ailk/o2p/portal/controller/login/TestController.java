package com.ailk.o2p.portal.controller.login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ailk.o2p.portal.bo.Demo;
import com.ailk.o2p.portal.controller.BaseController;

@Controller
public class TestController extends BaseController {
	
	@RequestMapping(value = "/demo/validate", method = RequestMethod.POST)
	@ResponseBody
	public String doValidate(final HttpServletRequest request,
			final HttpServletResponse response){
		Demo demo=new Demo();
		demo.setRightName(getRequestValue(request, "rightName"));
		demo.setRightDesc(getRequestValue(request, "rightDesc"));
		JSONObject json=validateWithException(demo);
		return json.toString();
	}
	
	


}

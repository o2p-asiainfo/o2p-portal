package com.ailk.o2p.portal.controller.messageflow;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ailk.o2p.portal.controller.BaseController;
import com.ailk.o2p.portal.service.messageflow.IMessageFlowService;
@Controller
public class MessageFlowController extends BaseController {

	@Autowired
	private IMessageFlowService messageFlowService;
	
	/**jsonObject参数形式
	 * {
	    'messageFlowId':123456,
	    'name':'mawl'
		'transformerOne':123456,
		'serviceId':34567,
		'transformerTwo':123456,
		'type':'old',
		'componentId':123456
		}
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/messageFlow/saveMessageFlow")
	@ResponseBody
	public String saveMessageFlow(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String jsonObject = this.getRequestValue(request, "jsonObject");
		JSONObject finalJson = JSONObject.fromObject(jsonObject);
		JSONObject json = new JSONObject();
		Map<String,String> flag = messageFlowService.getSerInvokeInsMessage(finalJson);
		if(null != flag && flag.size()>0){
			json.put("result", "success");
			json.put("id", flag.get("id"));
		}else{
			Map<String,String> result = messageFlowService.saveOrUpdateMessageFlow(finalJson);
			json.put("result", result.get("result"));
			json.put("id", result.get("id"));
			json.put("call", result.get("call"));
		}
		return json.toString();
	}
	/**jsonObject参数形式
	 * {
		'messageFlowId':123456,
		'serInvokeInsId':123456,
		'componentId':34567,
		'serviceId':123456,
		'name':'mawl'
		}
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/messageFlow/saveSerInvokeIns")
	@ResponseBody
	public String saveSerInvokeIns(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String jsonObject = this.getRequestValue(request, "jsonObject");
		JSONObject finalJson = JSONObject.fromObject(jsonObject);
		JSONObject json = new JSONObject();
		Map<String,String> flag = messageFlowService.getSerInvokeInsMessage(finalJson);
		if(null != flag && flag.size()>0){
			json.put("result", "success");
			json.put("id", flag.get("serInvoId"));
		}else{
			Map<String,String> result = messageFlowService.saveOrUpdateSerInvokeIns(finalJson);
			json.put("result", result.get("result"));
			json.put("id", result.get("id"));
		}
		return json.toString();
	}
	

	/**
	 * 用于工作流配置NoticeTask环节创建或修改服务调用实例
	 * @param request
	 * @param response
	 * @return
	 * @throws lxh
	 */
	@RequestMapping(value = "/messageFlow/saveSerInvokeInsForNoticeTask")
	@ResponseBody
	public String saveSerInvokeInsForNoticeTask(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String jsonObject = this.getRequestValue(request, "jsonObject");
		JSONObject finalJson = JSONObject.fromObject(jsonObject);
		JSONObject json = new JSONObject();
		Map<String,String> flag = messageFlowService.getSerInvokeInsMessageForNoticeTask(finalJson);
		if(null != flag && flag.size()>0){
			json.put("result", "success");
			json.put("id", flag.get("serInvoId"));
		}else{
			Map<String,String> result = messageFlowService.saveOrUpdateSerInvokeIns(finalJson);
			json.put("result", result.get("result"));
			json.put("id", result.get("id"));
		}
		return json.toString();
	}
	
	
}

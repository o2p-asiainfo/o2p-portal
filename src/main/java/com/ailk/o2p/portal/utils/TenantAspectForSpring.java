package com.ailk.o2p.portal.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.asiainfo.integration.o2p.sqllog.util.OperateActContext;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;

/**
 * @ClassName: TenantAspectForSpring
 * @Description: 租户Spring Dao 拦截器
 * @author zhengpeng
 * @date 2016-4-25 下午2:58:51
 *
 */
public class TenantAspectForSpring {
	
	private static final Logger log = Logger.getLog(TenantAspectForSpring.class);
	
	public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		log.debug("############## TenantAspectForSpring className:" + joinPoint.getTarget().getClass().getName() + " || method：" + method.getName());
		
		Object[] args = joinPoint.getArgs();
		if(args != null && args.length > 0){
			for(Object argObj: args){
				UserRoleInfo userRoleInfo = OperateActContext.getUserInfo();
				if(userRoleInfo != null && userRoleInfo.getTenantId() != null){
					this.setTenantToObject(argObj, userRoleInfo.getTenantId());
				}
			}
		}
		
		// 执行dao包里的方法
		Object result = joinPoint.proceed();

		return result;
	}
	
	/**
	 * 判断 CLOUD 查询基础数据 是否需要过滤运营商数据
	 * MainDataType/MainData    主数据
	 * CharSpec                 属性规格
	 * PriceItem                定价科目
	 * TimeSeqDef/TimeSeqDtl    时间规格
	 * RsSysCellDef             基站
	 * @param methodName
	 * @return
	 */
	private Boolean isOperateFilter(String methodName){
		if(methodName.contains("queryPriceItem") || methodName.contains("MainData") || methodName.contains("MainDataType")
				|| methodName.contains("qryCharSpec") || methodName.contains("cntCharSpec") || methodName.contains("qryCharSpecValue")
				|| methodName.contains("queryTimeSeqDef") || methodName.contains("countTimeSeqDef") 
				|| methodName.contains("queryTimeSeqDtl") 
				|| methodName.contains("queryRsSysCellDef") || methodName.contains("countRsSysCellDef") || methodName.contains("selRsSysCellDef")
				){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private void setTenantToObject(Object paramObj,Integer tenantId){
		if(paramObj != null){
			if(paramObj instanceof HashMap){ 
				this.setTenantToMap((HashMap)paramObj, tenantId);
			}else if(paramObj instanceof List){
				this.setTenantToList((List) paramObj, tenantId);
			} else{
				this.setTenantToTenantBean(paramObj, tenantId);
			}
		}
	}
	

	@SuppressWarnings({"unchecked", "rawtypes" })
	private void setTenantToMap(HashMap paramMap,Integer tenantId){
		if(!paramMap.containsKey(TenantInterceptor.TENANT_CODE)){
			paramMap.put(TenantInterceptor.TENANT_ID, tenantId); 
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void setTenantToList(List paramList,Integer tenantId){
		for(Object paramObject : paramList){
			if(paramObject instanceof HashMap){
				this.setTenantToMap((HashMap) paramObject,tenantId);
			}else{
				this.setTenantToTenantBean(paramObject,tenantId);
			}
		}
	}
	
	private void setTenantToTenantBean(Object paramObject,Integer tenantId) {
		Class objectClass = paramObject.getClass();
		Field[] fields = objectClass.getDeclaredFields();
		for (Field field : fields) {
			if(TenantInterceptor.TENANT_ID.equals(field.getName())){
				try {
					PropertyUtils.setSimpleProperty(paramObject, field.getName(), tenantId);
				} catch (Exception e) {
					log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,"TenantAspectForSpring setTenantToObjectContent:" + e.getMessage(),e));
				} 
			}
		}
	}

}

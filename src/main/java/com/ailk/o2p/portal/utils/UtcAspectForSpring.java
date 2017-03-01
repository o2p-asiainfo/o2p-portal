package com.ailk.o2p.portal.utils;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ailk.eaap.o2p.common.util.date.UTCConvertUtil;
import com.ailk.eaap.o2p.common.util.date.UTCTimeUtil.DateType;
import com.ailk.eaap.op2.common.EAAPConstants;
import com.asiainfo.foundation.log.Logger;

/**
 * @ClassName: UtcAspectForSpring
 * @Description: Spring拦截器，用于拦截的方法执行前，将本地时间转化成UTC时间，方法执行后将数据库的UTC时间转成本地时间
 * @author zhengpeng
 * @date 2014-10-21 下午3:42:48
 * @version V1.0
 */
public class UtcAspectForSpring {
	
	private static final Logger log = Logger.getLog(UtcAspectForSpring.class);
//	@Autowired
//	private HttpServletRequest request;
	
	/**
	 * 对conf工程里service包里的query方法进行拦截
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {

		//获取到本地时区
		if(RequestContextHolder.getRequestAttributes() != null){
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			log.debug("############## UtcAspectForSpring className:" + joinPoint.getTarget().getClass().getName() + " || method：" + method.getName());
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			int timeOffest = this.getTimeOffsetForSession(session);
			
			log.debug(" UtcAspectForSpring aroundMethod start execute class：{0}; method name:{1};timeOffest:{2}",joinPoint.getTarget(),joinPoint.getSignature().getName(),timeOffest);
			
			//对传入参数的本地时间进行处理,转成UTC时间
			Object[] args = joinPoint.getArgs();
			if(timeOffest !=  0 && args != null && args.length > 0){
				UTCConvertUtil.checkObject(args[0], timeOffest, DateType.LOCAL_DATE);
			}
			
			//执行dao包里的query方法
			Object result = joinPoint.proceed();
			
			//对获得结果为UTC时间进行处理,转成本地时间
			if (timeOffest != 0 && result != null) {
				UTCConvertUtil.checkObject(result, timeOffest, DateType.UTC_DATE);
			}
			return result;
		}else{
			return joinPoint.proceed();
		}
	}
	
	/**
	 * 从session里获取到时间差
	 * @param session
	 * @return
	 */
	private int getTimeOffsetForSession(HttpSession session){
		int timeOffest = 0;
		if(session != null && session.getAttribute(EAAPConstants.TIME_OFFSET) != null){
			timeOffest = Integer.valueOf(session.getAttribute(EAAPConstants.TIME_OFFSET).toString());
		}
		return timeOffest;
	}

}

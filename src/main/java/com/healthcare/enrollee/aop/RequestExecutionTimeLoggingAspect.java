package com.healthcare.enrollee.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.healthcare.enrollee.logging.LogManager;
import com.healthcare.enrollee.logging.LoggingLevel;

@Aspect
@Component
public class RequestExecutionTimeLoggingAspect {

	@Autowired
	private HttpServletRequest request;

	@Pointcut("execution(* com.healthcare.enrollee.controller.*.*(..))")
	private void controllerPointcut() {

	}
	
	@Around("controllerPointcut()")
	public Object logRequestExecutionTime(ProceedingJoinPoint jp) throws Throwable {
		StopWatch watch = new StopWatch();
		watch.start();
		Object response = jp.proceed();
		watch.stop();
		LogManager.writeLog(RequestExecutionTimeLoggingAspect.class, "Execution time for request URL: "
				+ request.getRequestURL() + " (" + request.getMethod() + ") is " + watch.getTotalTimeMillis() + "ms", LoggingLevel.INFO);
		return response;
	}
}

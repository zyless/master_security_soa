package com.fh.ssoa.covid.plattform.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class ExecutionTimeTrackerAdvice {
	// Measuring how many milliseconds a method needs by
	// by using TrackExecutionTime the monitoring can be applied be only using @TrackExecutionTime above a method
	@Around("@annotation(com.fh.ssoa.covid.plattform.aop.TrackExecutionTime)")
	public Object trackTime(ProceedingJoinPoint pjp) throws Throwable{
		long startTime=System.currentTimeMillis();
		Object obj=pjp.proceed();
		long endTime=System.currentTimeMillis();
		log.info("Method name " +pjp.getSignature() + " time taken to execute " + (endTime - startTime) + "ms");
		return obj;
	}
	
	
}

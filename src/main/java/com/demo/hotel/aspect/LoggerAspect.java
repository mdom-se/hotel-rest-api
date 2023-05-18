package com.demo.hotel.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private final Logger LOG = LoggerFactory.getLogger(LoggerAspect.class);

    @Around("execution(* com.demo.hotel.restcontroller.HotelRestController.*(..))")
    public Object logAroundHotelRestController(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info("HotelRestController >> {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        LOG.info("HotelRestController << {}", joinPoint.getSignature().getName());
        return result;
    }

    @Around("execution(* com.demo.hotel.restcontroller.AmenityRestController.*(..))")
    public Object logAroundAmenityRestController(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info("AmenityRestController >> {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        LOG.info("AmenityRestController << {}", joinPoint.getSignature().getName());
        return result;
    }

}

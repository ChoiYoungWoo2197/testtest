package com.uwo.isms.aspect;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Debugger {

    private org.apache.logging.log4j.Logger log = LogManager.getLogger(Debugger.class);

    @Before("within(com.uwo.isms.web..*)")
    private void controllerLogger(JoinPoint joinPoint) {
        printer("controller", joinPoint);
    }

    @Before("within(com.uwo.isms.service..*)")
    private void serviceLogger(JoinPoint joinPoint) {
        printer("service", joinPoint);
    }

    @Before("within(com.uwo.isms.dao..*)")
    private void daoLogger(JoinPoint joinPoint) {
        printer("dao", joinPoint);
    }

    private void printer(String type, JoinPoint joinPoint) {

        if (log.isDebugEnabled()) {

            log.debug("==========================================================================================");
            log.debug("=== " + type.toUpperCase() + " Class : " + joinPoint.getTarget().getClass().getName());
            log.debug("=== " + type.toUpperCase() + " Method : " + joinPoint.getSignature().getName());
            log.debug("=== " + type.toUpperCase() + " Arguments : " + StringUtils.join(joinPoint.getArgs(), ", "));
            log.debug("==========================================================================================");

        }

    }

}

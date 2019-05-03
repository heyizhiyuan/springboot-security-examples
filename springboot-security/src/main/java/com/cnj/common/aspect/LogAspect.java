package com.cnj.common.aspect;

import com.cnj.common.exception.BusinessException;
import com.cnj.common.i18n.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by cnj on 2019-2-24
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final ObjectMapper mapper;

    @Autowired
    MessageUtils messageUtils;

    @Autowired
    public LogAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Pointcut(value = "execution(public * com.cnj.*.*.*(..))")
    public void recordLog() {
    }

    @Pointcut("@annotation(com.cnj.common.aspect.Log)")
    private void cut() {
    }

    /**
     * 定制一个环绕通知
     *
     * @param joinPoint
     */
    @Around("cut()")
    public Object advice(ProceedingJoinPoint joinPoint) {
        try {
            StringBuffer param = new StringBuffer();
            for (Object object : joinPoint.getArgs()) {
                if (
                        object instanceof MultipartFile
                                || object instanceof HttpServletRequest
                                || object instanceof HttpServletResponse) {
                    continue;
                }
                param.append(mapper.writeValueAsString(object))
                        .append(",");
            }
            log.info(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()
                    + ":【parameter】: " + param.toString() );
            final Object proceed = joinPoint.proceed();
            log.info("【RETURN】"+proceed);
            return proceed;
        } catch (Throwable e) {
            return new BusinessException(messageUtils.getMessage("failed"));
        }
    }

    @Before("cut()")
    public void before() {
        log.info("已经记录下操作日志@Before 方法执行前");
    }

    @After(value = "recordLog()")
    public void after() {
        log.info("已经记录下操作日志@After 方法执行后");
    }

}


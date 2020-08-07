package cn.hui.vote.web.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import cn.hui.vote.common.exception.ApiErrorCode;
import cn.hui.vote.common.exception.BizException;
import cn.hui.vote.web.domain.BaseRequest;
import cn.hui.vote.web.domain.ResponseBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

@Component
@Aspect
public class HttpAuthAop {

    private static final Logger LOGGER              = LoggerFactory.getLogger(HttpAuthAop.class);

    private static final int    MAX_API_QPM         = 1200;

    @Pointcut("execution (public * cn.hui.vote.web.controller..*(..))")
    public void httpMethod() {
    }

    @Around("httpMethod()")
    public Object http(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        Object target = pjp.getTarget();

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        String simpleClassName = target.getClass().getSimpleName();
        String methodFullName = simpleClassName + "." + method.getName();

        Object[] args = pjp.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
        request.getRemoteAddr();

        LOGGER.info("Http request {} in, args: {}", methodFullName, Arrays.toString(args));

        Object response = null;

        try {
            response = pjp.proceed();
//        } catch (BizException e) {
//            response = handleException(e.getErrorCode(), e.getMessage());
//            LOGGER.warn("Http request {} fail, errCode: {}, errMsg: {}, args: {}, resp: {}, start: {}, time: {}",
//                methodFullName, e.getErrorCode(), e.getMessage(), Arrays.toString(args), JSON.toJSONString(response),
//                start, (System.currentTimeMillis() - start), e);
//        } catch (IllegalArgumentException e) {
//            response = handleException(ApiErrorCode.API_INPUT_PARAM_ERORR.getCode(), e.getMessage());
//            LOGGER.warn("Http request {} fail, args: {}, resp: {}", methodFullName, Arrays.toString(args),
//                JSON.toJSONString(response), e);
        } catch (Throwable t) {
//            response = handleException(ApiErrorCode.SYSTEM_ERROR);
            LOGGER.error("Http request {} error, args: {}, start: {}, proc time: {}", methodFullName, Arrays.toString(args),
                 start, (System.currentTimeMillis() - start), t);
            throw t;
        } finally {
            String argsString = Arrays.toString(args);
            LOGGER.info("Http request {} finish, args: {}, start: {}, proc time: {}", methodFullName, argsString,
                 start, (System.currentTimeMillis() - start));
        }

        return response;
    }

    private ResponseBean<?> handleException(int code, String msg) {
        ResponseBean<?> response = new ResponseBean<>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    private ResponseBean<?> handleException(ApiErrorCode errorCode) {
        return handleException(errorCode.getCode(), errorCode.getMsg());
    }

    private void checkToken(BaseRequest requestBean) {

    }

}

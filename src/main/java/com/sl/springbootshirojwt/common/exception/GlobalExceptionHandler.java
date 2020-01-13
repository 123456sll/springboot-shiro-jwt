package com.sl.springbootshirojwt.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.sl.springbootshirojwt.common.bean.ResultBean;
import com.sl.springbootshirojwt.common.enums.ResultCode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResultBean<JSONObject> handle(ValidationException exception) {
        JSONObject jsonObject = new JSONObject();
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            List<String> list = new ArrayList<>();
            for (ConstraintViolation<?> item : violations) {
                //打印验证不通过的信息
                list.add(item.getMessage());
                logger.error(item.getMessage());
            }
            jsonObject.put("error", list);
        }
        ResultBean<JSONObject> resultBean = new ResultBean<>(ResultCode.FAIL.getCode(), "非法参数！", jsonObject);
        return resultBean;
    }

    @ExceptionHandler(BindException.class)
    public ResultBean<JSONObject> handle(BindException exception) {
        JSONObject jsonObject = new JSONObject();
        logger.error(exception.getMessage());
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            jsonObject.put(error.getCode(), error.getDefaultMessage());
        }
        ResultBean<JSONObject> resultBean = new ResultBean<>(ResultCode.FAIL.getCode(), "非法参数！", jsonObject);
        return resultBean;
    }






}

package com.sl.springbootshirojwt.shiro;

import com.alibaba.fastjson.JSONObject;
import com.sl.springbootshirojwt.common.bean.ResultBean;
import com.sl.springbootshirojwt.common.enums.ResultCode;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShiroExceptionHandle {

    private static final Logger logger = LoggerFactory.getLogger(ShiroExceptionHandle.class);

    @ExceptionHandler(AuthorizationException.class)
    public ResultBean<JSONObject> handle(UnauthorizedException exception) {
        JSONObject jsonObject = new JSONObject();
        logger.error(exception.getMessage());
        ResultBean<JSONObject> resultBean = new ResultBean<>(ResultCode.FORBIDDEN.getCode(), "未经授权！", jsonObject);
        return resultBean;
    }

}

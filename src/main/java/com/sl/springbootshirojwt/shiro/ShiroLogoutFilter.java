package com.sl.springbootshirojwt.shiro;

import com.alibaba.fastjson.JSONObject;
import com.sl.springbootshirojwt.common.bean.ResultBean;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ShiroLogoutFilter extends LogoutFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroLogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        if (this.isPostOnlyLogout() && !WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals("POST")) {
            return this.onLogoutRequestNotAPost(request, response);
        } else {
            try {
                subject.logout();
            } catch (SessionException var6) {
                logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
            }
            ResultBean<JSONObject> resultBean = new ResultBean<>();
            HttpServletResponse httpServletResponse=(HttpServletResponse)response;
            httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(JSONObject.toJSON(resultBean).toString());
            return false;
        }
    }
}

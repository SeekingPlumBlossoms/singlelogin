package com.example.user.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 17611
 * @version 1.0
 * @className LoginFilter
 * @description 登录拦截器
 * @date 2019/4/4 10:26
 **/
@Configurable
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入拦截器了");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //获取session不自动创建
        HttpSession httpSession = httpServletRequest.getSession(false);
        String token = httpServletRequest.getParameter("token");
        if (httpSession == null && StringUtils.isBlank(token)) {
            System.out.println("未登录,跳转到登录页面");
            httpServletResponse.sendRedirect("http://www.cas.com:8081/index?url=http://www.user.com:8080/userInfo");
            return;
        }
        if (httpSession != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        System.out.println(httpServletRequest.getRequestURI());
        if (StringUtils.isNotBlank(token)) {
            System.out.println("获取token");
            httpServletResponse.sendRedirect("http://www.cas.com:8081/checkToken?url=http://www.user.com:8080/userInfo&token=" + token);
            return;
        }

    }
}

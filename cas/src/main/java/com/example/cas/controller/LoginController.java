package com.example.cas.controller;

import com.example.cas.utils.JavaWebTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 17611
 * @version 1.0
 * @className LoginController
 * @description 登录controller
 * @date 2019/4/4 11:10
 **/
@Controller
public class LoginController {

    @RequestMapping("index")
    public String index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        System.out.println("登陆页面");
        HttpSession session =httpServletRequest.getSession();
        String url = httpServletRequest.getParameter("url");
        String page=httpServletRequest.getParameter("page");
        String token= (String)session.getAttribute("token");
        if(StringUtils.isNotBlank(token)){
            try {
                httpServletResponse.sendRedirect(url+"?token="+token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("url", url);
        model.addAttribute("page", page);
        return "index";
    }

    @RequestMapping("login")
    public String login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        System.out.println("请求登陆");
        String userName = httpServletRequest.getParameter("userName");
        String password = httpServletRequest.getParameter("password");
        String url = httpServletRequest.getParameter("url");
    if(!"yebin".equals(userName) || !"123456".equals(password)){
            return "loginFail";
        }
        System.out.println("userName=" + userName + "::::password=" + password + ":::url=" + url);

        if (url == null || "".equals(url)) {
            model.addAttribute("userName", userName);
            return "loginSuccess";
        }
        String token = JavaWebTokenUtils.createTokenWithClaim(userName);
          httpServletRequest.getSession().setAttribute("token",token);
        try {
            httpServletResponse.sendRedirect(url + "?token=" + token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("userName", userName);
        return "loginSuccess";
    }

    @RequestMapping("checkToken")
    public String checkToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        System.out.println("登陆页面");
        String url = httpServletRequest.getParameter("url");
        String token = httpServletRequest.getParameter("token");
        System.out.println(httpServletRequest.getSession().getId());
        String userName = JavaWebTokenUtils.verifyToken(token);

        if (userName == null || userName.equals("")) {
            return "index";
        }
        try {
            if(url.contains("order")){
                httpServletResponse.sendRedirect("http://www.order.com:8082/createSession?token=" + token+"&url="+url);
            } else {
                httpServletResponse.sendRedirect("http://www.user.com:8080/createSession?token=" + token+"&url="+url);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("userName", userName);
        return "loginSuccess";
    }
}

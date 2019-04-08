package com.example.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * @author 17611
 * @version 1.0
 * @className UserController
 * @description 用户服务controller
 * @date 2019/4/4 9:50
 **/
@Controller
public class UserController {

    @RequestMapping("/userInfo")
    public String getUserInfo(Model model){
        System.out.println("进来了");

        return "userInfo";
    }
    @RequestMapping("/createSession")
    @ResponseBody
    public void createSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        httpServletRequest.getSession();
        System.out.println(httpServletRequest.getSession().getId());
        String url=httpServletRequest.getParameter("url");
        System.out.println(url);
        try {
            httpServletResponse.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.example.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 17611
 * @version 1.0
 * @className OrderController
 * @description 订单模块
 * @date 2019/4/8 10:33
 **/
@Controller
public class OrderController {

    @RequestMapping("/queryInfo")
    public String queryOrderInfo(){
        return "orderInfo";
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

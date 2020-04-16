package com.lagou.edu.demo.controller;

import com.lagou.edu.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;
import com.lagou.edu.mvcframework.annotations.LagouSecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LagouController
@LagouRequestMapping("/demo")
public class DemoController {
    @LagouAutowired
    private IDemoService demoService;

    /**
     * URL:/demo/query
     * @param request
     * @param response
     * @param name
     * @return
     */
    @LagouRequestMapping("/handle01")
 @LagouSecurity("wcd")
    public String query(HttpServletRequest request, HttpServletResponse response,String name) {
        return demoService.get(name);
    }
}

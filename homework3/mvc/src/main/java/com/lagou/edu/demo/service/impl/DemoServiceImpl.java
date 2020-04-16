package com.lagou.edu.demo.service.impl;

import com.lagou.edu.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouSecurity;
import com.lagou.edu.mvcframework.annotations.LagouService;

@LagouService("demoService")
@LagouSecurity
public class DemoServiceImpl implements IDemoService {
    @Override
    public String get(String name) {
        System.out.println("service 实现类中的那么参数"+name);
        return name;
    }
}

package com.lagou.edu.mvcframework.pojo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 存储封装handler相关信息
 */

public class Handler {
    private  Object controller;
    private Method method;
    private Pattern pattern;
    //参数顺序
    private Map<String,Integer> paramIdexMapping;

    public Handler(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        this.paramIdexMapping = new HashMap<>();
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Map<String, Integer> getParamIdexMapping() {
        return paramIdexMapping;
    }

    public void setParamIdexMapping(Map<String, Integer> paramIdexMapping) {
        this.paramIdexMapping = paramIdexMapping;
    }
}

package com.lagou.edu.mvcframework.pojo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 存储封装handler相关信息
 */

public class Handler {
    private Object controller;//method.invoke(obj,)

    private Object security; //method.invoke(obj,)

    public Handler(Object controller, Object security, Method method, Pattern pattern) {
        this.controller = controller;
        this.security = security;
        this.method = this.method;
        this.pattern = pattern;
        this.paramIndexMapping = new HashMap<>();
    }

    public Object getSecurity() {
        return security;
    }

    public void setSecurity(Object security) {
        this.security = security;
    }

    private Method method;

    private Pattern pattern;//spring中url是支持正则的

    private Map<String, Integer> paramIndexMapping;//参数顺序，是为了进行参数绑定，key是参数名，value代表是第几个参数<name,2>


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

    public Map<String, Integer> getParamIndexMapping() {
        return paramIndexMapping;
    }

    public void setParamIndexMapping(Map<String, Integer> paramIndexMapping) {
        this.paramIndexMapping = paramIndexMapping;
    }
}

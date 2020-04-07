package com.lagou.edu.factory;

import com.lagou.edu.common.annotation.Autowired;
import com.lagou.edu.common.annotation.Service;
import com.lagou.edu.common.annotation.Transactional;
import com.lagou.edu.factory.scanner.ClassPathScanner;
import com.lagou.edu.factory.scanner.Scanner;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class BeanFactory {



    private static Map<String,Object> map = new HashMap<>();  // 存储对象

    private static List<String[]> autowiredList = new LinkedList<>(); // 缓存autowired信息

    private static List<String> proxyList = new LinkedList<>();

    static {
        // 任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
        // 加载xml
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        // 解析xml
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            // 解析xml初始化BeanFactory
            initBeanFactoryByDoc(document);

            // 扫描注解初始化BeanFactory
            initBeanFactoryByAnnotation(document);

            // 清空缓存对象
            clearCache();

        } catch (DocumentException | ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public static  Object getBean(String id) {
        return map.get(id);
    }

    /**
     * 根据xml文件初始化BeanFactory
     */
    private static void initBeanFactoryByDoc(Document document) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Element rootElement = document.getRootElement();
        List<Element> beanList = rootElement.selectNodes("//bean");
        for (Element element : beanList) {
            // 处理每个bean元素，获取到该元素的id 和 class 属性
            String id = element.attributeValue("id");        // accountDao
            String clazz = element.attributeValue("class");  // com.lagou.edu.dao.impl.JdbcAccountDaoImpl
            // 通过反射技术实例化对象
            Class<?> aClass = Class.forName(clazz);
            Object o = aClass.getDeclaredConstructor().newInstance(); // 实例化之后的对象

            // 存储到map中待用
            map.put(id, o);
        }

        // 实例化完成之后维护对象的依赖关系，检查哪些对象需要传值进入，根据它的配置，我们传入相应的值
        // 有property子元素的bean就有传值需求
        List<Element> propertyList = rootElement.selectNodes("//property");
        // 解析property，获取父元素
        for (Element element : propertyList) {
            String name = element.attributeValue("name");
            String ref = element.attributeValue("ref");

            // 找到当前需要被处理依赖关系的bean
            Element parent = element.getParent();

            // 调用父元素对象的反射功能
            String parentId = parent.attributeValue("id");
            autowiredParams(parentId, name, ref);
        }
    }

    /**
     * 扫描注解初始化BeanFactory
     */
    private static void initBeanFactoryByAnnotation(Document document) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Element rootElement = document.getRootElement();
        Element scanNode = (Element) rootElement.selectSingleNode("//component-scan");

        String path = null;
        if (Objects.nonNull(scanNode)) {
            path = scanNode.attributeValue("base-package");
        }

        if (null != path && !"".equals(path)) {
            // 增加扫描注解逻辑
            Scanner scanner = new ClassPathScanner();
            List<String> clzList = scanner.scan(path);
            for (String clzName : clzList) {
                Class<?> aClass = Class.forName(clzName);
                initBeanByAnnotation(aClass);
            }
            // 维护依赖关系
            for (String[] arr : autowiredList) {
                autowiredParams(arr[0], arr[1], arr[2]);
            }
            // 替换对象为代理对象，依赖注入的方法不用加强，所以放在注入之后
            for (String id : proxyList) {
                Object obj = getProxyObjectIfNecessary(map.get(id));
                map.put(id, obj);
            }
        }

    }

    /**
     * 扫描注解初始化BeanFactory
     * 简单处理主键冲突 - 直接覆盖
     */
    private static void initBeanByAnnotation(Class<?> clz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Service service = clz.getAnnotation(Service.class);
        if (null != service) {
            String id = service.value();
            if ("".equals(id)) {
                id = getBeanNameByClz(clz);
            }
            Object obj = clz.getDeclaredConstructor().newInstance();

            // 中间对象保存进map待用，不做spring三级缓存
            map.put(id, obj);

            // 保存依赖维护关系
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                if (null != autowired) {
                    String ref = autowired.value();
                    if ("".equals(ref)) {
                        ref = getBeanNameByClz((field.getType()));
                    }
                    // 维护需要依赖关系的对象
                    autowiredList.add(new String[]{id, field.getName(), ref});
                }
            }

            // 保存代理关系
            if (null != clz.getAnnotation(Transactional.class)) {
                proxyList.add(id);
            }

        }
    }

    private static Object getProxyObjectIfNecessary(Object obj) {
        Class<?> clz = obj.getClass();
        Transactional transactional = clz.getAnnotation(Transactional.class);
        if (null != transactional) {
            ProxyFactory proxyFactory = (ProxyFactory) map.get("proxyFactory");
            Class<?>[] interfaces = clz.getInterfaces();
            // 实现了接口，使用jdk动态加载
            if (null != interfaces && interfaces.length > 0) {
                return proxyFactory.getJdkProxy(obj);
            }
            // 使用CGLib动态加载
            return proxyFactory.getCglibProxy(obj);
        }
        // 不需要代理
        return obj;
    }

    /**
     * 清空缓存
     */
    private static void clearCache() {
        autowiredList.clear();
        proxyList.clear();
    }

    /**
     * 依赖注入实现
     */
    private static void autowiredParams(String parentId, String name, String ref) throws InvocationTargetException, IllegalAccessException {
        Object parentObject = map.get(parentId);
        // 遍历父对象中的所有方法，找到"set" + name
        Method[] methods = parentObject.getClass().getMethods();
        for (Method method : methods) {
            if(method.getName().equalsIgnoreCase("set" + name)) {  // 该方法就是 setAccountDao(AccountDao accountDao)
                method.invoke(parentObject, map.get(ref));
                break;
            }
        }
    }

    /**
     * 根据类获取bean名称
     */
    private static String getBeanNameByClz(Class<?> clz) {
        return setFirstLower(clz.getSimpleName());
    }

    /**
     * 首字母小写
     */
    private static String setFirstLower(String str) {
        char first = str.charAt(0);
        if (Character.isLowerCase(first)) {
            return str;
        }
        return Character.toLowerCase(first) + str.substring(1);
    }

}

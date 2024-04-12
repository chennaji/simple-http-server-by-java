package cnj;

import cnj.Exception.UrlNotMatchException;
import cnj.examples.Test2Servlet;
import cnj.examples.TestServlet;

import java.util.Map;

public class ServetMapping {
    static Map<String, Class<?>> servletMap = new java.util.HashMap<>();
    static{
        //TODO: 通过反射扫描cnj.examples包下的类，找到@WebServlet注解的类，初始化servletMap
        //初始化servletMap
        servletMap.put("/test", TestServlet.class);
        servletMap.put("/test2", Test2Servlet.class);
    }
    public static Class<?> getServletClass(String url) throws UrlNotMatchException {
        //url无效则抛出错误
        if(!servletMap.containsKey(url)){
            throw new UrlNotMatchException("无效的url");
        }
        System.out.println("即将匹配的url为：" + url);
        System.out.println("匹配的servlet为：" + servletMap.get(url).getName());
        return servletMap.get(url);
    }
}

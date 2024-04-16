package cnj;

import cnj.Exception.UrlNotMatchException;
import cnj.examples.DefaultServlet;

import java.io.IOException;

public class ServletHandler implements UrlHandle {

    @Override
    public Response handleURL(Request request, String url) {
        Class<?> clazz = null;
        try {
            clazz = ServetMapping.getServletClass(url);
        } catch (UrlNotMatchException e) {
            clazz = DefaultServlet.class;
        }
        Object targetServlet = null;
        try {
            targetServlet = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //根据Servlet的执行结果，组装Response对象
        String method = request.getMethod();
        Response response = new Response(request.getSocket());
        switch (method) {
            case "GET": {
                try {
                    ((AbstractServlet) targetServlet).doGet(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "POST": {
                try {
                    ((AbstractServlet) targetServlet).doPost(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
                throw new RuntimeException("不支持的请求方法");
        }
        return response;
    }
}

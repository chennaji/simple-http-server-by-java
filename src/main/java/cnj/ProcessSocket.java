package cnj;

import cnj.Exception.UrlNotMatchException;
import cnj.examples.DefaultServlet;

import java.net.Socket;
public class ProcessSocket implements Runnable {
    private Socket socket;
    int socketId;

    public ProcessSocket(Socket socket, int socketId) {
        this.socket = socket;
        this.socketId = socketId;
    }

    @Override
    public void run() {
        System.out.println("获取到新http请求，socketId为" + socketId);
        /**
         * 整体逻辑：
         * 1.解析请求，实例化Request对象
         * 2.根据url，获取对应的Servlet
         * 3.根据Servlet的执行结果，组装Response对象
         * 4.发送响应
         */
        //1.解析请求，实例化Request对象
        Request request = new Request(this);
        request.parseRequest();
        System.out.println(request);

        //2.根据url，获取对应的Servlet
        String url = request.getUrl();
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

        //3.根据Servlet的执行结果，组装Response对象
        String method = request.getMethod();
        Response response = new Response(this);
        switch (method) {
            case "GET":
                ((AbstractServlet) targetServlet).doGet(request,response);
                break;
            case "POST":
                ((AbstractServlet) targetServlet).doPost(request,response);
                break;
            default:
                throw new RuntimeException("不支持的请求方法");
        }
        //4.发送响应
        response.sendResponse();
    }


    public Socket getSocket() {
        return socket;
    }

    public int getSocketId() {
        return socketId;
    }
}

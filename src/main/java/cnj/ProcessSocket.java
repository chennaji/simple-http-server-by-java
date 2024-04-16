package cnj;

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
         * 2.根据url，判断请求资源类型：html、servlet
         * 3.调用对应的处理器，处理请求
         * 4.发送响应
         */
        //1.解析请求，实例化Request对象
        Request request = new Request(this.socket);
        request.parseRequest();
        System.out.println(request);

        //2.根据url，判断请求资源类型：html、servlet
        String url = request.getUrl();
        ServetMapping.URL_TYPE urlType;
        if (url.endsWith(".html"))
            urlType = ServetMapping.URL_TYPE.HTML;
        else
            urlType = ServetMapping.URL_TYPE.SERVLET;
        Response response = null;
        switch (urlType) {
            //3.调用对应的处理器，处理请求
            case HTML: {
                //处理静态资源
                UrlHandle matcher = new HTMLHandler();
                response = matcher.handleURL(request,url);
                break;
            }
            case SERVLET: {
                //处理动态资源
                UrlHandle matcher = new ServletHandler();
                response = matcher.handleURL(request,url);
                break;
            }
        }

        //4.发送响应
        response.sendResponse();
        response.printResponse();
    }

    public Socket getSocket() {
        return socket;
    }

    public int getSocketId() {
        return socketId;
    }
}

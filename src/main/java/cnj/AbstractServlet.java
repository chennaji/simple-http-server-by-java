package cnj;

/**
 * 用户需要实现我的抽象类，实现doGet和doPost方法
 * 用户需要自己封装Response的必要参数：例如
 *              请求行：status、message
 *              请求头：Content-Type、Content-Length
 *              请求体：text？ html？ json？ xml？
 */
public abstract class AbstractServlet {
    private Request request;
    private Response response;
    abstract public void doGet(Request request, Response response);
    abstract public void doPost(Request request, Response response);
}

package cnj.examples;

import cnj.AbstractServlet;
import cnj.Request;
import cnj.Response;

public class DefaultServlet extends AbstractServlet {

    @Override
    public void doGet(Request request, Response response) {
        System.out.println("也许是哪里错了，访问了DefaultServlet");
    }

    @Override
    public void doPost(Request request, Response response) {
        System.out.println("也许是哪里错了，访问了DefaultServlet");
    }
}

package cnj.examples;

import cnj.AbstractServlet;
import cnj.Request;
import cnj.Response;
import cnj.annotation.WebServlet;

@WebServlet("/test")
public class TestServlet extends AbstractServlet {

    @Override
    public void doGet(Request request, Response response) {
        System.out.println("TestServet方法执行：doGet");
    }

    @Override
    public void doPost(Request request, Response response) {
        System.out.println("TestServet方法执行：doPost");
    }
}

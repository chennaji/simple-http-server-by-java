package cnj.examples;

import cnj.AbstractServlet;
import cnj.Request;
import cnj.Response;
import cnj.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends AbstractServlet {

    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.setStatus(200);
        response.addHeader("Content-Type", "text/html;charset=UTF-8");
        response.setBody("<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "hi,cnj!\n" +
                "</body>\n" +
                "</html>");
    }

    @Override
    public void doPost(Request request, Response response) {
        System.out.println("TestServet方法执行：doPost");
    }
}

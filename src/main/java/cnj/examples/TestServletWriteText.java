package cnj.examples;

import cnj.AbstractServlet;
import cnj.Request;
import cnj.Response;
import cnj.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/testWriteText")
public class TestServletWriteText extends AbstractServlet {


    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.setStatus(200);
        response.addHeader("Content-Type", "text/plain;charset=UTF-8");
        response.write("hi,cnj,you can write plain text!");

    }

    @Override
    public void doPost(Request request, Response response) {
    }
}

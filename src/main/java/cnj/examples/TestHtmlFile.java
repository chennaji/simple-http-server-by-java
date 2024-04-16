package cnj.examples;

import cnj.AbstractServlet;
import cnj.Request;
import cnj.Response;
import cnj.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/testHtmlFile")
public class TestHtmlFile extends AbstractServlet {

    @Override
    public void doGet(Request request, Response response) throws IOException {

    }

    @Override
    public void doPost(Request request, Response response) throws IOException {

    }
}

package cnj.examples;

import cnj.AbstractServlet;
import cnj.Request;
import cnj.Response;
import cnj.annotation.WebServlet;

@WebServlet("/test2")
public class Test2Servlet extends AbstractServlet {

        @Override
        public void doGet(Request request, Response response) {
        }

        @Override
        public void doPost(Request request, Response response) {
        }
}

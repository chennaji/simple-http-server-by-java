package cnj;

import cnj.Exception.UrlNotMatchException;
import cnj.annotation.WebServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Stack;

public class HTMLHandler implements UrlHandle {
    @Override
    public Response handleURL(Request request, String url) throws UrlNotMatchException {
        //TODO:根据url，读取对应的html文件内容，组装Response对象
        System.out.println(url);
        //处理url格式成xxx.html
        String htmlName = url.substring(url.lastIndexOf("/") + 1);
        //找到webapps的路径
        String workPath = System.getProperty("user.dir");
        String webappsPath = workPath + File.separator + "src" + File.separator + "main" + File.separator + "webapps";
        System.out.println(webappsPath);
        Stack<File> dirs = new Stack<>();
        dirs.push(new File(webappsPath));
        while (!dirs.isEmpty()) {
            File curDir = dirs.pop();
            File[] files = curDir.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    dirs.push(f);
                } else if (f.isFile() && htmlName.equals(f.getName())) {
                    System.out.println("in");
                    return createResponse(request.getSocket(), f);
                }
            }
        }
        throw new UrlNotMatchException("未知html");
    }

    private Response createResponse(Socket s, File f) {
        Response response = new Response(s);
        //设置返回的Response的必要信息
        response.setStatus(200);
        response.setMessage("OK");
        response.addHeader("Content-Type", "text/html");
//        response.addHeader("Content-Length", String.valueOf(htmlPath.length()));
        System.out.println(response);
        //写入数据流
        int byteRead = 0;
        //把该文件读入为一个String
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[8096];
            fis.read(buffer);
            for (byte b : buffer) {
                if(b == 0){
                    break;
                }
                char c = (char) b;
                sb.append(c);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.write(sb.toString());
        return response;
    }
}

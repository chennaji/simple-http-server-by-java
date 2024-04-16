package cnj;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class Response {
    private Socket socket;
    private String version;
    private int status;
    private String message;
    private Map<String,String> headers = new java.util.HashMap<>();
    private StringBuilder body;
    /**
     * 已完成组装的响应行
     */
    private String responseLine;
    /**
     * 已完成组装的响应头
     */
    private String responseHeader;
    /**
     * 已完成组装的响应体
     */
    private String responseBody;

    public Response(Socket socket){
        this.socket = socket;
        initializeResponse();
    }

    private void parseResponseLine(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(version);
        stringBuilder.append(status + " ");
        stringBuilder.append(message);
        stringBuilder.append("\r\n");
        this.responseLine = stringBuilder.toString();
    }
    private void parseResponseHeader(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\r\n");
        }
        stringBuilder.append("\r\n");//头部结束空一行
        this.responseHeader = stringBuilder.toString();
    }
    private void parseResponseBody(){
        this.responseBody = body.toString();
    }
    public void printResponse(){
        System.out.println("Response:{");
        System.out.println(responseLine + responseHeader + responseBody + "}");
    }

    public void initializeResponse() {
        this.version = "HTTP/1.1 ";
        this.status = 200;
        this.message = "OK";
        //设置浏览器必须检查的头部默认值
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "text/plain; charset=utf-8");//其他格式：text/html、application/json、image/jpeg
        headers.put("Server", "cnj");
        this.body = new StringBuilder();
    }
    public void sendResponse(){
        parseResponseLine();
        parseResponseHeader();
        parseResponseBody();
        String response = responseLine + responseHeader + responseBody;
        try {
            socket.getOutputStream().write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void setStatus(int status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * 外部调用设置响应行
     * @param key
     * @param value
     */
    public void addHeader(String key, String value){
        headers.put(key, value);
    }
    public void write(String body){
        this.body.append(body);
        //如果是text/plain，需要计算Content-Length
        if(headers.get("Content-Type").equals("text/plain;charset=UTF-8")){
            //如果已经有Content-Length，需要加上原来的长度
            if (headers.containsKey("Content-Length")) {
                int prevLength = Integer.parseInt(headers.get("Content-Length"));
                int newLength = prevLength + body.length();
                headers.put("Content-Length", String.valueOf(newLength));
            } else {
                int newLength = body.length();
                headers.put("Content-Length", String.valueOf(newLength));
            }
        }
    }
    public Socket getSocket() {
        return socket;
    }
}

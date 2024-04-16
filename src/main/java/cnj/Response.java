package cnj;

import java.io.IOException;
import java.util.Map;

public class Response {
    private String version;
    private int status;
    private String message;
    private Map<String,String> headers = new java.util.HashMap<>();
    //TODO: 为response添加更多的属性，和set方法，以供Servet中调用
    private String body;
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

    private ProcessSocket socket;

    public Response(ProcessSocket socket){
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
        this.responseBody = body;
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
        this.body = "";
    }
    public void sendResponse(){
        parseResponseLine();
        parseResponseHeader();
        parseResponseBody();
        String response = responseLine + responseHeader + responseBody;
        try {
            socket.getSocket().getOutputStream().write(response.getBytes());
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
    public void setBody(String body) {
        this.body = body;
    }
}

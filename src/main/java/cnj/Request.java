package cnj;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Request {
    //传入请求的Socket
    private ProcessSocket socket;
    private byte[] rawRequestData = new byte[1460];
    //请求行
    private String method;
    private String url;
    private String version;
    //请求头
    private Map<String, String> headers = new java.util.HashMap<>();
    //请求体
    private String body;//post数据在请求体中
    private Map<String,String> params = new java.util.HashMap<>();//get数据在url中
    public Request(ProcessSocket socket) {
        this.socket = socket;
        readData();
    }
    private void readData() {
        try {
            InputStream inputStream = socket.getSocket().getInputStream();
            //读取请求数据
            inputStream.read(this.rawRequestData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void parseRequest() {
        //双指针解析请求
        Integer end = 0;
        //解析请求行
        for (int i = 0; i < 3; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            while ((char) rawRequestData[end] != ' ' && (char) rawRequestData[end] != '\r') {
                stringBuilder.append((char) rawRequestData[end]);
                end++;
            }
            if (i == 0) {
                    this.method = stringBuilder.toString();
                    stringBuilder.setLength(0);
                    end++;
                } else if (i == 1) {
                    this.url = stringBuilder.toString();
                    stringBuilder.setLength(0);
                    end++;
                } else if (i == 2) {
                    this.version = stringBuilder.toString();
            }
        }
        //解析请求头
        if(rawRequestData[end] == '\r' && rawRequestData[end + 1] == '\n') {
            end += 2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while(true) {
            while ((char) rawRequestData[end] != ':') {
                stringBuilder.append((char) rawRequestData[end]);
                end++;
            }
            //冒号前为key
            String key = stringBuilder.toString();
            stringBuilder.setLength(0);
            end +=2;//冒号后有一个空格
            while ((char) rawRequestData[end] != '\r') {
                stringBuilder.append((char) rawRequestData[end]);
                end++;
            }
            //冒号后为value
            String value = stringBuilder.toString();
            stringBuilder.setLength(0);
            headers.put(key, value);
            if(rawRequestData[end] == '\r' && rawRequestData[end + 1] == '\n' && rawRequestData[end + 2] == '\r' && rawRequestData[end + 3] == '\n') {
                end += 4;
                break; //连续两个回车换行,请求头结束
            }else {
                end += 2; //否则继续解析下一行请求头
            }
        }
        //解析请求体
    }
    public void printRawSocketData() {
        //打印Socket ID
        System.out.println("Socket ID: " + socket.getSocketId());
        for (byte b : rawRequestData) {
            System.out.print((char) b);
        }
        System.out.println();
        System.out.println("--------------------");
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getBody() {
        return body;
    }
    public String getHeader(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        return  "http数据包解析结果：\n" +
                "Request{" +
                "\n\tmethod='" + method + '\'' +
                ", \n\turl='" + url + '\'' +
                ", \n\tversion='" + version + '\'' +
                ", \n\theaders=" + headers +
                ", \n\tbody='" + body + '\'' +
                "\n}";
    }
}

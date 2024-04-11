package cnj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tomcat {
    public static void main(String[] args) {
	//启动tomcat
        new Tomcat().start();
    }

    private void start() {
        //开始监听端口
        try {
            ServerSocket serverSocket = new ServerSocket(8088);
            //创建线程池
            ExecutorService pool = Executors.newFixedThreadPool(10);
            int socketId = 0;
            while (true) {
                Socket socket = serverSocket.accept();
                pool.execute(new ProcessSocket(socket, socketId));
                socketId++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

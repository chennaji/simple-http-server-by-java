package cnj;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
public class ProcessSocket implements Runnable {
    private Socket socket;
    int socketId;

    public ProcessSocket(Socket socket, int socketId) {
        this.socket = socket;
        this.socketId = socketId;
    }

    @Override
    public void run() {
        try {
            //打印Socket ID
            System.out.println("Socket ID: " + socketId);
            //读取Socket数据
            InputStream inputStream = socket.getInputStream();

            inputStream = socket.getInputStream();
            byte[] bytes = new byte[300];
            //打印测试
            inputStream.read(bytes);
            for (byte b : bytes) {
                System.out.print((char) b);
            }
            System.out.println();
            System.out.println("--------------------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

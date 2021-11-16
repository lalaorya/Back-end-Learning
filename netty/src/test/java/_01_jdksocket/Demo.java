package _01_jdksocket;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author virtual
 * @Date 2021/11/15 22:28
 * @description：使用原生JDK实现socket网络编程
 */

public class Demo {

    /**创建一个socket客户端，写入信息到服务器，并从服务器读取响应
     * @throws IOException
     */
    @Test
    public void client() throws IOException {
        // 创建一个socket对象
        Socket client = new Socket("127.0.0.1",9000);
        // 每个socket都有输入流对象和输出流对象，用于从socket从读取和写入信息
        PrintWriter printWriter = new PrintWriter(client.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                client.getInputStream()
        ));
        int count=0;
        char chars[] = new char[1024];
        int len;
        while (count<10){
            printWriter.write("hello "+ count++);
//            printWriter.flush();
            StringBuilder builder = new StringBuilder();
            while ((len=reader.read(chars)) != -1) {
                builder.append(new String(chars, 0, len));
            }
            System.out.println("接收到："+builder.toString());
        }
        printWriter.close();
        client.close();
    }

    @Test
    public void server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        Socket server = serverSocket.accept();
        Reader reader = new InputStreamReader(server.getInputStream());
        PrintStream writer = new PrintStream(server.getOutputStream());
        char chars[] = new char[1024];
        int len;
//        while (true){
            StringBuilder builder = new StringBuilder();
            while ((len=reader.read(chars)) != -1) {
                builder.append(new String(chars, 0, len));
            }
            System.out.println("Receive from client message=: " + builder);
            writer.write(builder.toString().getBytes());

//        }


    }
}

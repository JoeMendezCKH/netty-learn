package com.joe.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO 测试, 使用 telnet 作为 Client 连接
 * <p>
 * 当并发数较大时,需要创建大量线程来处理连接,系统资源占用较大
 * 连接建立后,如果当前线程暂时没有数据可读,则线程就阻塞在 Read 操作上,造成线程资源浪费
 *
 * @author ckh
 * @create 10/22/20 9:29 AM
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("server start ... ");

        while (true) {
            // listener, hold on client connect
            // accept() : The method blocks until a connection is made.
            final Socket socket = serverSocket.accept();
            System.out.println("connect a client...");
            threadPool.execute(() -> handler(socket));
        }
    }

    /**
     * connect with client
     *
     * @param socket socket 连接
     */
    public static void handler(Socket socket) {
        try {
            System.out.println("thread id:" + Thread.currentThread().getId() + " \t" + Thread.currentThread().getName() + " connected");
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();

            // read() : This method blocks until input data is available, end of file is detected, or an exception is thrown.
            int read = inputStream.read(bytes);
            while (read != -1) {
                System.out.print("thread id: " + Thread.currentThread().getId() + " \t" + Thread.currentThread().getName() + " : ");
                System.out.println(new String(bytes, 0, read));
                read = inputStream.read(bytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("closed connection");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

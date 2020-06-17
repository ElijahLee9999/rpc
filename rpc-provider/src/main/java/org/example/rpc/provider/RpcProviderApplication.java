package org.example.rpc.provider;

import org.example.rpc.api.Hello;
import org.example.rpc.api.IHelloService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Elijah
 * @create 2020-06-16 15:24
 */
public class RpcProviderApplication {

    private static ExecutorService cachedThreadPool = new ThreadPoolExecutor(4, 8, 3,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(8),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    private static final int port = 18888;


    public static void main(String[] args) {
        //注册服务
        ClassRegister.register(IHelloService.class, HelloServiceImpl.class);
        Socket socket = null;
        try {
            //1.Socket绑定本地端口
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Bio rpc 服务启动开始###端口###" + port);
            //2.监听端口
            while (true){
                //阻塞监听
                socket = serverSocket.accept();
                cachedThreadPool.execute(new RequireHandler(socket.getInputStream(), socket.getOutputStream()));
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {

        }
    }
}

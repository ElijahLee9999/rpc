package org.example.rpc.provider;

import org.example.rpc.api.Hello;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Elijah
 * @create 2020-06-16 15:24
 */
public class RpcProviderApplication {
    public static void main(String[] args) {
        try {
            //1.Socket绑定本地端口
            ServerSocket serverSocket = new ServerSocket(8888);
            //2.监听端口
            while (true){
                //阻塞监听
                Socket socket = serverSocket.accept();
                //1.接收所有的参数
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String clazzName = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class[] paramTypes = (Class[]) inputStream.readObject();
                Object[] args4Method = (Object[]) inputStream.readObject();
                Class clazz = null;
                //2.服务注册，找到具体的实现类
                if (clazzName.equals(Hello.class.getName())){
                    clazz = Hello.class;
                }
                //3.执行UserServiceImpl的方法
                Method method = clazz.getMethod(methodName,paramTypes);
                Object invoke = method.invoke(clazz.newInstance(),args4Method);

                //4.返回结果给客户端
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(invoke);
                outputStream.flush();

                //5.关闭连接
                outputStream.close();
                inputStream.close();

                socket.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

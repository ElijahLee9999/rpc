package org.example.rpc.provider;

import org.example.rpc.api.Hello;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * @author Elijah
 * @create 2020-06-16 15:33
 */
public class RequireHandler implements Runnable {
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public RequireHandler (ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try {
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
            outputStream.writeObject(invoke);
            outputStream.flush();

            //5.关闭连接
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

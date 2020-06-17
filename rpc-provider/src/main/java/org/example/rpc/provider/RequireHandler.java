package org.example.rpc.provider;

import org.example.rpc.api.Hello;
import org.example.rpc.api.RpcModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * @author Elijah
 * @create 2020-06-16 15:33
 */
public class RequireHandler implements Runnable {
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public RequireHandler(InputStream inputStream, OutputStream outputStream) throws IOException {
        this.inputStream = new ObjectInputStream(inputStream);
        this.outputStream = new ObjectOutputStream(outputStream);
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            RpcModel rpcModel = (RpcModel) inputStream.readObject();
            String clazzName = rpcModel.getClazzName();
            String methodName = rpcModel.getMethodName();
            Class<?>[] paramTypes = rpcModel.getParamTypes();
            Object[] args4Method = rpcModel.getArgs4Method();
            //2.服务注册，找到具体的实现类
            Class clazz = ClassRegister.getClass(clazzName);
            if (null == clazz) {
                throw new ClassNotFoundException(clazzName + " not found");
            }
            //3.执行UserServiceImpl的方法
            Method method = clazz.getMethod(methodName,paramTypes);
            Object invoke = method.invoke(clazz.newInstance(),args4Method);

            //4.返回结果给客户端
            outputStream.writeObject(invoke);
            outputStream.flush();
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

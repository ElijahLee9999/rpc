package org.example.rpc.consumer;

import org.example.rpc.api.RpcModel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Elijah
 * @create 2020-06-17 15:22
 */
public class InvokeHandler<T> implements InvocationHandler {
    private String host;
    private int port;
    private Class<T> clazz;

    public InvokeHandler(Class<T> clazz, String host, int port) {
        this.host = host;
        this.port = port;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            //1.建立远程连接
            socket = new Socket(host, port);
            //2.要调用的类、方法、参数
            String clazzName = clazz.getName();
            String methodName = method.getName();
            //为了鉴别方法的重载，这里需要传入参数类型
            Class<?>[] paramTypes = method.getParameterTypes();
            RpcModel rpcModel = new RpcModel();
            rpcModel.setArgs4Method(args);
            rpcModel.setClazzName(clazzName);
            rpcModel.setMethodName(methodName);
            rpcModel.setParamTypes(paramTypes);

            //3.传输类信息，请求远程执行结果
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(rpcModel);
            outputStream.flush();

            //4.接收返回的结果
            inputStream = new ObjectInputStream(socket.getInputStream());
            Object object = inputStream.readObject();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != socket) {
                socket.close();
            }
            if (null != outputStream) {
                outputStream.close();
            }
            if (null != inputStream) {
                inputStream.close();
            }
        }
    }
}

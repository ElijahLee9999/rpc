package org.example.rpc.consumer;

import java.lang.reflect.Proxy;

/**
 * @author Elijah
 * @create 2020-06-17 11:12
 */
public class ProxyHandler {

    public static <T> T proxy(Class<T> clazz, String host, int port) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new InvokeHandler<T>(clazz, host, port));
    }
}

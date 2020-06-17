package org.example.rpc.consumer;

import org.example.rpc.api.Hello;
import org.example.rpc.api.IHelloService;

/**
 * @author Elijah
 * @create 2020-06-17 11:06
 */
public class RpcConsumerApplication {
    private static final String host = "localhost";
    private static final int port = 18888;

    public static void main(String[] args) {
        IHelloService iHelloService = ProxyHandler.proxy(IHelloService.class, host, port);
        Hello hello = iHelloService.sayHello("Hi, Elijah");
        System.out.println(hello.getName());
        System.out.println(hello.getAge());
    }
}

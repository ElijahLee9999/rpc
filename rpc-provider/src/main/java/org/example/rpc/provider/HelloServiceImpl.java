package org.example.rpc.provider;

import org.example.rpc.api.Hello;
import org.example.rpc.api.IHelloService;

import java.io.Serializable;

/**
 * @author Elijah
 * @create 2020-06-16 14:49
 */
public class HelloServiceImpl implements IHelloService, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Hello sayHello(String text) {
        System.out.println(String.format("我是生产者，收到信息=%s", text));
        Hello hello = new Hello();
        hello.setAge(18);
        hello.setName("Elijah");
        return hello;
    }
}

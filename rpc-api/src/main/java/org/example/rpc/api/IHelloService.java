package org.example.rpc.api;

/**
 * @author Elijah
 * @create 2020-06-15 10:14
 */
public interface IHelloService {

    /**
     * sayHello
     * @param text String
     * @return String
     */
    Hello sayHello(String text);
}

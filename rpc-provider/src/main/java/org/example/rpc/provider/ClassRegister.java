package org.example.rpc.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Elijah
 * @create 2020-06-17 13:54
 */
public class ClassRegister {
    private static Map<String, Class> classMap = new HashMap<>();

    public static void register(Class interfaceName, Class impl) {
        System.out.println(String.format("注册服务:接口（%s），实现（%s）", interfaceName.getName(), impl.getName()));
        classMap.put(interfaceName.getName(), impl);
    }

    public static Class getClass (String className) {
        return classMap.get(className);
    }
}

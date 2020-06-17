package org.example.rpc.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Elijah
 * @create 2020-06-17 15:54
 */
@Data
public class RpcModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clazzName;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] args4Method;
}

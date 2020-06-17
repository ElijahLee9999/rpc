package org.example.rpc.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Elijah
 * @create 2020-06-16 15:00
 */
@Data
public class Hello implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer age;
}

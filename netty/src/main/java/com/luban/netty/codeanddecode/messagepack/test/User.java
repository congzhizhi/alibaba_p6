package com.luban.netty.codeanddecode.messagepack.test;

import lombok.Data;
import org.msgpack.annotation.Message;

@Data
@Message
public class User  {
    private int age;
    private String userName;
}

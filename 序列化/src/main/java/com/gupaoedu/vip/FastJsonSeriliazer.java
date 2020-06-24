package com.gupaoedu.vip;

import com.alibaba.fastjson.JSON;

/**
 *  User user=new User();
 *  user.setAge(18);
 *  user.setName("Mic");
 *  // 序列化后为23个字节
 */
public class FastJsonSeriliazer implements ISerializer{
    public <T> byte[] serialize(T obj) {

        return JSON.toJSONString(obj).getBytes();
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T)JSON.parseObject(new String(data),clazz);
    }
}

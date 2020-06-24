package com.gupaoedu.vip;

import java.io.*;

/**
 *  User user=new User();
 *  user.setAge(18);
 *  user.setName("Mic");
 *  // 序列化后的为92字节
 */
public class JavaSerializer implements ISerializer{


    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream=
                new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream=
                    new ObjectOutputStream(byteArrayOutputStream);

            outputStream.writeObject(obj);

            return  byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(data);
        try {
            ObjectInputStream objectInputStream=
                    new ObjectInputStream(byteArrayInputStream);

            return (T) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

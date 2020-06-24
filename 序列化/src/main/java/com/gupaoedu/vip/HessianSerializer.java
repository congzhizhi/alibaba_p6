package com.gupaoedu.vip;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User user=new User();
 * user.setAge(18);
 * user.setName("Mic");
 * //序列化后为50字节
 */
public class HessianSerializer implements ISerializer{
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        HessianOutput hessianOutput=new HessianOutput(outputStream);
        try {
            hessianOutput.writeObject(obj);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream inputStream=new ByteArrayInputStream(data);
        HessianInput hessianInput=new HessianInput(inputStream);
        try {
            return (T)hessianInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

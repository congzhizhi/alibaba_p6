package com.gupaoedu.vip;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * User user=new User();
 * user.setAge(18);
 * user.setName("Mic");
 * // 序列化后为221字节
 */
public class XStreamSerializer implements ISerializer{

    XStream xStream=new XStream(new DomDriver());

    public <T> byte[] serialize(T obj) {

        return xStream.toXML(obj).getBytes();
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T)xStream.fromXML(new String(data));
    }
}

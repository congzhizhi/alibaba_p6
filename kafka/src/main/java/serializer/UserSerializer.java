package serializer;

import bean.User;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

//自定义序列化器
public class UserSerializer implements Serializer<User> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, User user) {
        if (user == null) {
            return null;
        }
        byte[] name;
        int age = user.getAge();

        try {
            if (user.getName() != null) {
                name = user.getName().getBytes("UTF-8");
            } else {
                name = new byte[0];
            }
            //数组总共的长度
            ByteBuffer byteBuffer = ByteBuffer.allocate(4+4+name.length);
            //name字节数
            byteBuffer.putInt(name.length);
            //放name字节数组
            byteBuffer.put(name);
            //放age,age本身就是int类型的
            byteBuffer.putInt(age);
            return byteBuffer.array();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }


    @Override
    public void close() {

    }

}

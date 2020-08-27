package derializer;

import bean.User;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class UserDeserializer implements Deserializer<User> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public User deserialize(String s, byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        int nameLength = byteBuffer.getInt();
        byte name[] = new byte[nameLength];
        byteBuffer.get(name,0,nameLength);
        int age = byteBuffer.getInt();

        return new User().setAge(age).setName(new String(name));
    }

    @Override
    public void close() {

    }
}

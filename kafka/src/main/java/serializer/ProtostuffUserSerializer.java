package serializer;

import bean.User;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ProtostuffUserSerializer implements Serializer<User> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, User user) {
        if (user == null){
            return null;
        }
        Schema schema = RuntimeSchema.getSchema(user.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] protostuff = null;
        protostuff = ProtostuffIOUtil.toByteArray(user,schema,buffer);
        buffer.clear();
        return protostuff;
    }

    @Override
    public void close() {

    }
}

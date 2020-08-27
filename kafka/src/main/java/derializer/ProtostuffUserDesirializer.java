package derializer;

import bean.User;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ProtostuffUserDesirializer implements Deserializer<User> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public User deserialize(String s, byte[] bytes) {

        if (bytes == null) {
            return null;
        }
        Schema schema = RuntimeSchema.getSchema(User.class);
        User user = new User();
        ProtostuffIOUtil.mergeFrom(bytes, user, schema);
        return user;
    }

    @Override
    public void close() {

    }
}

package consumer;

import bean.User;
import derializer.ProtostuffUserDesirializer;
import derializer.UserDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class UserConsumer {
    public static final String brokerList = "192.168.0.138:9092";
    public static final String topic = "topic-demo";
    public static final String group = "group-id-user";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers",brokerList);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProtostuffUserDesirializer.class.getName());
        properties.put("group.id",group);
        KafkaConsumer<String, User> consumer = new KafkaConsumer<String, User>(properties);
        consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(1000));
            records.forEach(record->{
                System.out.println(record.value().getName()+":"+record.value().getAge());
            });
        }

    }
}

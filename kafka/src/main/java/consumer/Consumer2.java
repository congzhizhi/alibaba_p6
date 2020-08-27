package consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer2 {
    public static final String brokerList = "192.168.0.138:9092";
    public static final String topic = "topic-demo";
    public static final String group = "group-id3";
    public static final String client = "client-id2";

    public  static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static Properties initConfig(){

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,group);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,client);
        return  properties;
    }
    public static void main(String[] args) {

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(initConfig());
        consumer.subscribe(Collections.singletonList(topic));
        try {
            while (isRunning.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                records.forEach(record->{
                    System.out.println("topic="+record.topic()+",  partition="+record.partition()+",  offset="+record.offset());
                    System.out.println("key="+record.key()+", value="+record.value());
                    System.out.println("_______________________________");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}

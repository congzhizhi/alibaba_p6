import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaConsumerTest {



    public static final String bokerList = "192.168.0.138:9092";
    public static final String topic = "topic-demo";

    public static Properties initConfig(){
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bokerList);
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());//kafka接收的消息必須是以字節數組的形式存在
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"producer.client.id.demo");//客戶端ID
        return prop;
    }

    public static void main( String[] args )
    {

        Properties prop = initConfig();
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(prop);//KafkaProducer是線程安全的
        consumer.subscribe(Collections.singletonList(topic));
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
            }

        }

    }

}

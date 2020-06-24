import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProducerTest {

    public static final String bokerList = "192.168.0.138:9092";
    public static final String topic = "topic-demo";

    public static Properties initConfig(){
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bokerList);
        prop.put(ProducerConfig.RETRIES_CONFIG,1);//重試，包括網絡異常或者leader失效異常等
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());//kafka接收的消息必須是以字節數組的形式存在
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        prop.put(ProducerConfig.CLIENT_ID_CONFIG,"producer.client.id.demo");//客戶端ID
        return prop;
    }

    public static void main( String[] args )
    {

        Properties prop = initConfig();
        KafkaProducer<String,String> producer = new KafkaProducer<String,String>(prop);//KafkaProducer是線程安全的
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,"hello,kafka!");
        try {
            Future<RecordMetadata> metadataFuture = producer.send(record,(recordMetadata,ex)->{
                if (ex != null){
                    System.out.println("出現異常啦");
                    ex.printStackTrace();
                }else{
                     System.out.println("topic:"+recordMetadata.topic()+"\r\n"+"offset:"+recordMetadata.offset()+"\r\n"+"partition:"+recordMetadata.partition());
                }
            });
//            Future<RecordMetadata> metadataFuture = producer.send(record);
//            RecordMetadata metadata = metadataFuture.get();
//            System.out.println("topic:"+metadata.topic()+"\r\n"+"offset:"+metadata.offset()+"\r\n"+"partition:"+metadata.partition());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            producer.close();//close方法阻塞等待之前所有的发送请求完成后再关闭。
        }

    }

}

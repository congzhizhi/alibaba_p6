package producer;

import bean.User;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import serializer.ProtostuffUserSerializer;
import serializer.UserSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserProducer {
//        public static final String brokerList = "192.168.0.18:9092,192.168.0.19:9092";//集群模式
        public static final String brokerList = "192.168.0.138:9092";
        public static final String topic = "topic-demo";

        public static Properties initConfig(){
            Properties properties = new Properties();
            properties.put("bootstrap.servers",brokerList);
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProtostuffUserSerializer.class.getName());
            properties.put("client.id","producer.client.id.demo");
            return properties;
        }

        //同步发送
        public  static void sendSyn(ProducerRecord<String, User> record, KafkaProducer<String, User> producer){
            Future<RecordMetadata> retu =  producer.send(record);//send方法本身是异步的
            RecordMetadata recordMetadata = null;
            try {
                recordMetadata = retu.get();//实现同步发送
                System.out.println("同步发送成功到:"+recordMetadata.topic());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) throws InterruptedException {

            //KafkaProducer是线程安全的，可以池化来供其他线程调用
            KafkaProducer<String, User> producer = new KafkaProducer<>(initConfig());
            User user = new User().setName("conghzihi").setAge(21);
            ProducerRecord<String, User> record = new ProducerRecord<>(topic, user);
            sendSyn(record,producer);



            //阻塞等待之前所有的发送请求完成后再关闭
            producer.close();

        }
}

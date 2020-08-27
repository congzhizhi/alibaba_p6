package thread;

import interceptor.ConsumerInterceptorTTL;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class MultiConsumerThreadDemo {
    public static final String brokerList = "192.168.3.8:9092";
    public static final String topic = "topic";
    public static final String group = "group-id42";
    public static final String client = "client-id2";
    public static Properties initConfig(){

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //消费位移自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);

        properties.put(ConsumerConfig.GROUP_ID_CONFIG,group);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,client);
        return  properties;
    }

    public static void main(String[] args) {
        Properties props = initConfig();
        int consumerThreadNum = 4;
        for (int i=0;i<consumerThreadNum;i++){
            new KafkaConsumerThread(props,topic).start();
        }
    }
    public static class KafkaConsumerThread extends Thread{

        private  KafkaConsumer<String,String> kafkaConsumer;

        public  KafkaConsumerThread(Properties props,String topic){
            this.kafkaConsumer = new KafkaConsumer<String, String>(props);
            this.kafkaConsumer.subscribe(Arrays.asList(topic));
        }

        @Override
        public void run() {

            try {
                while (true){
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(Long.MAX_VALUE));
                    records.forEach(record->{
                        System.out.println("topic="+record.topic()+",  partition="+record.partition()+",  offset="+record.offset());
                        //消息处理
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.kafkaConsumer.close();
            }

        }
    }


}


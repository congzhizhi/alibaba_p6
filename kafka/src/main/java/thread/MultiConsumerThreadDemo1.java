package thread;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultiConsumerThreadDemo1 {
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

    static Map<TopicPartition, OffsetAndMetadata> offsets =new HashMap<>();

    public static void main(String[] args) {
        Properties props = initConfig();
        int consumerThreadNum = 4;
        for (int i=0;i<consumerThreadNum;i++){
            new KafkaConsumerThread(props,topic,Runtime.getRuntime().availableProcessors()).start();
        }
    }
    public static class KafkaConsumerThread extends Thread{

        private  KafkaConsumer<String,String> kafkaConsumer;
        private  ExecutorService executorService;
        private int threadNum;

        public  KafkaConsumerThread(Properties props, String topic, int processorNum){
            this.kafkaConsumer = new KafkaConsumer<String, String>(props);
            this.kafkaConsumer.subscribe(Arrays.asList(topic));
            this.threadNum = processorNum;
            executorService = new ThreadPoolExecutor(
                    threadNum,
                    threadNum,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(1000),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }

        @Override
        public void run() {

            try {
                while (true){
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(Long.MAX_VALUE));
                        //消息处理
                        executorService.submit(new RecordsHandler(records));
                        //位移提交工作
                        synchronized (offsets){
                            if(!offsets.isEmpty()){
                                kafkaConsumer.commitSync(offsets);
                                offsets.clear();
                            }
                        }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.kafkaConsumer.close();
            }
        }
    }

    public static class RecordsHandler implements Runnable {
        private final  ConsumerRecords<String,String > records;
        public RecordsHandler(ConsumerRecords<String, String> records) {
            this.records = records;
        }
        @Override
        public void run() {
            //真正处理records的地方

            //处理完后进行位移操作
            for (TopicPartition partition : records.partitions()) {
                List<ConsumerRecord<String, String>> tpRecords = this.records.records(partition);
                long lastConsumedOffset = tpRecords.get(tpRecords.size()-1).offset();
                synchronized (offsets){
                    if (!offsets.containsKey(partition)){
                        offsets.put(partition,new OffsetAndMetadata(lastConsumedOffset+1));
                    }else{
                        long position = offsets.get(partition).offset();
                        if (position<lastConsumedOffset+1){
                            offsets.put(partition,new OffsetAndMetadata(lastConsumedOffset+1));
                        }
                    }
                }
                
            }
        }
    }
}


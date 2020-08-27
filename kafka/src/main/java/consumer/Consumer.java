package consumer;

import interceptor.ConsumerInterceptorTTL;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer {
    public static final String brokerList = "192.168.3.8:9092";
    public static final String topic = "topic";
    public static final String group = "group-id42";
    public static final String client = "client-id2";

    public  static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static Properties initConfig(){

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //新的消费者，从最早的消息开始消费，如果是从最后开始消费的话，参数改成latest
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //消费位移自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
//        //自动提交的周期，默认是5秒
//        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,5000);

        properties.put(ConsumerConfig.GROUP_ID_CONFIG,group);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,client);
        //配置拦截器
//        properties.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, ConsumerInterceptorTTL.class);
        properties.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG,60000);

        return  properties;
    }
    public static void main(String[] args) {

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(initConfig());

        List<TopicPartition> partitions = new ArrayList<>();
        //获取主题的全部分区
        consumer.partitionsFor("topic").forEach(partitionInfo -> {
            System.out.println("分区："+partitionInfo.partition());
            partitions.add(new TopicPartition(partitionInfo.topic(),partitionInfo.partition()));

        });
        //通过assign方法来实现订阅主题全部分区
        consumer.assign(partitions);
//        TopicPartition topicPartition = new TopicPartition("topic-demo",0);
//        consumer.assign(Arrays.asList(topicPartition));//订阅主题topic-demo的分区0

        //subscribe方法订阅主题,此方法会自动进行消费者平衡
//        consumer.subscribe(Collections.singletonList(topic));
        //取消订阅
//        consumer.unsubscribe();

//        Set<TopicPartition> set = new HashSet<>();
//        Map<TopicPartition,Long> map = new HashMap<>();
//        while (set.size() == 0){
//            consumer.poll(Duration.ofMillis(10000));
//            set = consumer.assignment();
//        }
//        for (TopicPartition tp: set){
//            map.put(tp,System.currentTimeMillis()-1*24*3600*1000);
//        }
//        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(map);
//
//        for (TopicPartition tp: set){
//             OffsetAndTimestamp offsetAndTimestamp = offsets.get(tp);
//             if (offsetAndTimestamp!=null)
//                 consumer.seek(tp,offsetAndTimestamp.offset());
//
//        }


        try {
            while (isRunning.get()) {
                //阻塞1秒
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                //一直阻塞，直到有消息为止
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(Long.MAX_VALUE));
//                按分区处理消息
                for (TopicPartition tp : records.partitions()){
                    //获取当前分区的所有消息
                    List<ConsumerRecord<String,String>> partitionRecords = records.records(tp);
                    for (ConsumerRecord<String, String> record : partitionRecords){
                        System.out.println("partition:"+record.partition()+"----value:"+record.value());
                    }
                    //当前分区最后一条消息的位移
                    long lastConsumedOffset = partitionRecords.get(partitionRecords.size() -1).offset();
                    //按分区的粒度，进行位移提交
                    consumer.commitSync(Collections.singletonMap(tp,new OffsetAndMetadata(lastConsumedOffset+1)));
                    //异步提交位移
//                    consumer.commitAsync(Collections.singletonMap(tp, new OffsetAndMetadata(lastConsumedOffset + 1)), new OffsetCommitCallback() {
//                        @Override
//                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
//                            if(e == null){
//                                System.out.println(map);
//                            }else{
//                                System.out.println("提交失败");
//                            }
//                        }
//                    });
                }
//                records.forEach(record->{
//                    System.out.println("topic="+record.topic()+",  partition="+record.partition()+",  offset="+record.offset());
//                    long offset = record.offset();
//                    TopicPartition partition = new TopicPartition(record.topic(), record.partition());
//                    //每消费一条消息提交一次位移
//                    consumer.commitSync(Collections.singletonMap(partition,new OffsetAndMetadata(offset+1)));
//                    System.out.println("_______________________________");
//                });








            }
        }catch (WakeupException  e){
            //do nothing
        }
        catch (Exception e) {
            //正常退出或者再均衡时，进行同步位移提交
            e.printStackTrace();
        } finally {
            consumer.commitSync();
            consumer.close();
        }
    }
}

package producer;

import interceptor.ProducerInterceptor;
import interceptor.ProducerInterceptor2;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Producer {
//        public static final String brokerList = "192.168.0.18:9092,192.168.0.19:9092";//集群模式
        public static final String BROKERLIST = "192.168.3.8:9092";
        public static final String TOPIC = "topic-management";
        public static final String CLIENT_ID = "topic-demodd";


        public static Properties initConfig(){
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKERLIST);
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.put(ProducerConfig.CLIENT_ID_CONFIG,CLIENT_ID);

            //设置RecordAccumulator发送缓冲区的大小
            properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,"33554432");//32 MB
            //设置阻塞异常的超时等待
            properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,"5000");//5s

            //该大小的ByteBuffer会放入缓存池BufferPool中
            properties.put(ProducerConfig.BATCH_SIZE_CONFIG,"32768");//32KB

            //缓存未响应的请求数个数，超过该数值后就不能向这个连接发送更多的请求了
            properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,10);
            //客户端更新kafka集群元数据的时间间隔,默认5分钟
            properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG,300000);

            //leader副本成功收到消息后返回响应，不管follower副本
            properties.put(ProducerConfig.ACKS_CONFIG,"1");

            //生产者客户端能发送消息的最大值，默认为1048576B，即1MB。
            properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG,"1048576");


            /**
             * 如果key不为null,那么默认的分区器会对key进行哈希（采用MurmurHash2算法，具备高运算性能及低碰撞率）最终根据得到的哈希值来计算分区号,拥有相同的key的消息会被写入同一个分区
             *
             * 如果key为null,那么消息将会以轮询的方式发往主题内的各个可用分区内
             *
             * 在不改变主题分区的情况下，key与分区之间的映射可以保持不变。不过，一旦主题中增加了新的分区，映射就破坏了。
             *
             *
//             */
//            properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class.getName());//指定分区器，配合key使用

              //配置重试异常
            /*
             *重试异常包括
             * NetworkException/LerderNotAvailableException/UnknownTopicOrPartitionException/NotEnoughReplicasException
             * NotCoordinatorException
             */
//            properties.put(ProducerConfig.RETRIES_CONFIG,10);
            //两次重试的时间间隔
//            properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,100);


            /**配置拦截器
             * 在消息序列化和计算分区之前会调用生产者拦截器的onSend方法来对消息进行更改
             * 在消息被应答之前或消息发送失败的时候调用onAcknowledgement()方法，优先于用户设定的Callback之前执行，这个方法运行在Producer的线程中
             * 所以这个方法中实现的逻辑越简单越好，否则会影响消息的发送速度
             * 多个拦截器以逗号隔开，调用顺序跟配置顺序相同
             */
//            properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
//                    ProducerInterceptor.class.getName()+","+ ProducerInterceptor2.class.getName());
            return properties;
        }

        //同步发送
        public  static void sendSyn(ProducerRecord<String, String> record, KafkaProducer<String, String> producer){
            Future<RecordMetadata> retu =  producer.send(record);//send方法本身是异步的
            RecordMetadata recordMetadata = null;
            try {
                recordMetadata = retu.get();//实现同步发送
                System.out.println("同步发送成功到:"+recordMetadata.topic()+":"+recordMetadata.partition());
                //recordMetadata = retu.get(3, TimeUnit.SECONDS);//超时阻塞拉
                //producer.send(record).get();//如果不需要返回的信息，通过这种方式同步更简单
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //异步发送
        public  static void sendAsyn(ProducerRecord<String, String> record,KafkaProducer<String, String> producer){

            //异步发送
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        System.out.println("异步发送成功"+recordMetadata.topic()+":"+recordMetadata.partition());
                    }
                }
            });
        }

        public static void main(String[] args) throws InterruptedException {

            //KafkaProducer是线程安全的，可以池化来供其他线程调用
            KafkaProducer<String, String> producer = new KafkaProducer<>(initConfig());
//            ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key","message_congzhizhi");//指定key，配合分区器使用
            for(int i=0;i<10;i++){
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "message_congzhizhi"+i);
                sendSyn(record,producer);
//                sendAsyn(record,producer);
            }

            //阻塞等待之前所有的发送请求完成后再关闭
            producer.close();

        }
}

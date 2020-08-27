package interceptor;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerInterceptorTTL implements ConsumerInterceptor<String,String> {

    private  static  final  long EXPIRE_INTERVAL = 10 * 1000;

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> consumerRecords) {
        long now = System.currentTimeMillis();
        //构建新的分区消息映射表
        Map<TopicPartition, List<ConsumerRecord<String,String>>> newRecords = new HashMap<>();
        //遍历分区
        for (TopicPartition tp : consumerRecords.partitions()){
            //获取分区内的消息
            List<ConsumerRecord<String,String>> tpRecords = consumerRecords.records(tp);
            List<ConsumerRecord<String,String>> newTpRecords = new ArrayList<>();
            //遍历消息，做判断
            for (ConsumerRecord<String, String> tpRecord : tpRecords) {
                //拿到10秒以内的消息
                if (now - tpRecord.timestamp() < EXPIRE_INTERVAL){
                    newTpRecords.add(tpRecord);
                }
            }
            if (!newTpRecords.isEmpty()){
                newRecords.put(tp, newTpRecords);
            }
        }
        return new ConsumerRecords<>(newRecords);
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
        map.forEach((tp,offset)->{
            System.out.println(tp+":"+offset.offset());
        });
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}

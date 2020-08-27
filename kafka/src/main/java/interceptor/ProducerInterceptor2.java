package interceptor;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class ProducerInterceptor2 implements org.apache.kafka.clients.producer.ProducerInterceptor<String ,String> {
    private volatile long sendSuccess = 0;
    private volatile long sendFailure = 0;

    @Override
    public ProducerRecord onSend(ProducerRecord<String ,String> producerRecord) {
        //消息发送前，进行修改操作
        String modifieldValue = "prefix2-"+producerRecord.value();
        return new ProducerRecord<String ,String>(producerRecord.topic(),
                producerRecord.partition(),
                producerRecord.timestamp(),
                producerRecord.key(),
                modifieldValue,
                producerRecord.headers());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {
        System.out.println("发送成功率："+(double)sendSuccess/(sendSuccess+sendFailure));
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}

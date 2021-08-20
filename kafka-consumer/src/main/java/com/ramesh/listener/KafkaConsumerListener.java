package com.ramesh.listener;

import com.ramesh.library.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerListener {

    @KafkaListener(id = "mytopiclistener", topics = { "mytopic"} )
    public void listen(ConsumerRecord<String, Message> consumerRecord) {
        log.info("Received message: Partition: {}, Message: {}", consumerRecord.partition(), consumerRecord.value());
    }
}

package com.ramesh.controller;

import com.ramesh.library.MyTopicPartition;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaConsumerController {

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @PostMapping(value = "/pause", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> pause(@RequestBody MyTopicPartition myTopicPartition) {
        MessageListenerContainer mytopiclistener = kafkaListenerEndpointRegistry.getListenerContainer("mytopiclistener");
        TopicPartition topicPartition = new TopicPartition(myTopicPartition.getTopic(), myTopicPartition.getPartition());
        mytopiclistener.pausePartition(topicPartition);
        return new ResponseEntity<>("Partition: " + myTopicPartition.getPartition() + " is paused.", HttpStatus.OK);
    }

    @PostMapping(value = "/resume", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> resume(@RequestBody MyTopicPartition myTopicPartition) {
        MessageListenerContainer mytopiclistener = kafkaListenerEndpointRegistry.getListenerContainer("mytopiclistener");
        TopicPartition topicPartition = new TopicPartition(myTopicPartition.getTopic(), myTopicPartition.getPartition());
        mytopiclistener.resumePartition(topicPartition);
        return new ResponseEntity<>("Partition: " + myTopicPartition.getPartition() + " is resumed.", HttpStatus.OK);
    }

}

package com.ramesh.controller;

import com.ramesh.library.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> send(@RequestBody Message message) {
        log.info("Sending message: {}", message);
        ListenableFuture<SendResult<String, Message>> listenableFuture = kafkaTemplate.send("mytopic", message.getCategory().getPartition(), message.getCategory().name(), message);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
            @Override
            public void onFailure(Throwable throwable) {
                KafkaProducerException kafkaProducerException = (KafkaProducerException) throwable;
                log.error("Failed to send message: {}", kafkaProducerException.getFailedProducerRecord().value());
            }

            @Override
            public void onSuccess(SendResult<String, Message> stringMessageSendResult) {
                log.info("Message sent to partition: {}", stringMessageSendResult.getRecordMetadata().partition());
            }
        });
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

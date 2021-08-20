package com.ramesh.library;

import lombok.Data;

@Data
public class MyTopicPartition {
    private String topic;
    private Integer partition;
}

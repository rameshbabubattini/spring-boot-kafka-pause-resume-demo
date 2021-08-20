package com.ramesh.library;

public enum Category {
    CATEGORY1(0),
    CATEGORY2(1),
    CATEGORY3(2);

    private Integer partition;

    private Category(Integer partition) {
        this.partition = partition;
    }

    public Integer getPartition() {
        return this.partition;
    }
}

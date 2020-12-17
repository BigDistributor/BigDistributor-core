package com.bigdistributor.biglogger.remote.kafka;

import com.google.gson.Gson;

public class KafkaMessage {
    private KafkaTopic topic;
    private String jobId;
    private long ts;
    private int blockId;
    private String log;

    private KafkaMessage(KafkaTopic topic, String jobId, long ts, int blockId, String log) {
        super();
        this.topic = topic;
        this.jobId = jobId;
        this.ts = ts;
        this.blockId = blockId;
        this.log = log;
    }

    public KafkaMessage(KafkaTopic topic, String jobId, int blockId, String log) {
        this(topic, jobId, System.currentTimeMillis(), blockId, log);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static KafkaMessage fromString(String json) {
        return new Gson().fromJson(json, KafkaMessage.class);
    }
}

package com.bigdistributor.core.remote.mq.entities;

import com.google.gson.Gson;

public class MQMessage {
    private MQTopic topic;
    private String jobId;
    private long ts;
    private int blockId;
    private String log;

    private MQMessage(MQTopic topic, String jobId, long ts, int blockId, String log) {
        super();
        this.topic = topic;
        this.jobId = jobId;
        this.ts = ts;
        this.blockId = blockId;
        this.log = log;
    }

    public MQMessage(MQTopic topic, String jobId, int blockId, String log) {
        this(topic, jobId, System.currentTimeMillis(), blockId, log);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static MQMessage fromString(String json) {
        return new Gson().fromJson(json, MQMessage.class);
    }


    public MQTopic getTopic() {
        return topic;
    }

    public String getJobId() {
        return jobId;
    }

    public long getTs() {
        return ts;
    }

    public int getBlockId() {
        return blockId;
    }

    public String getLog() {
        return log;
    }
}

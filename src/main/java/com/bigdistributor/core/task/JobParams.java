package com.bigdistributor.core.task;

public class JobParams {
    private String server;
    private String queue;
    private String jobId;
    private Integer blockId;

    public JobParams(String server, String queue, String jobId, Integer blockId) {
        this.server = server;
        this.queue = queue;
        this.jobId = jobId;
        this.blockId = blockId;
    }

    public String getServer() {
        return server;
    }

    public String getQueue() {
        return queue;
    }

    public String getJobId() {
        return jobId;
    }

    public Integer getBlockId() {
        return blockId;
    }
}

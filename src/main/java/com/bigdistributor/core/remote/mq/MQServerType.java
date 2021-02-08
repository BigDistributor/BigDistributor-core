package com.bigdistributor.core.remote.mq;

public enum MQServerType {
    Kafka("Kafka"), RabbitMQ("RabbitMq"), SNS("SNS");

    private final String key;

    MQServerType(String key) {
        this.key = key;
    }


    public String getKey() {
        return key;
    }

    public static MQServerType getForKey(String key) {
        for( MQServerType p: MQServerType.values()){
            if (p.getKey().equalsIgnoreCase(key))
                return p;
        }
        throw new IllegalArgumentException();
    }
}

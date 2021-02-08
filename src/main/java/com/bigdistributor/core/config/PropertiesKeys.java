package com.bigdistributor.core.config;

import com.bigdistributor.core.remote.mq.MQServerType;

public enum PropertiesKeys {
    Version("config.version", 1.0),
    MQType("mq.server.type", MQServerType.SNS),
    MQServer("mq.server.host", "ec2-18-184-134-47.eu-central-1.compute.amazonaws.com"),
    MQQueue("mq.server.queue", "bigdistributor"),
    MQServerPort("mq.server.port", 9092);

    private final String key;
    private final Object defaultValue;

    PropertiesKeys(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Object objectOf(String s) {
        if (this == MQType)
            return MQServerType.getForKey(s);
        if (defaultValue.getClass().isInstance(Double.class))
            return Double.valueOf(s);
        else if (defaultValue.getClass().isInstance(Integer.class))
            return Integer.valueOf(s);
        else
            return s;
    }

    public static PropertiesKeys getPropForKey(String key) {
        for (PropertiesKeys p : PropertiesKeys.values()) {
            if (p.getKey().equalsIgnoreCase(key))
                return p;
        }
        throw new IllegalArgumentException();
    }
}

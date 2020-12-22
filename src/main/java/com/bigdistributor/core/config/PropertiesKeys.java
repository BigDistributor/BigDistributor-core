package com.bigdistributor.core.config;

public enum PropertiesKeys {
    Version("config.version", 1.0),
    MQServer("mq.server", "");

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
}

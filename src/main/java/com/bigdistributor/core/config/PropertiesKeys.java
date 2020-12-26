package com.bigdistributor.core.config;

public enum PropertiesKeys {
    Version("config.version", 1.0),
    MQServer("mq.server.host", "ec2-3-91-12-31.compute-1.amazonaws.com"),
    MQServerPort("mq.server.port", 5672);

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
        if (defaultValue.getClass().isInstance(Double.class))
            return Double.valueOf(s);
        else if (defaultValue.getClass().isInstance(Integer.class))
            return Integer.valueOf(s);
        else
            return s;
    }

    public static PropertiesKeys getPropForKey(String key) {
        for( PropertiesKeys p: PropertiesKeys.values()){
            if (p.getKey().equalsIgnoreCase(key))
                return p;
        }
        throw new IllegalArgumentException();
    }
}

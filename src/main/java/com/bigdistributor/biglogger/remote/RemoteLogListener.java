package com.bigdistributor.biglogger.remote;

import com.bigdistributor.biglogger.remote.kafka.KafkaMessage;

public interface RemoteLogListener {
    void onLogAdded(KafkaMessage message);
}

package com.bigdistributor.biglogger.remote.kafka;

import com.bigdistributor.biglogger.remote.RemoteLogListener;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class KafkaLogDispatchManager {
    static Logger logger = Logger.getLogger(KafkaLogDispatchManager.class.getClass());
    private static List<RemoteLogListener> listeners = new ArrayList<>();

    public static void addLog(String log) {
        KafkaMessage message = KafkaMessage.fromString(log);
        logger.log(Level.DEBUG, "New msg: " + message);
        notifyListeners(message);
    }

    private static void notifyListeners(KafkaMessage message) {
        listeners.forEach(listener -> listener.onLogAdded(message));
    }

    public static void addListener(RemoteLogListener listener) {
        listeners.add(listener);
    }
}

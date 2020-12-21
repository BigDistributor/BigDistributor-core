package com.bigdistributor.core.remote.mq;

import com.bigdistributor.core.remote.mq.entities.MQMessage;
import com.bigdistributor.core.remote.mq.entities.RemoteLogListener;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MQLogReceiveDispatchManager {
    static Logger logger = Logger.getLogger(MQLogReceiveDispatchManager.class.getClass());
    private static List<RemoteLogListener> listeners = new ArrayList<>();

    public static void addLog(String log) {
        MQMessage message = MQMessage.fromString(log);
        logger.log(Level.DEBUG, "New msg: " + message);
        notifyListeners(message);
    }

    private static void notifyListeners(MQMessage message) {
        listeners.forEach(listener -> listener.onLogAdded(message));
    }

    public static void addListener(RemoteLogListener listener) {
        listeners.add(listener);
    }
}

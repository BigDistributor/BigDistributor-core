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
    private static List<MQMessage> msgs = new ArrayList<>();

    public static void addLog(String log) {
        MQMessage message = MQMessage.fromString(log);
        msgs.add(message);
        logger.log(Level.DEBUG, "New msg: " + message);
        notifyListeners(message);
    }

    private static void notifyListeners(MQMessage message) {
        listeners.forEach(listener -> listener.onLogAdded(message));
    }

    public static void addListener(RemoteLogListener listener) {
        addListener(listener, false);
    }

    public static void addListener(RemoteLogListener listener, Boolean notifyOld) {
        listeners.add(listener);
        if (notifyOld)
            msgs.forEach(m -> listener.onLogAdded(m));
    }
}

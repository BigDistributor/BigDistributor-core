package com.bigdistributor.core.remote.mq;

import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.core.remote.mq.entities.MQMessage;
import com.bigdistributor.core.remote.mq.entities.RemoteLogListener;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class MQLogReceiveDispatchManager {
    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static List<RemoteLogListener> listeners = new ArrayList<>();
    private static List<MQMessage> msgs = new ArrayList<>();

    public static void addLog(String log) {
        MQMessage message = MQMessage.fromString(log);
        msgs.add(message);
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

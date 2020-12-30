package com.bigdistributor.biglogger.adapters;


import com.bigdistributor.core.remote.mq.entities.MQMessage;
import com.bigdistributor.core.remote.mq.entities.MQTopic;
import com.bigdistributor.core.task.JobID;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log extends Logger {

    protected Log(String name) {
        super(name, null);
    }

    public static Log getLogger(String name) {
        return new Log(name);
    }

    public void blockDone(Integer blockId, String str) {
        MQMessage msg = new MQMessage(MQTopic.TASK_DONE, JobID.get(), blockId, str);
        log(Level.CONFIG, msg.toString());
    }

    public void blockStarted(Integer blockId, String str) {
        MQMessage msg = new MQMessage(MQTopic.TASK_STARTED, JobID.get(), blockId, str);
        log(Level.CONFIG, msg.toString());
    }

    public void blockError(Integer blockId, String str) {
        MQMessage msg = new MQMessage(MQTopic.TASK_ERROR, JobID.get(), blockId, str);
        log(Level.CONFIG, msg.toString());
    }

    public void blockLog(Integer blockId, String str) {
        MQMessage msg = new MQMessage(MQTopic.LOG, JobID.get(), blockId, str);
        log(Level.CONFIG, msg.toString());
    }


    public void info(String string) {
        log(Level.INFO, string);
    }

    public void debug(String string) {
        log(Level.FINE, string);
    }

    public void error(String string) {
        log(Level.SEVERE, string);
    }
}

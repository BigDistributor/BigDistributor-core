package com.bigdistributor.biglogger.adapters;


import com.bigdistributor.core.remote.mq.entities.MQMessage;
import com.bigdistributor.core.remote.mq.entities.MQTopic;
import com.bigdistributor.core.task.JobID;

import java.io.Serializable;
import java.util.logging.*;

public class Log implements Serializable {

    private static Logger rootLogger;
    private Logger logger;

//    static {
//        init();
//    }

    public Log(){
        this("");
    }

    public Log(String name) {
        if (rootLogger == null) {
            init();
        }
        Logger logger = Logger.getLogger(name);
        logger.setParent(rootLogger);
        this.logger = logger;
    }

    public Log(Logger logger) {
        this.logger = logger;
    }

    public static Log getLogger(String name) {
        if (rootLogger == null) {
            init();
        }
        Logger logger = Logger.getLogger(name);
        logger.setParent(rootLogger);
        return new Log(logger);
    }

    private static void init() {
        rootLogger = LogManager.getLogManager().getLogger("");
//        Handler handlerObj = new ConsoleHandler();
//        handlerObj.setLevel(Level.ALL);
//        rootLogger.addHandler(handlerObj);
        rootLogger.setLevel(Level.INFO);
        rootLogger.log(Level.FINEST, "finest");
    }

    public static void setRoot(Logger rootLogger) {
        Log.rootLogger = rootLogger;
    }

    public static Logger getRoot() {
        return rootLogger;
    }

    public static Logger setLogger(Logger logger) {
        if (rootLogger == null) {
            init();
        }
        logger.setParent(rootLogger);
        return logger;
    }

    public void blockDone(Integer blockId, String str) {
        MQMessage msg = new MQMessage(MQTopic.TASK_DONE, JobID.get(), blockId, str);
        logger.log(Level.WARNING, msg.toString());
    }

    public void blockStarted(Integer blockId, String str) {
        MQMessage msg = new MQMessage(MQTopic.TASK_STARTED, JobID.get(), blockId, str);
        logger.log(Level.WARNING, msg.toString());
    }

    public void blockError(Integer blockId, String str) {
        MQMessage msg = new MQMessage(MQTopic.TASK_ERROR, JobID.get(), blockId, str);
        logger.log(Level.WARNING, msg.toString());
    }

    public void blockLog(Integer blockId, String str) {
        MQMessage msg = new MQMessage(MQTopic.LOG, JobID.get(), blockId, str);
        logger.log(Level.WARNING, msg.toString());
    }


    public void info(String string) {
        logger.log(Level.INFO, string);
    }

    public void debug(String string) {
        logger.log(Level.FINE, string);
    }

    public void error(String string) {
        logger.log(Level.SEVERE, string);
    }


}

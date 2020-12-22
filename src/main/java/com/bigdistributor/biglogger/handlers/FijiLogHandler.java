package com.bigdistributor.biglogger.handlers;

import com.bigdistributor.biglogger.generic.LogHandler;
import com.bigdistributor.core.app.ApplicationMode;

import org.scijava.Context;
import org.scijava.log.LogService;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

@LogHandler(format = "fiji", modes = {ApplicationMode.DistributionMasterFiji, ApplicationMode.OneNodeFiji})
public class FijiLogHandler extends Handler {
    public FijiLogHandler() {
        System.out.println("Fiji Log Handler initiated..");
    }

    public static Boolean initialized = false;

    private static LogService logService;

    public static void initLogger() {
        if (initialized) return;
        final Context context = new Context(LogService.class);
        logService = context.getService(LogService.class);
        initialized = true;
    }

//    public static void addListener(LoggingPanel loggingPanel) {
//        if (!initialized)
//            initLogger();
//        logService.addLogListener(loggingPanel);
//    }

    public void publish(LogRecord record) {
        if (!initialized)
            initLogger();
        logService.subLogger(record.getSourceClassName()).log(record.getLevel().intValue(), record.getMessage());
    }

    public void flush() {
    }

    public void close() throws SecurityException {
        logService = null;
        initialized = false;

    }
}

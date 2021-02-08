package com.bigdistributor.biglogger.adapters;

import com.bigdistributor.biglogger.generic.LogHandler;
import com.bigdistributor.biglogger.generic.LogReceiver;
import com.bigdistributor.biglogger.handlers.mq.rabbitmq.MQLogPublishHandler;
import com.bigdistributor.biglogger.handlers.TerminalLogHandler;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.config.ConfigManager;
import com.bigdistributor.core.config.PropertiesKeys;
import com.bigdistributor.core.generic.InvalidApplicationModeException;
import com.bigdistributor.core.remote.mq.MQServerType;
import com.bigdistributor.core.task.JobParams;
import org.reflections.Reflections;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class LoggerManager {

    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public static void initLoggers(ApplicationMode type) throws InvalidApplicationModeException {
        if (type == null) {
            throw new InvalidApplicationModeException("Can't be null");
        }
        List<Class<?>> loggers = getLoggers(type);

        logger.info("Initiating Logger for application mode: " + type + " - ");
//
        loggers.stream().forEach(c -> {
            try {
                logger.info("Setting " + c.getSimpleName());
                LogHandler annotation = c.getAnnotation(LogHandler.class);
//                For MQ checking which one used
                String format = annotation.format();
                if (!format.equals("")) {
                    MQServerType mqType = MQServerType.getForKey(annotation.format());
                    if (!ConfigManager.getConfig().get(PropertiesKeys.MQType).equals(mqType)) {
                        logger.info("Invalid logger " + mqType);
                        return;
                    }
                }
                if (Handler.class.isAssignableFrom(c)) {
                    Handler handler = Handler.class.cast(c.newInstance());
                    handler.setLevel(Level.ALL);
                    Log.getRoot().addHandler(handler);
                } else if (LogReceiver.class.isAssignableFrom(c)) {
                    LogReceiver receiver = LogReceiver.class.cast(c.newInstance());
                    receiver.start();
                }
            } catch (IllegalAccessException | InstantiationException e) {
                logger.error(e.toString());
            }
        });
    }

    private static List<Class<?>> getLoggers(ApplicationMode type) {
        List<Class<?>> loggers = new ArrayList<>();
        final Set<Class<?>> indexableClasses = new Reflections("com.bigdistributor").getTypesAnnotatedWith(LogHandler.class);
        indexableClasses.forEach(c -> {
            if (Arrays.asList(c.getAnnotation(LogHandler.class).modes()).contains(type))
                loggers.add(c);
        });
        return loggers;
    }

    public static void addRemoteLogger(JobParams params) {
        Handler handler = new MQLogPublishHandler(params.getServer(), params.getQueue());
        handler.setLevel(Level.ALL);
        Log.getRoot().addHandler(handler);
    }

    public static void addHandler(Handler handler){
        Log.getRoot().addHandler(handler);
    }

    public static void initTerminal() {
        LogManager.getLogManager().reset();
        Handler handler = new TerminalLogHandler();
        handler.setLevel(Level.ALL);
        Log.getRoot().addHandler(handler);
    }
}

package com.bigdistributor.biglogger.adapters;

import com.bigdistributor.biglogger.generic.LogHandler;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.generic.InvalidApplicationModeException;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerManager {

    public static void initLogger(ApplicationMode type) throws InvalidApplicationModeException {
        if(type==null){
            throw new InvalidApplicationModeException("Can't be null");
        }
        final Set<Class<?>> indexableClasses = new Reflections("").getTypesAnnotatedWith(LogHandler.class);
        System.out.println("Initiating Logger for application mode: "+type);
        LogManager.getLogManager().reset();
        Logger rootLogger = LogManager.getLogManager().getLogger("");

        indexableClasses.stream().forEach(c -> {
            try {
                if (Handler.class.isAssignableFrom(c))
                    if (Arrays.asList(c.getAnnotation(LogHandler.class).modes()).contains(type))
                        rootLogger.addHandler(Handler.class.cast(c.newInstance()));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }
}

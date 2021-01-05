package com.bigdistributor.biglogger.adapters;

import com.bigdistributor.biglogger.generic.LogHandler;
import com.bigdistributor.biglogger.generic.LogMode;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.generic.InvalidApplicationModeException;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class LoggerManager {
    private static List<Handler> advanceHandlers = new ArrayList<>();

    public static void initLogger(ApplicationMode type) throws InvalidApplicationModeException {
        if (type == null) {
            throw new InvalidApplicationModeException("Can't be null");
        }
        final Set<Class<?>> indexableClasses = new Reflections("com.bigdistributor").getTypesAnnotatedWith(LogHandler.class);
        System.out.println("Initiating Logger for application mode: " + type);
        LogManager.getLogManager().reset();
        indexableClasses.stream().forEach(c -> {
            try {
                if (Handler.class.isAssignableFrom(c))
                    if (Arrays.asList(c.getAnnotation(LogHandler.class).modes()).contains(type)) {
                        Handler handler = Handler.class.cast(c.newInstance());
                        handler.setLevel(Level.ALL);
                        if (c.getAnnotation(LogHandler.class).type() == LogMode.Basic) {
                            Log.getRoot().addHandler(handler);
                        } else {
                            advanceHandlers.add(handler);
                        }
                    }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static void initAdvanceHandlers() {
        for (Handler handler : advanceHandlers)
            Log.getRoot().addHandler(handler);
    }
}

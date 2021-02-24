package com.bigdistributor.biglogger.handlers;

import com.bigdistributor.biglogger.adapters.LoggerManager;
import com.bigdistributor.biglogger.handlers.data.App;
import com.bigdistributor.biglogger.handlers.data.HelloClass;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.generic.InvalidApplicationModeException;
import org.junit.Test;

public class LoggerTest {

    @Test
    public void logMessageEnsureCorrectOutputFormat() {
        new HelloClass().sayHello();
        new App();
        new HelloClass().sayHello();
    }

    @Test
    public void testLoggerManager() throws InvalidApplicationModeException {
        LoggerManager.setApplicationMode(ApplicationMode.OneNodeHeadless);
        LoggerManager.initLoggers();
    }

}

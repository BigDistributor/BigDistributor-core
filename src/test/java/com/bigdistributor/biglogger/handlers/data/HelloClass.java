package com.bigdistributor.biglogger.handlers.data;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

public class HelloClass {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public void sayHello() {
        LOGGER.info("Hello from class");
    }

}
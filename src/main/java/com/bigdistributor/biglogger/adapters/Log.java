package com.bigdistributor.biglogger.adapters;


import java.util.logging.Level;
import java.util.logging.Logger;

public class Log extends Logger {

    protected Log(String name) {
        super(name, null);
    }

    public static Log getLogger(String name){
        return new Log(name);
    }

    public void info(String string) {
    	log(Level.INFO,string);
    }

    public void debug(String string) {
        log(Level.FINE,string);
    }

    public void error(String string) {
        log(Level.SEVERE,string);
    }
}

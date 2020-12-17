package com.bigdistributor.biglogger.handlers;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class TracingPrintStream extends PrintStream {
    public TracingPrintStream(PrintStream original) {
        super(original);
    }

    @Override
    public void println(String msg) {
        LogRecord logRecord = new LogRecord(Level.ALL, msg);
        Logger.getGlobal().log(logRecord);
        super.println(formatLog(msg));
    }

    private String formatLog(String msg) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        // Element 0 is getStackTrace
        // Element 1 is println
        // Element 2 is the caller
        StackTraceElement caller = stack[2];
        return caller.getClassName() + ": " + msg;
    }

    public static void main(String[] args) {
        System.setOut(new TracingPrintStream(System.out));
        System.out.println("Sample line");
    }
}

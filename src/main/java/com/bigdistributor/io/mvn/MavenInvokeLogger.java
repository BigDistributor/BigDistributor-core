package com.bigdistributor.io.mvn;

import com.bigdistributor.biglogger.adapters.Log;
import org.apache.maven.shared.invoker.InvokerLogger;

import java.lang.invoke.MethodHandles;

public class MavenInvokeLogger implements InvokerLogger {

    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    @Override
    public void debug(String s) {
        logger.debug(s);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        logger.debug(s);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void info(String s, Throwable throwable) {
        logger.info(s);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void warn(String s) {
        logger.error(s);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        logger.error(s);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void error(String s) {
        logger.error(s);
    }

    @Override
    public void error(String s, Throwable throwable) {
        logger.error(s);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void fatalError(String s) {
        logger.error(s);
    }

    @Override
    public void fatalError(String s, Throwable throwable) {
        logger.error(s);
    }

    @Override
    public boolean isFatalErrorEnabled() {
        return true;
    }

    @Override
    public void setThreshold(int i) {

    }

    @Override
    public int getThreshold() {
        return 0;
    }
}

package com.bigdistributor.biglogger.handlers;

import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.biglogger.generic.LogHandler;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

@LogHandler(format = "terminal", modes = {ApplicationMode.Headless, ApplicationMode.Fiji})
public class TerminalLogHandler extends Handler {
    public TerminalLogHandler() {
        System.out.println("Terminal Log Handler initiated..");
    }

    private static final Formatter defaultFormatter = new Formatter() {
        private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s : %4$s";

        @Override
        public String format(LogRecord record) {
            return String.format(format,
                    new Date(record.getMillis()),
                    record.getLevel().getLocalizedName(),
                    record.getSourceClassName(),
                    record.getMessage()
            );
        }
    };

    @Override
    public void publish(final LogRecord record) {
        final String msg;
        if (getFormatter() == null) {
            msg = this.defaultFormatter.format(record);
        } else {
            msg = getFormatter().format(record);
        }
        System.out.println(msg);


    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }


}

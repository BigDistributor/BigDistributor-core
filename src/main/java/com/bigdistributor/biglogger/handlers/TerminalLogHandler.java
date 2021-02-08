package com.bigdistributor.biglogger.handlers;

import com.bigdistributor.core.tools.Colors;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

//@LogHandler( type = LogMode.Basic, modes = {ApplicationMode.ExecutionNode, ApplicationMode.DistributionMaster, ApplicationMode.OneNodeHeadless})
public class TerminalLogHandler extends Handler {

    public TerminalLogHandler() {
        System.out.println("Terminal Log Handler initiated..");
    }

    protected static final Formatter defaultFormatter = new Formatter() {
        private static final String format = "%1$s [%2$tF %2$tT] [%3$-7s] %4$s : %5$s";

        @Override
        public String format(LogRecord record) {
            return String.format(format,
                    getColor(record.getLevel()),
                    new Date(record.getMillis()),
                    record.getLevel().getLocalizedName(),
                    record.getSourceClassName(),
                    record.getMessage()
            );
        }
    };

    private static String getColor(Level level) {
        int val = level.intValue();
        int range = (val<801)?0:((val<901)?1:((val<1001)?2:3));
        switch (range){
            case 0 : return Colors.GREEN.getANSI_CODE();
            case 1 : return Colors.WHITE.getANSI_CODE();
            case 2 : return Colors.PURPLE.getANSI_CODE();
            case 3 : return Colors.RED.getANSI_CODE();
        }
        return Colors.CYAN.getANSI_CODE();
    }

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

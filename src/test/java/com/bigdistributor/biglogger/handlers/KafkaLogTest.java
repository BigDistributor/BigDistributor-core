package com.bigdistributor.biglogger.handlers;

import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.app.BigDistributorApp;
import com.bigdistributor.core.app.BigDistributorMainApp;
import org.junit.Test;

import java.util.logging.Logger;

@BigDistributorApp(mode = ApplicationMode.ExecutionNode)
public class KafkaLogTest extends BigDistributorMainApp {
    final Logger logger = Log.getLogger(BigDistributorMainApp.class.getName());

    @Test
    public void checkSendLog(){
        new KafkaLogTest();
        logger.info("connected yes");
    }
}

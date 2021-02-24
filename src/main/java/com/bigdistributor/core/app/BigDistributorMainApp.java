package com.bigdistributor.core.app;


import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.biglogger.adapters.LoggerManager;
import com.bigdistributor.core.config.ConfigManager;

import java.io.Serializable;

/**
 * Class {@code BigDistributorMainApp} is the main root of BigDistributor.
 * Any Main app will need to extends from this class
 * App have 4 modes: Fiji, Headless, Cluster, Cloud
 * And based on mode the LogHandler will be initiated
 *
 * @author Marwan Zouinkhi
 * @see ApplicationMode
 * @since V0.1
 */

public abstract class BigDistributorMainApp implements Serializable {

    final Log logger = Log.getLogger(BigDistributorMainApp.class.getName());

    public BigDistributorMainApp() {
        LoggerManager.initTerminal();
        ConfigManager.init();
        BigDistributorApp dist = this.getClass().getAnnotation(BigDistributorApp.class);
        LoggerManager.setApplicationMode(dist.mode());
        logger.info("Main App: " + this.getClass() + " Type: " + dist.mode());


    }
}

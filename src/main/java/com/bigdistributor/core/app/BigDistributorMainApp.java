package com.bigdistributor.core.app;


import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.biglogger.adapters.LoggerManager;
import com.bigdistributor.core.config.ConfigManager;
import com.bigdistributor.core.generic.InvalidApplicationModeException;

import java.util.logging.Logger;

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

public abstract class BigDistributorMainApp {
    final Logger logger = Log.getLogger(BigDistributorMainApp.class.getName());

    public BigDistributorMainApp() {
        BigDistributorApp dist = this.getClass().getAnnotation(BigDistributorApp.class);
        initLogger(dist.mode());
        ConfigManager.init();
        LoggerManager.initAdvanceHandlers();

        logger.info("Main App: " + this.getClass() + " Type: " + dist.mode());
    }

    protected void initLogger(ApplicationMode mode) {
        try {
            LoggerManager.initLogger(mode);
        } catch (InvalidApplicationModeException e) {
            e.printStackTrace();
        }
    }
}

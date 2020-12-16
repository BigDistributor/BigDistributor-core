package com.bigdistributor.core.app;


import com.bigdistributor.plugin.adapters.LoggerManager;

/**
 * Class {@code Logger} is the main root of BigDistributor.
 * Any Main app will need to extends from this class
 * App have 4 modes: Fiji, Headless, Cluster, Cloud
 * And based on mode the LogHandler will be initiated
 *
 * @author Marwan Zouinkhi
 * @see ApplicationMode
 * @since V0.1
 */
@BigDistributorApp(mode = ApplicationMode.Headless)
public abstract class BigDistributorMainApp {

    public BigDistributorMainApp() {
        System.out.println("Main App: " + this.getClass());
        BigDistributorApp dist = this.getClass().getAnnotation(BigDistributorApp.class);
        System.out.println("App Type: " + dist.mode());
        initLogger(dist.mode());
    }

    void initLogger(ApplicationMode type) {
        LoggerManager.initLogger(type);
    }
}

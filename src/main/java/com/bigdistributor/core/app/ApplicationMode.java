package com.bigdistributor.core.app;

/**
 * BigDistributor can be run in 3 mode
 * FIJI: as plugin in Fiji with a GUI interface.
 * Headless: Local computer in a headless way, only with terminal
 * Cluster: in local cluster distribution processing
 * Cloud: in cloud server
 */
public enum ApplicationMode {
    Fiji, Headless, Cluster, Cloud
}

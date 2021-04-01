package com.bigdistributor.core.app;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;

public class DistributedAppConfig {

    private static DistributedAppConfig instance;

    final private long creationTime;
    private String taskID;

    public DistributedAppConfig get() {
        if(instance == null)
            instance = new DistributedAppConfig();
        return instance;
    }

    private DistributedAppConfig() {
        this.creationTime = new Date().getTime();
    }

    public static void save(File jsonFile) {
        new Gson().toJson(instance);
    }

    public static DistributedAppConfig initFromFile(File jsonFile) throws FileNotFoundException {
        instance = new Gson().fromJson(new FileReader(jsonFile), DistributedAppConfig.class);
        return instance;
    }
}

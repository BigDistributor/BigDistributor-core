package com.bigdistributor.core.task;

import com.bigdistributor.biglogger.adapters.Log;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

public class JobID {

    private static final Logger logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static JobID instance;

    public static String createNew() {
        instance = new JobID();
        return instance.getId();
    }

    public static void set(String id) {
        instance = new JobID(id);
    }

    private final static String nonAcceptableKeys = "-_";
    private String id;

    private JobID(String id) {
        this.id = id;
    }

    private JobID() {
        this.id = format(id());
    }

    private String format(String id) {
        String result = id.toLowerCase();
        for (char ch : nonAcceptableKeys.toCharArray())
            result.replace(Character.toString(ch), "");
        return result;
    }

    private static String id() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String id = UUID.randomUUID().toString().replace("-", "");
        return timeStamp + "-" + id;
    }

    private String getId() {
        return id;
    }

    public static String get() {
        if (instance == null) {
            logger.warning("No JobID found, New one is created!");
            return createNew();
        } else {
            return instance.getId();
        }
    }
}

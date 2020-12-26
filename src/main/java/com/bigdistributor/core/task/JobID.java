package com.bigdistributor.core.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class JobID {

    private final static String nonAcceptableKeys = "-_";
    private String id;

    public JobID() {
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

    public String get() {
        return id;
    }
}

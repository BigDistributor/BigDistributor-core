package com.bigdistributor.core.remote.mq.entities;

import java.util.ArrayList;
import java.util.Collection;


/**
 * In order to manage distribute processing, we will have 3 main topics for remote message
 * Task Done: topic to accumulate information when block task is done.
 * Task Error: We get it if we have any exception in the execution node.
 * Log : any log message or print made in the node.
 */
public enum MQTopic {
    TASK_ERROR("TASK_ERROR", true),
    TASK_DONE("TASK_DONE", true),
    LOG("TASK_LOG", true),
    TEST("test", false);

    private final String topicString;
    private final boolean important;

    MQTopic(String topicString, boolean important) {
        this.topicString = topicString;
        this.important = important;
    }

    public String getTopicString() {
        return topicString;
    }

    public boolean isImportant() {
        return important;
    }

    public static MQTopic fromString(String topicString) {
        for (MQTopic topic : MQTopic.values())
            if (topic.getTopicString().equalsIgnoreCase(topicString))
                return topic;
        return null;
    }


    public static Collection<String> getTopics() {
        Collection<String> list = new ArrayList<>();
        for (MQTopic topic : MQTopic.values())
            list.add(topic.getTopicString());
        return list;
    }
}

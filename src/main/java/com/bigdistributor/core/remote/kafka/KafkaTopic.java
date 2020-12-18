package com.bigdistributor.core.remote.kafka;

import java.util.ArrayList;
import java.util.Collection;


/**
 * In order to manage distribute processing, we will have 3 main topics for remote message
 * Task Done: topic to accumulate information when block task is done.
 * Task Error: We get it if we have any exception in the execution node.
 * Log : any log message or print made in the node.
 */
public enum KafkaTopic {
    TASK_ERROR("TASK_ERROR", true),
    TASK_DONE("TASK_DONE", true),
    LOG("TASK_LOG", true);

    private final String topicString;
    private final boolean important;

    KafkaTopic(String topicString, boolean important) {
        this.topicString = topicString;
        this.important = important;
    }

    public String getTopicString() {
        return topicString;
    }

    public boolean isImportant() {
        return important;
    }

    public static KafkaTopic fromString(String topicString) {
        for (KafkaTopic topic : KafkaTopic.values())
            if (topic.getTopicString().equalsIgnoreCase(topicString))
                return topic;
        return null;
    }


    public static Collection<String> getTopics() {
        Collection<String> list = new ArrayList<>();
        for (KafkaTopic topic : KafkaTopic.values())
            list.add(topic.getTopicString());
        return list;
    }
}

package com.bigdistributor.biglogger.kafka;

import com.bigdistributor.core.remote.kafka.KafkaMessage;
import com.bigdistributor.core.remote.kafka.KafkaTopic;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;

public class KafkaTest {

    @Test
    public void CheckKafkaMessage(){
        KafkaMessage message = new KafkaMessage(KafkaTopic.TASK_DONE,"test",0,"test log");
        String json = message.toString();
        System.out.println(json);
        KafkaMessage message2 = KafkaMessage.fromString(json);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(message,message2));
    }
}

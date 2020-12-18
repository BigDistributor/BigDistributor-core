package com.bigdistributor.core.remote.kafka;

import com.bigdistributor.core.clustering.kafka.KafkaServer;
import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

public class JobConsumer extends ShutdownableThread {
    private static final long TIME_OUT = 10000;
    private final KafkaConsumer<String, String> consumer;

    static Logger logger = Logger.getLogger(JobConsumer.class.getClass());

//	private static Consumer<String, String> createConsumer() {
//		final Properties props = new Properties();
//		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.getUrls());
//		props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
//		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//
//		final Consumer<String, String> consumer = new KafkaConsumer<>(props);
//
//		consumer.subscribe(net.preibisch.bigdistributor.algorithm.clustering.kafka.KafkaTopics.getTopics());
//		// consumer.subscribe(Collections.singletonList(KafkaTopics.TOPIC_DONE_TASK));
//		return consumer;
//	}

    public JobConsumer(KafkaServer kafkaServer) {
        super("KafkaJobConsumer", false);
        logger.setLevel(Level.ERROR);
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer.getFullUrl());
        consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer());
    }

    @Override
    public void doWork() {
        Logger.getRootLogger().setLevel(Level.OFF);
        consumer.subscribe(KafkaTopic.getTopics());
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(TIME_OUT);
            for (ConsumerRecord<String, String> record : records) {
                KafkaLogDispatchManager.addLog(record.value());
            }
        }
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean isInterruptible() {
        return false;
    }

    public static void main(String[] args) {
		logger.info("Start collect remote log..");
        JobConsumer consumerThread = new JobConsumer(new KafkaServer("141.80.219.131" ,9092));
        consumerThread.start();
    }
}
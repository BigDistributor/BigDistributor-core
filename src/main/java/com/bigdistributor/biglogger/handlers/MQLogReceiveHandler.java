package com.bigdistributor.biglogger.handlers;

import kafka.utils.ShutdownableThread;
import org.apache.log4j.Logger;
//TODO fix this and change to MQ receiver that communicate with MQLogReceiveDispatchManager
public class MQLogReceiveHandler extends ShutdownableThread {
    private static final long TIME_OUT = 10000;

    static Logger logger = Logger.getLogger(MQLogReceiveHandler.class.getClass());

    public MQLogReceiveHandler() {
        super("KafkaJobConsumer", false);
//        logger.setLevel(Level.ERROR);
//        Properties props = new Properties();
//        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer.getFullUrl());
//        consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer());
    }

    @Override
    public void doWork() {
//        Logger.getRootLogger().setLevel(Level.OFF);
//        consumer.subscribe(MQTopic.getTopics());
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(TIME_OUT);
//            for (ConsumerRecord<String, String> record : records) {
//                MQLogDispatchManager.addLog(record.value());
//            }
//        }
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
        MQLogReceiveHandler consumerThread = new MQLogReceiveHandler();
        consumerThread.start();
    }
}
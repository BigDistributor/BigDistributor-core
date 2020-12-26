//package com.com.bigdistributor.tasks.bigdistributor.biglogger.handlers;
//
//
//import com.com.bigdistributor.tasks.bigdistributor.biglogger.generic.LogHandler;
//import com.com.bigdistributor.tasks.bigdistributor.core.app.ApplicationMode;
//
//import java.util.logging.Handler;
//import java.util.logging.LogRecord;
//
////TODO fix this and change to MQ receiver that communicate with MQLogReceiveDispatchManager
//// Communicate with MQLogReceiveDispatchManager
//@LogHandler(format = "MQ", modes = {ApplicationMode.DistributionMaster,ApplicationMode.DistributionMasterFiji})
//public class MQLogReceiveHandler extends Handler {
//
//    private static final long TIME_OUT = 10000;
//
////    static Logger logger = Logger.getLogger(MQLogReceiveHandler.class.getClass());
//
//    public MQLogReceiveHandler() {
//        Thread thread = new Thread(() -> {
//
//        })
////        logger.setLevel(Level.ERROR);
////        Properties props = new Properties();
////        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer.getFullUrl());
////        consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer());
//    }
//
//    @Override
//    public void publish(LogRecord record) {
//
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public void close() throws SecurityException {
//
//    }
//
//    @Override
//    public void doWork() {
////        Logger.getRootLogger().setLevel(Level.OFF);
////        consumer.subscribe(MQTopic.getTopics());
////        while (true) {
////            ConsumerRecords<String, String> records = consumer.poll(TIME_OUT);
////            for (ConsumerRecord<String, String> record : records) {
////                MQLogDispatchManager.addLog(record.value());
////            }
////        }
//    }
//
//    @Override
//    public String name() {
//        return null;
//    }
//
//    @Override
//    public boolean isInterruptible() {
//        return false;
//    }
//
//    public static void main(String[] args) {
//        logger.info("Start collect remote log..");
//        MQLogReceiveHandler consumerThread = new MQLogReceiveHandler();
//        consumerThread.start();
//    }
//}
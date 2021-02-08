package com.bigdistributor.biglogger.handlers.mq.kafka;


import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.biglogger.generic.LogHandler;
import com.bigdistributor.biglogger.generic.LogMode;
import com.bigdistributor.biglogger.generic.LogReceiver;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.config.ConfigManager;
import com.bigdistributor.core.config.PropertiesKeys;
import com.bigdistributor.core.remote.mq.MQLogReceiveDispatchManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

//TODO fix this and change to MQ receiver that communicate with MQLogReceiveDispatchManager
// Communicate with MQLogReceiveDispatchManager
@LogHandler(format = "Kafka", type = LogMode.Advance, modes = {ApplicationMode.DistributionMaster, ApplicationMode.DistributionMasterFiji})
public class KafkaLogReceiverHandler implements LogReceiver {

    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private String server;
    private String queue;
    boolean shutdown = false;
    private ExecutorService executorService;

    public KafkaLogReceiverHandler() {
        initServer();
    }

    public KafkaLogReceiverHandler(String server, String queue) {
        this.server = server;
        this.queue = queue;
    }

    @Override
    public void start() {
        init();
        if (executorService == null)
            this.executorService = Executors.newFixedThreadPool(1);
        else
            executorService.shutdownNow();
        Runnable runnable = setRunnable();
        executorService.execute(runnable);
        logger.info("MQ Receiver started .. ");
    }

    private Runnable setRunnable() {
        return () -> {
            try {
                Thread.sleep(1000);
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(server);
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                logger.info(" [*] Waiting for messages");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    logger.info(" [x] Received '" + message + "'");
                    MQLogReceiveDispatchManager.addLog(message);
                };
                while (!shutdown)
                    channel.basicConsume(queue, true, deliverCallback, consumerTag -> {
                    });

                System.out.println("out");
            } catch (IOException | TimeoutException | InterruptedException e) {
                logger.error(e.toString());
                restart();
            }
        };
    }

    private void restart() {
        executorService.shutdownNow();
        Runnable runnable = setRunnable();
        executorService.execute(runnable);
    }

    @Override
    public void stop() {
        shutdown = true;
    }

    private void init() {
        shutdown = false;
        if (this.server == null) {
            initServer();
        }
    }


    private void initServer() {
        this.server = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQServer));
        this.queue = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQQueue));
    }

    public static void main(String[] args) {
        new com.bigdistributor.biglogger.handlers.mq.rabbitmq.MQLogReceiveHandler(String.valueOf(PropertiesKeys.MQServer.getDefaultValue()), String.valueOf(PropertiesKeys.MQQueue.getDefaultValue())).start();
    }

}

package com.bigdistributor.biglogger.handlers;


import com.bigdistributor.biglogger.generic.LogHandler;
import com.bigdistributor.biglogger.generic.LogMode;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.config.ConfigManager;
import com.bigdistributor.core.config.PropertiesKeys;
import com.bigdistributor.core.remote.mq.entities.MQMessage;
import com.bigdistributor.core.remote.mq.entities.MQTopic;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

//TODO change to MQ
@LogHandler(format = "MQ", type = LogMode.Advance, modes = {ApplicationMode.ExecutionNode})
public class MQLogPublishHandler extends Handler {
    private static Channel channel;

    private boolean init = false;


    public MQLogPublishHandler() {
        System.out.println("Kafka Log Handler initiated..");
        ensureReady();
    }

    private String server;
    private String queue;

    @Override
    public void publish(final LogRecord record) {
        if (ensureReady()) {
            try {
                String message;
                if (record.getLevel() == Level.CONFIG) {
                    message = record.getMessage();
                } else {
                    message = new MQMessage(MQTopic.LOG, "0", 0, record.getMessage()).toString();
                }
                channel.basicPublish("", queue, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private synchronized boolean ensureReady() {
        if (!init && this.channel == null)
            initChannel();

        return this.channel != null;
    }

    private void initChannel() {

        if (this.server == null) {
            initServer();
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();
//            channel.queueDeclare(queue, false, true, false, null);
            MQMessage message = new MQMessage(MQTopic.LOG, "0", 0, "Connected");
            channel.basicPublish("", queue, null, message.toString().getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init = true;

    }

    private void initServer() {
        this.server = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQServer));
        this.queue  =  String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQQueue));
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {

        if (this.channel != null) {
            try {
                if (this.channel.isOpen())
                    this.channel.close();
                this.channel = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setServers(final String servers) {
        this.server = servers;
        initChannel();
    }

}

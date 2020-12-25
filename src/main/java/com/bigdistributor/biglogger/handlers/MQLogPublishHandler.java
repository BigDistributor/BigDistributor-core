package com.bigdistributor.biglogger.handlers;


import com.bigdistributor.biglogger.generic.LogHandler;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.config.ConfigManager;
import com.bigdistributor.core.config.PropertiesKeys;
import com.bigdistributor.core.remote.mq.entities.MQMessage;
import com.bigdistributor.core.remote.mq.entities.MQTopic;
import com.bigdistributor.core.remote.mq.entities.Server;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

//TODO change to MQ
@LogHandler(format = "MQ", modes = {ApplicationMode.ExecutionNode})
public class MQLogPublishHandler extends Handler {
    private static Channel channel;

    private boolean init = false;

    public MQLogPublishHandler() {
        System.out.println("Kafka Log Handler initiated..");
        ensureReady();
    }

    private String server;


    @Override
    public void publish(final LogRecord record) {
        if (ensureReady()) {
            try {
                MQMessage message = new MQMessage(MQTopic.LOG, "0", 0, record.getMessage());
                channel.basicPublish("", Server.QUEUE, null, message.toString().getBytes());
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
            server = initServer();
        }
        String server = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQServer));
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();
            channel.queueDeclare(Server.QUEUE, false, true, false, null);
            MQMessage message = new MQMessage(MQTopic.LOG, "0", 0, "Connected");
            channel.basicPublish("", Server.QUEUE, null, message.toString().getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init = true;

    }

    private String initServer() {
        String host = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQServer));
        return host;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
        if (this.channel != null) {
            try {
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

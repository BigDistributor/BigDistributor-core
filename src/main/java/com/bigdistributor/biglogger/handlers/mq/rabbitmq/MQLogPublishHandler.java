package com.bigdistributor.biglogger.handlers;


import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.biglogger.generic.LogHandler;
import com.bigdistributor.biglogger.generic.LogMode;
import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.config.ConfigManager;
import com.bigdistributor.core.config.PropertiesKeys;
import com.bigdistributor.core.remote.mq.entities.MQMessage;
import com.bigdistributor.core.remote.mq.entities.MQTopic;
import com.bigdistributor.core.task.JobID;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

//TODO change to MQ
@LogHandler(format = "RabbitMq", type = LogMode.Advance, modes = {ApplicationMode.ExecutionNode})
public class MQLogPublishHandler extends Handler {
    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static Channel channel;

    private String jobId;
    private String server;
    private String queue;

    public MQLogPublishHandler() {
        logger.info("Remote Log Handler Initialization..");
        ensureReady();
        logger.info("Remote logger initiated.");
    }

    public MQLogPublishHandler(String server, String queue) {
        this.server = server;
        this.queue = queue;
    }


    @Override
    public void publish(final LogRecord record) {
        if (ensureReady()) {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(server);
                Connection connection = factory.newConnection();
                channel = connection.createChannel();
                String message;
                if (record.getLevel() == Level.WARNING) {
                    message = record.getMessage();
                } else {
                    message = new MQMessage(MQTopic.LOG, jobId, 0, record.getMessage()).toString();
                }
                channel.basicPublish("", queue, null, message.getBytes());
//                System.out.println(" [x] Sent '" + message + "'");
                return;
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }


    private synchronized boolean ensureReady() {
        if (this.server == null)
            initServer();
        this.jobId = JobID.get();
        return this.server != null;
    }

    private void initServer() {
        this.server = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQServer));
        this.queue = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQQueue));
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
    }

}

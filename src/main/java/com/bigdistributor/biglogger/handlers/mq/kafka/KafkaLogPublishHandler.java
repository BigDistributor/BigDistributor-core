package com.bigdistributor.biglogger.handlers.mq.kafka;


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
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.lang.invoke.MethodHandles;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

@LogHandler(format = "Kafka", type = LogMode.Advance, modes = {ApplicationMode.ExecutionNode})
public class KafkaLogPublishHandler extends Handler {
    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static Channel channel;

    private String jobId;
    private String queue;
    private String url;
    private KafkaProducer<String, String> producer;

    public KafkaLogPublishHandler() {
        logger.info("Remote Log Handler Initialization..");
        ensureReady();
        logger.info("Remote logger initiated.");
    }

    public KafkaLogPublishHandler(String url, String queue) {
        this.url = url;
        this.queue = queue;
    }


    @Override
    public void publish(final LogRecord logRecord) {
        if (ensureReady()) {
            String message;
            if (logRecord.getLevel() == Level.WARNING) {
                message = logRecord.getMessage();
            } else {
                message = new MQMessage(MQTopic.LOG, jobId, 0, logRecord.getMessage()).toString();
            }
            ProducerRecord<String,String> record = new ProducerRecord(queue,message);
            producer.send(record);
            return;
        }
    }


    private synchronized boolean ensureReady() {
        if (this.url == null)
            initServer();
        this.jobId = JobID.get();
        initProducer();
        return this.producer != null;
    }

    private void initProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(properties);
    }

    private void initServer() {
        String server = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQServer));
        String port = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQServerPort));

        this.url = server + ":" + port;
        this.queue = String.valueOf(ConfigManager.getConfig().get(PropertiesKeys.MQQueue));

        System.out.println("Server: "+url);
        System.out.println("Queue: "+queue);
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

    public void setServers(final String url) {
        this.url = url;
    }

}

package com.bigdistributor.biglogger.remote.kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;

public class KafkaLogDispatchManager {
	Logger logger = Logger.getLogger(this.getClass());



	public static void addLog(String log) {
		KafkaMessage message = KafkaMessage.fromString(log);

	}
}

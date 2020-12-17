package com.bigdistributor.core.clustering.kafka;

import com.bigdistributor.core.remote.entities.Server;

public class KafkaServer extends Server{
	public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
	public static final int CONNECTION_TIMEOUT = 100000;

	private final int bufferSize;
	private final int connectionTimeout;

	public KafkaServer(String host, int port){
		this(host,port,KAFKA_PRODUCER_BUFFER_SIZE,CONNECTION_TIMEOUT);
	}

	public KafkaServer(String host, int port, int bufferSize, int connectionTimeout) {
		super(host,port);
		this.bufferSize = bufferSize;
		this.connectionTimeout = connectionTimeout;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	//	public static String KAFKA_SERVER_URL = "141.80.219.131";
//	public static final int KAFKA_SERVER_PORT = 9092;
//	public static final int[] PORTS = new int[] {9092,9093,9094};
//
//	public static final String CLIENT_ID = "mzouink";
//	public static String jobId = "0";
//
//	public static void setURL(String url) {
//		KAFKA_SERVER_URL = url;
//	}
//
//	public static String getUrls() {
//		String[] str = new String[PORTS.length] ;
//		for(int i =0 ; i< PORTS.length;i++) {
//			str[i] = KAFKA_SERVER_URL+":"+PORTS[i];
//		}
//		return String.join(",", str);
//	}
//
//	public static void setJobId(String jobId) {
//		KafkaProperties.jobId = jobId;
//	}
//
//	public static String getJobId() {
//		return jobId;
//	}
}

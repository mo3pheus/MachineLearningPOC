package com.garmin.analytics.feature;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.garmin.analytics.storm.common.domain.UserSync;
import com.garmin.analytics.storm.common.domain.converter.UserSyncConverter;
import com.garmin.analytics.storm.common.domain.pb.UserSyncProtos.UserSyncProto;
import com.google.protobuf.InvalidProtocolBufferException;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaConsumer extends Thread {

	final static String clientId = "SimpleConsumerDemoClient2";
	final static String TOPIC = "test_scheduled_scoring_queue";
	ConsumerConnector consumerConnector;

	public static void main(String[] argv) throws UnsupportedEncodingException {
		KafkaConsumer helloKafkaConsumer = new KafkaConsumer();
		helloKafkaConsumer.start();
	}

	public KafkaConsumer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect",
				"olaxtx-gchn03.garmin.com,olaxtx-gchn02.garmin.com,olaxtx-gchn04.garmin.com:2181");
		properties.put("group.id", "test-group");
		ConsumerConfig consumerConfig = new ConsumerConfig(properties);
		consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
	}
	
	@Override
	public void run() {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(TOPIC, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector
				.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(TOPIC).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext())
			try {
				UserSync received = UserSyncConverter.fromProtoToDmain(UserSyncProto.parseFrom(it.next().message()));
				System.out.println("Received userPk="+received.getUserPK() + ", syncTime="+received.getSyncTimeMillis());
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}

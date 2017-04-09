package com.garmin.hadoop.sample;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.garmin.hadoop.shared.proto.ComputedFeatureProtoOuterClass;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;

import com.garmin.hadoop.shared.proto.ComputedFeatureProtoOuterClass.ComputedFeatureProto;
import com.google.protobuf.InvalidProtocolBufferException;
import com.garmin.hadoop.shared.proto.EpochStepQueryProto.EpochStepQueryData;

public class KafkaConsumer extends Thread {

	final static String clientId = "SimpleConsumerDemoClient";
	final static String TOPIC = "EpochPredictInlet2";
	ConsumerConnector consumerConnector;

	public static void main(String[] argv) throws UnsupportedEncodingException {
		KafkaConsumer helloKafkaConsumer = new KafkaConsumer();
		helloKafkaConsumer.start();
	}

	public KafkaConsumer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "olaxtx-gchin01.garmin.com:2181");
		properties.put("group.id", "test-group");
		ConsumerConfig consumerConfig = new ConsumerConfig(properties);
		consumerConnector = Consumer
				.createJavaConsumerConnector(consumerConfig);
	}

	@Override
	public void run() {
		/*
		 * Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		 * topicCountMap.put(TOPIC, new Integer(1)); Map<String,
		 * List<KafkaStream<byte[], byte[]>>> consumerMap =
		 * consumerConnector.createMessageStreams(topicCountMap);
		 */
		List<KafkaStream<byte[], byte[]>> consumerMap = consumerConnector
				.createMessageStreamsByFilter(new Whitelist(
						"EpochPredictInlet"));
		KafkaStream<byte[], byte[]> stream = consumerMap.get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		try {
			while (it.hasNext()) {
				System.out.println(EpochStepQueryData.parseFrom(
						it.next().message().clone()).getCurrentSteps());
			}
		} catch (InvalidProtocolBufferException pb) {

		}

	}
}

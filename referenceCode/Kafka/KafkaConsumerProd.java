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

public class KafkaConsumerProd extends Thread {

	final static String clientId = "SimpleConsumerDemoClient2";
	final static String TOPIC = "EpochPredictInlet";
	ConsumerConnector consumerConnector;

	public static void main(String[] argv) throws UnsupportedEncodingException {
		KafkaConsumerProd helloKafkaConsumer = new KafkaConsumerProd();
		helloKafkaConsumer.start();
	}

	public KafkaConsumerProd() {
		Properties properties = new Properties();
		/*
		 * olaxpx-gchn22.garmin.com:2181,olaxpx-gchn23.garmin.com:2181,olaxpx-gchn24
		 * .garmin.com:2181
		 */
		properties
				.put("zookeeper.connect",
						"olaxpx-gchn22.garmin.com:2181,olaxpx-gchn23.garmin.com:2181,olaxpx-gchn24.garmin.com:2181");
		properties.put("group.id", "test-group");
		ConsumerConfig consumerConfig = new ConsumerConfig(properties);
		consumerConnector = Consumer
				.createJavaConsumerConnector(consumerConfig);
	}

	// @Override
	public void run1() {
		List<KafkaStream<byte[], byte[]>> consumerMap = consumerConnector
				.createMessageStreamsByFilter(new Whitelist("EpochPredictInlet"));
		KafkaStream<byte[], byte[]> stream = consumerMap.get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		try {
			while (it.hasNext()) {
				System.out.println("\n Hello");
				System.out.println(EpochStepQueryData.parseFrom(
						it.next().message().clone()).getCurrentSteps());
			}
		} catch (InvalidProtocolBufferException pb) {
			System.out.println("\n Here I am ");
		}

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
			System.out.println(new String(it.next().message()));

	}
}

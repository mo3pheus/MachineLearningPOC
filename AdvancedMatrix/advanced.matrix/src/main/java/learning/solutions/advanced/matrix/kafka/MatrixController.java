/**
 * 
 */
package learning.solutions.advanced.matrix.kafka;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.protobuf.InvalidProtocolBufferException;

import learning.solutions.advanced.matrix.MatrixCommunication.MatrixCall;
import learning.solutions.advanced.matrix.MatrixCommunication.MatrixCall.Builder;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * @author sanketkorgaonkar
 *
 */
public class MatrixController extends Thread{

}

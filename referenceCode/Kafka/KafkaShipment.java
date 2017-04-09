package com.garmin.internal.dataTeam.tools.loadSimulator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.garmin.analytics.common.kafka.generated.pb.KafkaProto.KafkaMessageWrapper;
import com.garmin.connect.persistence.wellness.domain.pb.WellnessUploadedFileProto.WellnessUploadedFile;

public class KafkaShipment
{

    private Producer<String, byte[]> kafkaTrain;
    private InputStream              inputStream;
    private Properties               kafkaProperties;
    private long[]                   userPKList;
    private long                     currentTimestamp;

    public KafkaShipment(KafkaShipmentBuilder kafkaShipmentBuilder) throws Exception
    {
        /*
         * Set up Kafka producer
         */
        inputStream = new FileInputStream(kafkaShipmentBuilder.propertyFileLocation);
        kafkaProperties = new Properties();
        kafkaProperties.load(inputStream);
        kafkaProperties.put("sourceTopic", kafkaShipmentBuilder.sourceTopic);
        kafkaTrain = new Producer<String, byte[]>(new ProducerConfig(kafkaProperties));

        /*
         * Grab user data
         */
        this.userPKList = kafkaShipmentBuilder.userPKList;
        this.currentTimestamp = kafkaShipmentBuilder.currentTimestamp;
    }

    public void sendMessage() throws InterruptedException
    {
        for (int i = 0; i < this.userPKList.length; i++)
        {
            WellnessUploadedFile.Builder builder = WellnessUploadedFile.newBuilder();
            builder.setUserProfilePk(userPKList[i]);
            builder.setCreateTimestampGmt((new DateTime(this.currentTimestamp)).withZone(DateTimeZone.UTC).getMillis());

            KafkaMessageWrapper.Builder wrapperProtoBuilder = KafkaMessageWrapper.newBuilder();
            wrapperProtoBuilder.setMsg(builder.build().toByteString());
            wrapperProtoBuilder.setCreatedTime(System.currentTimeMillis());
            byte[] outputKafkaBytes = wrapperProtoBuilder.build().toByteArray();
            kafkaTrain.send(new KeyedMessage<String, byte[]>(kafkaProperties.getProperty("sourceTopic"), outputKafkaBytes));
            System.out.println(" Sending " + builder.build().toString() + " to " + kafkaProperties.getProperty("sourceTopic"));
            Thread.sleep(5);
        }
    }

    public void cleanUp() throws IOException
    {
        this.inputStream.close();
        this.kafkaTrain.close();
    }

    public static class KafkaShipmentBuilder
    {
        private long[] userPKList;
        private long   currentTimestamp;
        private String sourceTopic;
        private String propertyFileLocation;

        /*
         * public KafkaShipmentBuilder() { }
         */

        public KafkaShipmentBuilder withPropertyFileAt(String fileLocation)
        {
            this.propertyFileLocation = fileLocation;
            return this;
        }

        public KafkaShipmentBuilder withSourceTopic(String sourceTopic)
        {
            this.sourceTopic = sourceTopic;
            return this;
        }

        public KafkaShipmentBuilder withUserPKList(long[] userPKs)
        {
            this.userPKList = userPKs;
            return this;
        }

        public KafkaShipmentBuilder withCurrentTime(long currentTimestamp)
        {
            this.currentTimestamp = currentTimestamp;
            return this;
        }

        public KafkaShipment build() throws Exception
        {
            return new KafkaShipment(this);
        }
    }
}

package com.garmin.internal.dataTeam.tools.loadSimulator;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

public class SyncSimulationDriver
{

    public static KafkaShipment.KafkaShipmentBuilder kafkaProducerBuilder;
    public static final String                       PROP_FILE_LOCATION         = "C:\\hadoopProjects\\PropertyFiles\\kafkaProperties.properties";
    public static final String                       SOURCE_TOPIC               = "wellness_uploaded_file_queue";
    public static final String                       DATE_PATTERN               = "yyyy-MM-dd HH:mm:ss";
    public static final String                       SIMULATE_DATE              = "2016-08-29 00:00:00";
    public static final long[]                       MASTER_USERPKLIST_MULTIPLE =
                                                                                { 38, 81772, 1023343, 1076960, 1084728, 1317531, 2460525, 3109738, 3111331, 3541951, 3975354, 5874364, 6557839,
        6855447, 7058583, 8622308, 9527319, 9624064, 9759743, 10674970, 12126750, 1454620 };
    public static final long                         DURATION_MINUTES             = 2500l;
    public static KafkaShipment                      shipment;

    public static final void main(String[] args)
    {
        System.out.println("Welcome to load simulation");
        try
        {
            SimpleDateFormat df = new SimpleDateFormat();
            df.applyPattern(DATE_PATTERN);
            DateTime simDate = new DateTime(df.parse(SIMULATE_DATE));

            kafkaProducerBuilder = (new KafkaShipment.KafkaShipmentBuilder()).withSourceTopic(SOURCE_TOPIC).withCurrentTime(simDate.getMillis()).withPropertyFileAt(PROP_FILE_LOCATION)
                .withUserPKList(MASTER_USERPKLIST_MULTIPLE);

            shipment = kafkaProducerBuilder.build();
            simulateLoad();
            shipment.cleanUp();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void simulateLoad() throws Exception
    {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TimeUnit.MINUTES.toMillis(DURATION_MINUTES);

        for (long time = startTime; time < endTime; time = System.currentTimeMillis())
        {
            shipment.sendMessage();
        }
    }
}

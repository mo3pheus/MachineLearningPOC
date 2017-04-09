#Deploying floor feature topology on test cluster
#!/bin/bash
JAR_FILE=`find -name "analytics-storm-daily-summary-features*.jar" -print | sort -rV | head -1`
VERSION=`echo $JAR_FILE | grep -Po '(\d+)+([.](\d)+)(.)+(?=.jar)'`
VERSION_DASH=${VERSION//\./-}
TOPOLOGY_NAME="analytics-storm-daily-summary-features-topology-$VERSION_DASH";
INSIGHTS_NAMESPACE=insights
WELLNESS_NAMESPACE=gcwellness
CONSUMER_GROUP=DailySummaryFeaturesConsumerGroup
CF_KAFKA_TOPIC=computed_feature
TRIGGER_KAFKA_TOPIC=optimized_user_sync_queue

storm jar $JAR_FILE com.garmin.analytics.storm.daily.summary.topology.Topology -stormTopologyName $TOPOLOGY_NAME \
-triggerKafkaTopic $TRIGGER_KAFKA_TOPIC \
-kafkaZkHosts olaxpx-gchn22.garmin.com:2181,olaxpx-gchn23.garmin.com:2181,olaxpx-gchn24.garmin.com:2181 \
-kafkaBrokerList olaxpx-gchn22.garmin.com:6667,olaxpx-gchn23.garmin.com:6667,olaxpx-gchn24.garmin.com:6667 \
-HBaseZkHosts olaxpx-gchn22.garmin.com,olaxpx-gchn23.garmin.com,olaxpx-gchn24.garmin.com \
-insightsNamespace $INSIGHTS_NAMESPACE -wellnessNamespace $WELLNESS_NAMESPACE \
-featureProperties feature.properties \
-topologyProperties topology.properties \
-kafkaConsumerGroup $CONSUMER_GROUP \
-cfKafkaTopic $CF_KAFKA_TOPIC

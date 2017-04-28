import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class ConsumerClass {

	public static void main(String[] args) {
	Properties props = new Properties();
    props.put("zookeeper.connect", "localhost:2181");
    props.put("group.id", "MyKafkaConsumer");
    ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    Map<String, Integer> topicCount = new HashMap<>();
    topicCount.put("KafkaJavaApi", 1);

    Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
    List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get("KafkaJavaApi");
    for (final KafkaStream stream : streams) {
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            System.out.println("Message from Single Topic: " + new String(it.next().message()));
        }
    }
    if (consumer != null) {
        consumer.shutdown();
    }
    }

}

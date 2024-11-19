package com.example.kafkaexample;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    public static void main(String[] args) {
        String topicName = "demo-topic";
        String groupId = "demo-group";

        // Kafka Consumer 설정
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        // Consumer 생성
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 토픽 구독
        consumer.subscribe(Collections.singletonList(topicName));

        try {
            while (true) {
                // 메시지 폴링 (지속적으로 대기)
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("받은 메시지 : 키=%s, 값=%s, 오프셋=%d%n",
                            record.key(), record.value(), record.offset());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}

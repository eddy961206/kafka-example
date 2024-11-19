package com.example.kafkaexample;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProducerExample {
    public static void main(String[] args) {
        String topicName = "demo-topic";

        // kafka Producer 설정
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all"); // 모든 복제본이 메시지를 수신해야 완료
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        // Producer 생성
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        try {
            for (int i = 0; i <= 10; i++) {
                String key = "key" + i;
                String value = "Hello Kafka " + i;

                ProducerRecord<String, String> record =
                        new ProducerRecord<>(topicName, key, value);

                // 메시지 보내기 (비동기)
                Future<RecordMetadata> future = producer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        System.out.printf("메시지 보내기 성공 : 토픽=%s, 파티션=%d, 오프셋=%d%n",
                                metadata.topic(), metadata.partition(), metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                });

                // 잠시 대기 (옵션)
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }

}

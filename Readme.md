# Kafka Project Setup Guide

## 1. Prerequisites
- Install Docker

## 2. Initial Setup
프로젝트 디렉토리()로 이동하고 명령 프롬프트를 엽니다

## 3. Start Docker Compose

```bash
docker-compose up -d
```

> Note: `-d` 옵션은 컨테이너를 백그라운드에서 실행합니다. 
> <br>로그를 보려면 `docker-compose logs -f` 사용

## 4. Verify Setup

### 4.1 실행 중인 컨테이너 확인

```bash
docker-compose ps
```
예상 출력:

```bash
Name                     Command                  State               Ports
--------------------------------------------------------------------------------
kafka-intellij-example_kafka_1   start-kafka.sh                Up      0.0.0.0:9092->9092/tcp
kafka-intellij-example_zookeeper_1   /bin/sh -c /usr/bin/zkSu ...   Up      0.0.0.0:2181->2181/tcp
```

### 4.2 Kafka 브로커 확인
1. Kafka 컨테이너 접속:
```bash
docker-compose exec kafka bash
```

2. Kafka 토픽 목록 확인:
```bash
kafka-topics.sh --list --bootstrap-server localhost:9092
```

예상 출력:
```bash
demo-topic
```

3. 컨테이너 종료:
```bash
exit
```

## 5. 애플리케이션 실행

### 5.1 Consumer 실행
1. IntelliJ에서 `KafkaConsumerExample.java` 열기
2. 실행 버튼(녹색 ▶️) 클릭 또는 우클릭 후 'Run KafkaConsumerExample.main()' 선택

예상 콘솔 출력:
```plaintext
받은 메시지: 키=key1, 값=Hello Kafka 1, 오프셋=0
받은 메시지: 키=key2, 값=Hello Kafka 2, 오프셋=1
```
> Note: Consumer는 새로운 메시지를 계속 대기합니다

### 5.2 Producer 실행
1. IntelliJ에서 `KafkaProducerExample.java` 열기
2. 실행 버튼(녹색 ▶️) 클릭 또는 우클릭 후 'Run KafkaProducerExample.main()' 선택

예상 콘솔 출력:
```plaintext
메시지 보내기 성공: 토픽=demo-topic, 파티션=0, 오프셋=0
메시지 보내기 성공: 토픽=demo-topic, 파티션=0, 오프셋=1
```


Consumer 콘솔에서 실시간 메시지 확인:
```plaintext
받은 메시지: 키=key1, 값=Hello Kafka 1, 오프셋=0
받은 메시지: 키=key2, 값=Hello Kafka 2, 오프셋=1받은 메시지: 키=key2, 값=Hello Kafka 2, 오프셋=1...
```
package com.edigest.userservice.events;

import com.edigest.userservice.models.UserDto;
import com.edigest.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class AuthServiceConsumer {
    @Autowired
    private UserService userService;
    private final ReentrantLock lock = new ReentrantLock();
    private final ObjectMapper objectMapper = new ObjectMapper(); //
    @KafkaListener(topics ="${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listener(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
        lock.lock();
        try {
            String jsonValue = data.value();
            UserDto userDto = objectMapper.readValue(jsonValue, UserDto.class);
            userService.createOrUpdateUser(userDto.getUserId(), userDto);
            acknowledgment.acknowledge();
            log.info("Received User: {}", userDto.toString());
        } catch (Exception e) {
            log.error("Error deserializing Kafka message: ", e);
        } finally {
            lock.unlock();
        }
    }
}

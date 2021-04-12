/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labProject22.FlightTracker.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

//    @Value("${topic.name.producer}")
    private String topicName = "user";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message){
        System.out.println("Payload enviado: " + message);
//        log.info("Payload enviado: {}" message);
        kafkaTemplate.send(topicName, message);
    }

}
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LogManager.getLogger(FlightController.class);

    public void send(String topic, String message){
        System.out.println("Payload enviado: " + message);
        logger.info("Payload enviado: {}", message);
        kafkaTemplate.send(topic, message);
    }

}
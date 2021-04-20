/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labProject22.FlightTracker.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    private static final Logger logger = LogManager.getLogger(FlightController.class);
    private String msg = "";

    @KafkaListener(topics = "plane", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload){
        logger.info("Topic: {}", "plane");
        // logger.info("key: {}", payload.key());
        // logger.info("Headers: {}", payload.headers());
        // logger.info("Partion: {}", payload.partition());
        logger.info("Order: {}", payload.value());
        msg = payload.value();
    }

    public String getMessage(){
        return msg;
    }
}
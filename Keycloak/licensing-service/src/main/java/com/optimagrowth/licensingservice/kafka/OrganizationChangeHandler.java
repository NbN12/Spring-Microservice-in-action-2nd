package com.optimagrowth.licensingservice.kafka;

import com.optimagrowth.licensingservice.event.model.OrganizationChangeModel;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBinding(Sink.class)
@Slf4j
public class OrganizationChangeHandler {

    @StreamListener(Sink.INPUT)
    public void loggerSink(OrganizationChangeModel orgChange) {
        log.info("Received an {} event for organization id {}", orgChange.getAction(), orgChange.getOrganizationId());
    }

    // @StreamListener("inboundOrgChanges")
    // public void loggerSink(OrganizationChangeModel organization) {
    // log.info("Received a message of type " + organization.getType());
    // log.info("Received a message with an event {} from the organization service
    // for the organization id {} ",
    // organization.getType(), organization.getType());
    // }
}

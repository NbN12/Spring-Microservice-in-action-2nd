package com.optimagrowth.licensingservice.event.config;

import com.optimagrowth.licensingservice.event.model.OrganizationChangeModel;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SubConfig {

    @StreamListener(target = Sink.INPUT)
    public void loggerSink(OrganizationChangeModel orgChange) {
        log.info("Received an {} event for organization id {}", orgChange.getAction(), orgChange.getOrganizationId());
    }
}

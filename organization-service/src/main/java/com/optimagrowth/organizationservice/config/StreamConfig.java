package com.optimagrowth.organizationservice.config;

import com.optimagrowth.organizationservice.event.CustomChannels;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(CustomChannels.class)
public class StreamConfig {

}

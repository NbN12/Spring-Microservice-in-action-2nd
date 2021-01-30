package com.optimagrowth.licensingservice;

import com.optimagrowth.licensingservice.event.model.OrganizationChangeModel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
@EnableFeignClients
@EnableBinding(Sink.class)
public class LicensingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicensingServiceApplication.class, args);
	}

}

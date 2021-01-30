package com.optimagrowth.organizationservice.event.source;

import com.optimagrowth.organizationservice.event.model.OrganizationChangeModel;
import com.optimagrowth.organizationservice.utils.UserContext;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class SimpleSourceBean {

    private Source source;

    public void publishOrganizationChange(String action, String organizationId) {
        log.info("Sending Kafka message {} for Organization Id: {}", action, organizationId);

        OrganizationChangeModel change = OrganizationChangeModel.builder()
                .type(OrganizationChangeModel.class.getTypeName()).action(action).organizationId(organizationId)
                .correlationId(UserContext.getCorrelationId()).build();

        source.output().send(MessageBuilder.withPayload(change).build());
    }

}

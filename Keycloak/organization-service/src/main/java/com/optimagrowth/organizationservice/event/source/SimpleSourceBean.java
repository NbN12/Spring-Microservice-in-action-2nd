package com.optimagrowth.organizationservice.event.source;

import com.optimagrowth.organizationservice.event.CustomChannels;
import com.optimagrowth.organizationservice.event.model.OrganizationChangeModel;
import com.optimagrowth.organizationservice.utils.UserContext;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class SimpleSourceBean {

    private CustomChannels customChannels;

    public void publishOrganizationChange(String action, String organizationId) {
        log.info("Sending Kafka message {} for Organization Id: {}", action, organizationId);

        OrganizationChangeModel change = OrganizationChangeModel.builder()
                .type(OrganizationChangeModel.class.getTypeName()).action(action).organizationId(organizationId)
                .correlationId(UserContext.getCorrelationId()).build();

        // source.output().send(MessageBuilder.withPayload(change).build());
        customChannels.outboundOrgChanges().send(MessageBuilder.withPayload(change).build());
    }

}

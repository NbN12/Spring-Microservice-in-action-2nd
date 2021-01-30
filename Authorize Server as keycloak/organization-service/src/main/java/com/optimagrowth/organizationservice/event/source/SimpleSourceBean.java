package com.optimagrowth.organizationservice.event.source;

import com.optimagrowth.organizationservice.event.ActionEnum;
import com.optimagrowth.organizationservice.event.model.OrganizationChangeModel;
import com.optimagrowth.organizationservice.utils.UserContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
// @RequiredArgsConstructor
@AllArgsConstructor
public class SimpleSourceBean {

    private Source source;

    public void publishOrganizationChange(String action, String organizationId) {
        log.info("Sending Kafka message {} for Organization Id: {}", action, organizationId);

        OrganizationChangeModel change = new OrganizationChangeModel(OrganizationChangeModel.class.getTypeName(),
                action, organizationId, UserContext.getCorrelationId());

        source.output().send(MessageBuilder.withPayload(change).build());
    }

}

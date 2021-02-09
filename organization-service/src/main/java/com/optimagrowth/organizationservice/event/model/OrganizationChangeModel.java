package com.optimagrowth.organizationservice.event.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationChangeModel {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;
}

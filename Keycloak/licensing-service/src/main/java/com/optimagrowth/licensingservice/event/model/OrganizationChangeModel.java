package com.optimagrowth.licensingservice.event.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrganizationChangeModel {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;
}

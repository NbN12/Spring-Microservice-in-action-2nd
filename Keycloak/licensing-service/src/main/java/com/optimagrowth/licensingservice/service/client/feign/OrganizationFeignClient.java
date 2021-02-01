package com.optimagrowth.licensingservice.service.client.feign;

import com.optimagrowth.licensingservice.model.Organization;
import com.optimagrowth.licensingservice.service.client.feign.config.FeignClientConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "organization-service", configuration = FeignClientConfig.class)
public interface OrganizationFeignClient {

    @GetMapping(value = "/v1/organization/{organizationId}", consumes = "application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}

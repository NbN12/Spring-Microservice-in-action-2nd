package com.optimagrowth.licensingservice.service.client;

import java.util.Objects;

import com.optimagrowth.licensingservice.model.Organization;
import com.optimagrowth.licensingservice.repository.OrganizationRedisRepository;
import com.optimagrowth.licensingservice.service.client.feign.OrganizationFeignClient;
import com.optimagrowth.licensingservice.utils.UserContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrganizationClient {

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private OrganizationRedisRepository organizationRedisRepository;

    private Organization checkRedisCache(String organizationId) {
        try {
            return organizationRedisRepository.findById(organizationId).orElse(null);
        } catch (Exception e) {
            log.error("Error encountered while trying to retrieve organization {} check Redis Cache. Exception {}",
                    organizationId, e);
            return null;
        }
    }

    private void cacheOrganizationObject(Organization organization) {
        try {
            organizationRedisRepository.save(organization);
        } catch (Exception e) {
            log.error("Unable to cache organization {} in Redis. Exception {}", organization.getId(), e);
        }
    }

    public Organization getOrganization(String organizationId) {
        log.info("In Licensing Service.getOrganization: {}", UserContextHolder.getContext().getCorrelationId());
        Organization organization = checkRedisCache(organizationId);

        if (!Objects.isNull(organization)) {
            log.info("I have successfully retrieved an organization {} from the redis cache: {}", organizationId,
                    organization);
            return organization;
        }

        log.info("Unable to locate organization from the redis cache: {}.", organizationId);

        organization = organizationFeignClient.getOrganization(organizationId);
        if (!Objects.isNull(organization)) {
            cacheOrganizationObject(organization);
        }

        return organization;

    }
}

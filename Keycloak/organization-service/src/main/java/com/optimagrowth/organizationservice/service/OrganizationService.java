package com.optimagrowth.organizationservice.service;

import java.util.Optional;
import java.util.UUID;

import com.optimagrowth.organizationservice.event.ActionEnum;
import com.optimagrowth.organizationservice.event.source.SimpleSourceBean;
import com.optimagrowth.organizationservice.model.Organization;
import com.optimagrowth.organizationservice.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@SuppressWarnings("unused")
@Slf4j
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = organizationRepository.findById(organizationId);
        simpleSourceBean.publishOrganizationChange(ActionEnum.GET.name(), organizationId);
        return opt.isPresent() ? opt.get() : null;
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = organizationRepository.save(organization);
        simpleSourceBean.publishOrganizationChange(ActionEnum.CREATED.name(), organization.getId());
        return organization;
    }

    public void update(Organization organization) {
        organizationRepository.save(organization);
        simpleSourceBean.publishOrganizationChange(ActionEnum.UPDATED.name(), organization.getId());
    }

    public void delete(String organizationId) {
        organizationRepository.deleteById(organizationId);
    }
}

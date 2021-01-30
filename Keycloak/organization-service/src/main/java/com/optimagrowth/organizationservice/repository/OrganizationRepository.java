package com.optimagrowth.organizationservice.repository;

import java.util.Optional;

import com.optimagrowth.organizationservice.model.Organization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
    Optional<Organization> findById(String organizationId);
}

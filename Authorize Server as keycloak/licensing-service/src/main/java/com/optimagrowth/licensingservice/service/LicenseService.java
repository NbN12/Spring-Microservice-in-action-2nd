package com.optimagrowth.licensingservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.optimagrowth.licensingservice.config.ServiceConfig;
import com.optimagrowth.licensingservice.model.License;
import com.optimagrowth.licensingservice.model.Organization;
import com.optimagrowth.licensingservice.repository.LicenseRepository;
import com.optimagrowth.licensingservice.service.client.OrganizationFeignClient;
import com.optimagrowth.licensingservice.utils.UserContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LicenseService {

    @Autowired
    private MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private ServiceConfig config;

    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new IllegalArgumentException(String.format(
                    messages.getMessage("license.search.error.message", null, null), licenseId, organizationId));
        }

        Organization organization = retrieveOrganizationInfo(organizationId);

        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }
        return license.withComment(config.getExampleProperty());
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);

        return license.withComment(config.getExampleProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);

        return license.withComment(config.getExampleProperty());
    }

    public String deleteLicense(String licenseId) {
        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage("license.delete.message", null, null), licenseId);
        return responseMessage;

    }

    @CircuitBreaker(name = "organizationService")
    private Organization retrieveOrganizationInfo(String organizationId) {
        return organizationFeignClient.getOrganization(organizationId);
    }

    @CircuitBreaker(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
    @RateLimiter(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
    @Retry(name = "retryLicenseService", fallbackMethod = "buildFallbackLicenseList")
    @Bulkhead(name = "bulkheadLicenseService", type = Type.THREADPOOL, fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicenesesByOrganization(String organizationId) throws TimeoutException {
        log.debug("LicenseService:getLicensesByOrganization: {}", UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    @SuppressWarnings("unused")
    private List<License> buildFallbackLicenseList(String organizationId, Throwable t) {
        List<License> fallBackList = new ArrayList<>();
        License license = new License();
        license.setLicenseId("0000000-00-00000");
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        fallBackList.add(license);
        return fallBackList;
    }

    // This code only for testing >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void randomlyRunLong() throws TimeoutException {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3)
            sleep();
    }

    private void sleep() throws TimeoutException {
        try {
            Thread.sleep(5000);
            throw new TimeoutException();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< This code only for testing
}

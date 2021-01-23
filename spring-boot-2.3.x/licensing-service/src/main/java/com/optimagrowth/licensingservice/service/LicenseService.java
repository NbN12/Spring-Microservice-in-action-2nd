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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {

    @Autowired
    private MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private ServiceConfig config;

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

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

    public List<License> getLicenses() {
        List<License> licenses = new ArrayList<>();
        licenseRepository.findAll().forEach(l -> licenses.add(l));
        return licenses;
    }

    // @CircuitBreaker(name = "organizationService")
    private Organization retrieveOrganizationInfo(String organizationId) {
        return organizationFeignClient.getOrganization(organizationId);
    }

    // This code only for testing >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @SuppressWarnings("unused")
    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3)
            sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
            throw new TimeoutException("");
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } catch (TimeoutException e) {
            logger.error(e.getMessage());
        }
    }
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< This code only for testing

    // @CircuitBreaker(name = "licenseService", fallbackMethod =
    // "buildFallbackLicenseList")
    // @Bulkhead(name = "bulkheadLicenseService", type = Type.THREADPOOL,
    // fallbackMethod = "buildFallbackLicenseList")
    // @Retry(name = "retryLicenseService", fallbackMethod =
    // "buildFallbackLicenseList")
    // @RateLimiter(name = "licenseService", fallbackMethod =
    // "buildFallbackLicenseList")
    public List<License> getLicenesesByOrganizationId(String organizationId) {
        logger.debug("LicenseService:getLicensesByOrganization: {}", UserContextHolder.getContext().getCorrelationId());
        // randomlyRunLong();
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
}

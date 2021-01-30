package com.optimagrowth.organizationservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@ToString
public class Organization {

    @Id
    @Column(nullable = false, name = "organization_id")
    String id;

    @Column(nullable = false, name = "name")
    String name;

    @Column(nullable = false, name = "contact_name")
    String contactName;

    @Column(nullable = false, name = "contact_email")
    String contactEmail;

    @Column(nullable = false, name = "contact_phone")
    String contactPhone;
}

package org.khasanof.migration.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.khasanof.core.annotation.Common;
import org.khasanof.core.domain.AbstractAuditingEntity;

/**
 * A OrganizationMicroservice.
 */
@Entity
@Common
@Table(name = "mg_organization_microservice")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationMicroservice extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @NotNull
    @Column(name = "updated", nullable = false)
    private Boolean updated;

    @JsonIgnoreProperties(value = { "organizationMicroservice" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Microservice microservice;

    @JsonIgnoreProperties(value = { "organizationMicroservice" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Organization organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationMicroservice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public OrganizationMicroservice version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getUpdated() {
        return this.updated;
    }

    public OrganizationMicroservice updated(Boolean updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public Microservice getMicroservice() {
        return this.microservice;
    }

    public void setMicroservice(Microservice microservice) {
        this.microservice = microservice;
    }

    public OrganizationMicroservice microservice(Microservice microservice) {
        this.setMicroservice(microservice);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public OrganizationMicroservice organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationMicroservice)) {
            return false;
        }
        return getId() != null && getId().equals(((OrganizationMicroservice) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationMicroservice{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}

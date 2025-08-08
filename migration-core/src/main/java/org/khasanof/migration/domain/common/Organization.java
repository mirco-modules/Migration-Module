package org.khasanof.migration.domain.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.khasanof.core.annotation.Common;
import org.khasanof.core.domain.AbstractAuditingEntity;
import org.khasanof.migration.domain.common.enumeration.OrganizationStatus;

/**
 * A Organization.
 */
@Entity
@Common
@Table(name = "organization")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Organization extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrganizationStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public Organization tenantId(Long tenantId) {
        this.setTenantId(tenantId);
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public OrganizationStatus getStatus() {
        return this.status;
    }

    public Organization status(OrganizationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrganizationStatus status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return getId() != null && getId().equals(((Organization) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", tenantId=" + getTenantId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

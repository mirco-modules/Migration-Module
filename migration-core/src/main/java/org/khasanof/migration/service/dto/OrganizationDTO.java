package org.khasanof.migration.service.dto;

import jakarta.validation.constraints.NotNull;
import org.khasanof.core.service.dto.base.IDto;
import org.khasanof.migration.domain.common.enumeration.OrganizationStatus;

import java.util.Objects;

/**
 * A DTO for the {@link org.khasanof.migration.domain.common.Organization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationDTO implements IDto {

    private Long id;

    @NotNull
    private Long tenantId;

    private OrganizationStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public OrganizationStatus getStatus() {
        return status;
    }

    public void setStatus(OrganizationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationDTO)) {
            return false;
        }

        OrganizationDTO organizationDTO = (OrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationDTO{" +
            "id=" + getId() +
            ", tenantId=" + getTenantId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

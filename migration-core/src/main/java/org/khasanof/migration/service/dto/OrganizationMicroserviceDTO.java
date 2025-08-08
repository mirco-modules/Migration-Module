package org.khasanof.migration.service.dto;

import jakarta.validation.constraints.NotNull;
import org.khasanof.core.service.dto.base.IDto;

import java.util.Objects;

/**
 * A DTO for the {@link org.khasanof.migration.domain.common.OrganizationMicroservice} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationMicroserviceDTO implements IDto {

    private Long id;

    @NotNull
    private Integer version;

    @NotNull
    private Boolean updated;

    private MicroserviceDTO microservice;

    private OrganizationDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public MicroserviceDTO getMicroservice() {
        return microservice;
    }

    public void setMicroservice(MicroserviceDTO microservice) {
        this.microservice = microservice;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationMicroserviceDTO)) {
            return false;
        }

        OrganizationMicroserviceDTO organizationMicroserviceDTO = (OrganizationMicroserviceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationMicroserviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationMicroserviceDTO{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", updated='" + getUpdated() + "'" +
            ", microservice=" + getMicroservice() +
            ", organization=" + getOrganization() +
            "}";
    }
}

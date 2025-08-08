package org.khasanof.migration.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.khasanof.core.service.dto.base.IDto;

import java.util.Objects;

/**
 * A DTO for the {@link org.khasanof.migration.domain.common.Microservice} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MicroserviceDTO implements IDto {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String name;

    @NotNull
    private Integer version;

    @NotNull
    private Boolean updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MicroserviceDTO)) {
            return false;
        }

        MicroserviceDTO microserviceDTO = (MicroserviceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, microserviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MicroserviceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", version=" + getVersion() +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}

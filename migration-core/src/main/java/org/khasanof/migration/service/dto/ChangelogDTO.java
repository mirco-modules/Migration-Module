package org.khasanof.migration.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.khasanof.core.service.dto.base.IDto;
import org.khasanof.migration.domain.common.enumeration.ChangelogStatus;

import java.util.Objects;

/**
 * A DTO for the {@link org.khasanof.migration.domain.common.Changelog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChangelogDTO implements IDto {

    private Long id;

    @NotNull
    @Size(max = 4000)
    private String name;

    @NotNull
    @Size(max = 4000)
    private String originalName;

    @NotNull
    @Size(min = 1, max = 4000)
    private String path;

    @NotNull
    private Long filesize;

    @NotNull
    private ChangelogStatus status;

    private MicroserviceDTO microservice;

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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public ChangelogStatus getStatus() {
        return status;
    }

    public void setStatus(ChangelogStatus status) {
        this.status = status;
    }

    public MicroserviceDTO getMicroservice() {
        return microservice;
    }

    public void setMicroservice(MicroserviceDTO microservice) {
        this.microservice = microservice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChangelogDTO)) {
            return false;
        }

        ChangelogDTO changelogDTO = (ChangelogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, changelogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChangelogDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", originalName='" + getOriginalName() + "'" +
            ", path='" + getPath() + "'" +
            ", filesize=" + getFilesize() +
            ", status='" + getStatus() + "'" +
            ", microservice=" + getMicroservice() +
            "}";
    }
}

package org.khasanof.migration.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.khasanof.core.annotation.Common;
import org.khasanof.core.domain.AbstractAuditingEntity;
import org.khasanof.migration.domain.common.enumeration.ChangelogStatus;

/**
 * A Changelog.
 */
@Entity
@Common
@Table(name = "mg_changelog")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Changelog extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 4000)
    @Column(name = "name", length = 4000, nullable = false)
    private String name;

    @NotNull
    @Size(max = 4000)
    @Column(name = "original_name", length = 4000, nullable = false)
    private String originalName;

    @NotNull
    @Size(min = 1, max = 4000)
    @Column(name = "path", length = 4000, nullable = false)
    private String path;

    @NotNull
    @Column(name = "filesize", nullable = false)
    private Long filesize;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChangelogStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "organizationMicroservice" }, allowSetters = true)
    private Microservice microservice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Changelog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Changelog name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public Changelog originalName(String originalName) {
        this.setOriginalName(originalName);
        return this;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getPath() {
        return this.path;
    }

    public Changelog path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getFilesize() {
        return this.filesize;
    }

    public Changelog filesize(Long filesize) {
        this.setFilesize(filesize);
        return this;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public ChangelogStatus getStatus() {
        return this.status;
    }

    public Changelog status(ChangelogStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ChangelogStatus status) {
        this.status = status;
    }

    public Microservice getMicroservice() {
        return this.microservice;
    }

    public void setMicroservice(Microservice microservice) {
        this.microservice = microservice;
    }

    public Changelog microservice(Microservice microservice) {
        this.setMicroservice(microservice);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Changelog)) {
            return false;
        }
        return getId() != null && getId().equals(((Changelog) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Changelog{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", originalName='" + getOriginalName() + "'" +
            ", path='" + getPath() + "'" +
            ", filesize=" + getFilesize() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

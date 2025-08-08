package org.khasanof.migration.service.criteria;

import org.khasanof.core.annotation.query.JoinFilter;
import org.khasanof.core.service.criteria.base.ICriteria;
import org.khasanof.migration.domain.common.enumeration.ChangelogStatus;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * Criteria class for the {@link org.khasanof.migration.domain.Changelog} entity. This class is used
 * in {@link org.khasanof.migration.web.rest.ChangelogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /changelogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChangelogCriteria implements Serializable, ICriteria {

    /**
     * Class for filtering ChangelogStatus
     */
    public static class ChangelogStatusFilter extends Filter<ChangelogStatus> {

        public ChangelogStatusFilter() {}

        public ChangelogStatusFilter(ChangelogStatusFilter filter) {
            super(filter);
        }

        @Override
        public ChangelogStatusFilter copy() {
            return new ChangelogStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter originalName;

    private StringFilter path;

    private LongFilter filesize;

    private ChangelogStatusFilter status;

    @JoinFilter(fieldName = "microservice")
    private LongFilter microserviceId;

    private Boolean distinct;

    public ChangelogCriteria() {}

    public ChangelogCriteria(ChangelogCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.originalName = other.optionalOriginalName().map(StringFilter::copy).orElse(null);
        this.path = other.optionalPath().map(StringFilter::copy).orElse(null);
        this.filesize = other.optionalFilesize().map(LongFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(ChangelogStatusFilter::copy).orElse(null);
        this.microserviceId = other.optionalMicroserviceId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ChangelogCriteria copy() {
        return new ChangelogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getOriginalName() {
        return originalName;
    }

    public Optional<StringFilter> optionalOriginalName() {
        return Optional.ofNullable(originalName);
    }

    public StringFilter originalName() {
        if (originalName == null) {
            setOriginalName(new StringFilter());
        }
        return originalName;
    }

    public void setOriginalName(StringFilter originalName) {
        this.originalName = originalName;
    }

    public StringFilter getPath() {
        return path;
    }

    public Optional<StringFilter> optionalPath() {
        return Optional.ofNullable(path);
    }

    public StringFilter path() {
        if (path == null) {
            setPath(new StringFilter());
        }
        return path;
    }

    public void setPath(StringFilter path) {
        this.path = path;
    }

    public LongFilter getFilesize() {
        return filesize;
    }

    public Optional<LongFilter> optionalFilesize() {
        return Optional.ofNullable(filesize);
    }

    public LongFilter filesize() {
        if (filesize == null) {
            setFilesize(new LongFilter());
        }
        return filesize;
    }

    public void setFilesize(LongFilter filesize) {
        this.filesize = filesize;
    }

    public ChangelogStatusFilter getStatus() {
        return status;
    }

    public Optional<ChangelogStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public ChangelogStatusFilter status() {
        if (status == null) {
            setStatus(new ChangelogStatusFilter());
        }
        return status;
    }

    public void setStatus(ChangelogStatusFilter status) {
        this.status = status;
    }

    public LongFilter getMicroserviceId() {
        return microserviceId;
    }

    public Optional<LongFilter> optionalMicroserviceId() {
        return Optional.ofNullable(microserviceId);
    }

    public LongFilter microserviceId() {
        if (microserviceId == null) {
            setMicroserviceId(new LongFilter());
        }
        return microserviceId;
    }

    public void setMicroserviceId(LongFilter microserviceId) {
        this.microserviceId = microserviceId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChangelogCriteria that = (ChangelogCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(originalName, that.originalName) &&
            Objects.equals(path, that.path) &&
            Objects.equals(filesize, that.filesize) &&
            Objects.equals(status, that.status) &&
            Objects.equals(microserviceId, that.microserviceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, originalName, path, filesize, status, microserviceId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChangelogCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalOriginalName().map(f -> "originalName=" + f + ", ").orElse("") +
            optionalPath().map(f -> "path=" + f + ", ").orElse("") +
            optionalFilesize().map(f -> "filesize=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalMicroserviceId().map(f -> "microserviceId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

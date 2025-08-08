package org.khasanof.migration.service.criteria;

import org.khasanof.core.annotation.query.JoinFilter;
import org.khasanof.core.service.criteria.base.ICriteria;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * Criteria class for the {@link org.khasanof.migration.domain.OrganizationMicroservice} entity. This class is used
 * in {@link org.khasanof.migration.web.rest.OrganizationMicroserviceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organization-microservices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationMicroserviceCriteria implements Serializable, ICriteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter version;

    private BooleanFilter updated;

    @JoinFilter(fieldName = "microservice")
    private LongFilter microserviceId;

    @JoinFilter(fieldName = "organization")
    private LongFilter organizationId;

    private Boolean distinct;

    public OrganizationMicroserviceCriteria() {}

    public OrganizationMicroserviceCriteria(OrganizationMicroserviceCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.version = other.optionalVersion().map(IntegerFilter::copy).orElse(null);
        this.updated = other.optionalUpdated().map(BooleanFilter::copy).orElse(null);
        this.microserviceId = other.optionalMicroserviceId().map(LongFilter::copy).orElse(null);
        this.organizationId = other.optionalOrganizationId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public OrganizationMicroserviceCriteria copy() {
        return new OrganizationMicroserviceCriteria(this);
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

    public IntegerFilter getVersion() {
        return version;
    }

    public Optional<IntegerFilter> optionalVersion() {
        return Optional.ofNullable(version);
    }

    public IntegerFilter version() {
        if (version == null) {
            setVersion(new IntegerFilter());
        }
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public BooleanFilter getUpdated() {
        return updated;
    }

    public Optional<BooleanFilter> optionalUpdated() {
        return Optional.ofNullable(updated);
    }

    public BooleanFilter updated() {
        if (updated == null) {
            setUpdated(new BooleanFilter());
        }
        return updated;
    }

    public void setUpdated(BooleanFilter updated) {
        this.updated = updated;
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

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public Optional<LongFilter> optionalOrganizationId() {
        return Optional.ofNullable(organizationId);
    }

    public LongFilter organizationId() {
        if (organizationId == null) {
            setOrganizationId(new LongFilter());
        }
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
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
        final OrganizationMicroserviceCriteria that = (OrganizationMicroserviceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(version, that.version) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(microserviceId, that.microserviceId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, updated, microserviceId, organizationId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationMicroserviceCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalVersion().map(f -> "version=" + f + ", ").orElse("") +
            optionalUpdated().map(f -> "updated=" + f + ", ").orElse("") +
            optionalMicroserviceId().map(f -> "microserviceId=" + f + ", ").orElse("") +
            optionalOrganizationId().map(f -> "organizationId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

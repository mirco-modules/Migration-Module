package org.khasanof.migration.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class OrganizationMicroserviceCriteriaTest {

    @Test
    void newOrganizationMicroserviceCriteriaHasAllFiltersNullTest() {
        var organizationMicroserviceCriteria = new OrganizationMicroserviceCriteria();
        assertThat(organizationMicroserviceCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void organizationMicroserviceCriteriaFluentMethodsCreatesFiltersTest() {
        var organizationMicroserviceCriteria = new OrganizationMicroserviceCriteria();

        setAllFilters(organizationMicroserviceCriteria);

        assertThat(organizationMicroserviceCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void organizationMicroserviceCriteriaCopyCreatesNullFilterTest() {
        var organizationMicroserviceCriteria = new OrganizationMicroserviceCriteria();
        var copy = organizationMicroserviceCriteria.copy();

        assertThat(organizationMicroserviceCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(organizationMicroserviceCriteria)
        );
    }

    @Test
    void organizationMicroserviceCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var organizationMicroserviceCriteria = new OrganizationMicroserviceCriteria();
        setAllFilters(organizationMicroserviceCriteria);

        var copy = organizationMicroserviceCriteria.copy();

        assertThat(organizationMicroserviceCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(organizationMicroserviceCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var organizationMicroserviceCriteria = new OrganizationMicroserviceCriteria();

        assertThat(organizationMicroserviceCriteria).hasToString("OrganizationMicroserviceCriteria{}");
    }

    private static void setAllFilters(OrganizationMicroserviceCriteria organizationMicroserviceCriteria) {
        organizationMicroserviceCriteria.id();
        organizationMicroserviceCriteria.version();
        organizationMicroserviceCriteria.updated();
        organizationMicroserviceCriteria.microserviceId();
        organizationMicroserviceCriteria.organizationId();
        organizationMicroserviceCriteria.distinct();
    }

    private static Condition<OrganizationMicroserviceCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getVersion()) &&
                condition.apply(criteria.getUpdated()) &&
                condition.apply(criteria.getMicroserviceId()) &&
                condition.apply(criteria.getOrganizationId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<OrganizationMicroserviceCriteria> copyFiltersAre(
        OrganizationMicroserviceCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getVersion(), copy.getVersion()) &&
                condition.apply(criteria.getUpdated(), copy.getUpdated()) &&
                condition.apply(criteria.getMicroserviceId(), copy.getMicroserviceId()) &&
                condition.apply(criteria.getOrganizationId(), copy.getOrganizationId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

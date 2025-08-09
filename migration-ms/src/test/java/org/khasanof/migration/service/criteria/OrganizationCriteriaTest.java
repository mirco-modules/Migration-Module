package org.khasanof.migration.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class OrganizationCriteriaTest {

    @Test
    void newOrganizationCriteriaHasAllFiltersNullTest() {
        var organizationCriteria = new OrganizationCriteria();
        assertThat(organizationCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void organizationCriteriaFluentMethodsCreatesFiltersTest() {
        var organizationCriteria = new OrganizationCriteria();

        setAllFilters(organizationCriteria);

        assertThat(organizationCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void organizationCriteriaCopyCreatesNullFilterTest() {
        var organizationCriteria = new OrganizationCriteria();
        var copy = organizationCriteria.copy();

        assertThat(organizationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(organizationCriteria)
        );
    }

    @Test
    void organizationCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var organizationCriteria = new OrganizationCriteria();
        setAllFilters(organizationCriteria);

        var copy = organizationCriteria.copy();

        assertThat(organizationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(organizationCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var organizationCriteria = new OrganizationCriteria();

        assertThat(organizationCriteria).hasToString("OrganizationCriteria{}");
    }

    private static void setAllFilters(OrganizationCriteria organizationCriteria) {
        organizationCriteria.id();
        organizationCriteria.tenantId();
        organizationCriteria.status();
        organizationCriteria.organizationMicroserviceId();
        organizationCriteria.distinct();
    }

    private static Condition<OrganizationCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTenantId()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getOrganizationMicroserviceId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<OrganizationCriteria> copyFiltersAre(
        OrganizationCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTenantId(), copy.getTenantId()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getOrganizationMicroserviceId(), copy.getOrganizationMicroserviceId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

package org.khasanof.migration.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MicroserviceCriteriaTest {

    @Test
    void newMicroserviceCriteriaHasAllFiltersNullTest() {
        var microserviceCriteria = new MicroserviceCriteria();
        assertThat(microserviceCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void microserviceCriteriaFluentMethodsCreatesFiltersTest() {
        var microserviceCriteria = new MicroserviceCriteria();

        setAllFilters(microserviceCriteria);

        assertThat(microserviceCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void microserviceCriteriaCopyCreatesNullFilterTest() {
        var microserviceCriteria = new MicroserviceCriteria();
        var copy = microserviceCriteria.copy();

        assertThat(microserviceCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(microserviceCriteria)
        );
    }

    @Test
    void microserviceCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var microserviceCriteria = new MicroserviceCriteria();
        setAllFilters(microserviceCriteria);

        var copy = microserviceCriteria.copy();

        assertThat(microserviceCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(microserviceCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var microserviceCriteria = new MicroserviceCriteria();

        assertThat(microserviceCriteria).hasToString("MicroserviceCriteria{}");
    }

    private static void setAllFilters(MicroserviceCriteria microserviceCriteria) {
        microserviceCriteria.id();
        microserviceCriteria.name();
        microserviceCriteria.version();
        microserviceCriteria.updated();
        microserviceCriteria.organizationMicroserviceId();
        microserviceCriteria.distinct();
    }

    private static Condition<MicroserviceCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getVersion()) &&
                condition.apply(criteria.getUpdated()) &&
                condition.apply(criteria.getOrganizationMicroserviceId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MicroserviceCriteria> copyFiltersAre(
        MicroserviceCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getVersion(), copy.getVersion()) &&
                condition.apply(criteria.getUpdated(), copy.getUpdated()) &&
                condition.apply(criteria.getOrganizationMicroserviceId(), copy.getOrganizationMicroserviceId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

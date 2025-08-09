package org.khasanof.migration.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ChangelogCriteriaTest {

    @Test
    void newChangelogCriteriaHasAllFiltersNullTest() {
        var changelogCriteria = new ChangelogCriteria();
        assertThat(changelogCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void changelogCriteriaFluentMethodsCreatesFiltersTest() {
        var changelogCriteria = new ChangelogCriteria();

        setAllFilters(changelogCriteria);

        assertThat(changelogCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void changelogCriteriaCopyCreatesNullFilterTest() {
        var changelogCriteria = new ChangelogCriteria();
        var copy = changelogCriteria.copy();

        assertThat(changelogCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(changelogCriteria)
        );
    }

    @Test
    void changelogCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var changelogCriteria = new ChangelogCriteria();
        setAllFilters(changelogCriteria);

        var copy = changelogCriteria.copy();

        assertThat(changelogCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(changelogCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var changelogCriteria = new ChangelogCriteria();

        assertThat(changelogCriteria).hasToString("ChangelogCriteria{}");
    }

    private static void setAllFilters(ChangelogCriteria changelogCriteria) {
        changelogCriteria.id();
        changelogCriteria.name();
        changelogCriteria.originalName();
        changelogCriteria.path();
        changelogCriteria.filesize();
        changelogCriteria.status();
        changelogCriteria.microserviceId();
        changelogCriteria.distinct();
    }

    private static Condition<ChangelogCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getOriginalName()) &&
                condition.apply(criteria.getPath()) &&
                condition.apply(criteria.getFilesize()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getMicroserviceId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ChangelogCriteria> copyFiltersAre(ChangelogCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getOriginalName(), copy.getOriginalName()) &&
                condition.apply(criteria.getPath(), copy.getPath()) &&
                condition.apply(criteria.getFilesize(), copy.getFilesize()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getMicroserviceId(), copy.getMicroserviceId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

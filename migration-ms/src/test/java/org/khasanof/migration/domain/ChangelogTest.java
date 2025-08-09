package org.khasanof.migration.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.khasanof.migration.domain.ChangelogTestSamples.*;
import static org.khasanof.migration.domain.MicroserviceTestSamples.*;

import org.junit.jupiter.api.Test;
import org.khasanof.migration.domain.common.Changelog;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.web.rest.TestUtil;

class ChangelogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Changelog.class);
        Changelog changelog1 = getChangelogSample1();
        Changelog changelog2 = new Changelog();
        assertThat(changelog1).isNotEqualTo(changelog2);

        changelog2.setId(changelog1.getId());
        assertThat(changelog1).isEqualTo(changelog2);

        changelog2 = getChangelogSample2();
        assertThat(changelog1).isNotEqualTo(changelog2);
    }

    @Test
    void microserviceTest() throws Exception {
        Changelog changelog = getChangelogRandomSampleGenerator();
        Microservice microserviceBack = getMicroserviceRandomSampleGenerator();

        changelog.setMicroservice(microserviceBack);
        assertThat(changelog.getMicroservice()).isEqualTo(microserviceBack);

        changelog.microservice(null);
        assertThat(changelog.getMicroservice()).isNull();
    }
}

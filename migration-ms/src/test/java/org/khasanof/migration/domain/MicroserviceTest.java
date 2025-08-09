package org.khasanof.migration.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.khasanof.migration.domain.MicroserviceTestSamples.*;
import static org.khasanof.migration.domain.OrganizationMicroserviceTestSamples.*;

import org.junit.jupiter.api.Test;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.domain.common.OrganizationMicroservice;
import org.khasanof.migration.web.rest.TestUtil;

class MicroserviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Microservice.class);
        Microservice microservice1 = getMicroserviceSample1();
        Microservice microservice2 = new Microservice();
        assertThat(microservice1).isNotEqualTo(microservice2);

        microservice2.setId(microservice1.getId());
        assertThat(microservice1).isEqualTo(microservice2);

        microservice2 = getMicroserviceSample2();
        assertThat(microservice1).isNotEqualTo(microservice2);
    }
}

package org.khasanof.migration.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.khasanof.migration.domain.MicroserviceTestSamples.*;
import static org.khasanof.migration.domain.OrganizationMicroserviceTestSamples.*;
import static org.khasanof.migration.domain.OrganizationTestSamples.*;

import org.junit.jupiter.api.Test;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.domain.common.Organization;
import org.khasanof.migration.domain.common.OrganizationMicroservice;
import org.khasanof.migration.web.rest.TestUtil;

class OrganizationMicroserviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationMicroservice.class);
        OrganizationMicroservice organizationMicroservice1 = getOrganizationMicroserviceSample1();
        OrganizationMicroservice organizationMicroservice2 = new OrganizationMicroservice();
        assertThat(organizationMicroservice1).isNotEqualTo(organizationMicroservice2);

        organizationMicroservice2.setId(organizationMicroservice1.getId());
        assertThat(organizationMicroservice1).isEqualTo(organizationMicroservice2);

        organizationMicroservice2 = getOrganizationMicroserviceSample2();
        assertThat(organizationMicroservice1).isNotEqualTo(organizationMicroservice2);
    }

    @Test
    void microserviceTest() throws Exception {
        OrganizationMicroservice organizationMicroservice = getOrganizationMicroserviceRandomSampleGenerator();
        Microservice microserviceBack = getMicroserviceRandomSampleGenerator();

        organizationMicroservice.setMicroservice(microserviceBack);
        assertThat(organizationMicroservice.getMicroservice()).isEqualTo(microserviceBack);

        organizationMicroservice.microservice(null);
        assertThat(organizationMicroservice.getMicroservice()).isNull();
    }

    @Test
    void organizationTest() throws Exception {
        OrganizationMicroservice organizationMicroservice = getOrganizationMicroserviceRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        organizationMicroservice.setOrganization(organizationBack);
        assertThat(organizationMicroservice.getOrganization()).isEqualTo(organizationBack);

        organizationMicroservice.organization(null);
        assertThat(organizationMicroservice.getOrganization()).isNull();
    }
}

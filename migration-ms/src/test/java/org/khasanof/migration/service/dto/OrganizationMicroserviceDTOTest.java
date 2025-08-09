package org.khasanof.migration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.khasanof.migration.web.rest.TestUtil;

class OrganizationMicroserviceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationMicroserviceDTO.class);
        OrganizationMicroserviceDTO organizationMicroserviceDTO1 = new OrganizationMicroserviceDTO();
        organizationMicroserviceDTO1.setId(1L);
        OrganizationMicroserviceDTO organizationMicroserviceDTO2 = new OrganizationMicroserviceDTO();
        assertThat(organizationMicroserviceDTO1).isNotEqualTo(organizationMicroserviceDTO2);
        organizationMicroserviceDTO2.setId(organizationMicroserviceDTO1.getId());
        assertThat(organizationMicroserviceDTO1).isEqualTo(organizationMicroserviceDTO2);
        organizationMicroserviceDTO2.setId(2L);
        assertThat(organizationMicroserviceDTO1).isNotEqualTo(organizationMicroserviceDTO2);
        organizationMicroserviceDTO1.setId(null);
        assertThat(organizationMicroserviceDTO1).isNotEqualTo(organizationMicroserviceDTO2);
    }
}

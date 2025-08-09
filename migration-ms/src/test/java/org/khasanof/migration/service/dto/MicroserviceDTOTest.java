package org.khasanof.migration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.khasanof.migration.web.rest.TestUtil;

class MicroserviceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MicroserviceDTO.class);
        MicroserviceDTO microserviceDTO1 = new MicroserviceDTO();
        microserviceDTO1.setId(1L);
        MicroserviceDTO microserviceDTO2 = new MicroserviceDTO();
        assertThat(microserviceDTO1).isNotEqualTo(microserviceDTO2);
        microserviceDTO2.setId(microserviceDTO1.getId());
        assertThat(microserviceDTO1).isEqualTo(microserviceDTO2);
        microserviceDTO2.setId(2L);
        assertThat(microserviceDTO1).isNotEqualTo(microserviceDTO2);
        microserviceDTO1.setId(null);
        assertThat(microserviceDTO1).isNotEqualTo(microserviceDTO2);
    }
}

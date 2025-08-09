package org.khasanof.migration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.khasanof.migration.web.rest.TestUtil;

class ChangelogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChangelogDTO.class);
        ChangelogDTO changelogDTO1 = new ChangelogDTO();
        changelogDTO1.setId(1L);
        ChangelogDTO changelogDTO2 = new ChangelogDTO();
        assertThat(changelogDTO1).isNotEqualTo(changelogDTO2);
        changelogDTO2.setId(changelogDTO1.getId());
        assertThat(changelogDTO1).isEqualTo(changelogDTO2);
        changelogDTO2.setId(2L);
        assertThat(changelogDTO1).isNotEqualTo(changelogDTO2);
        changelogDTO1.setId(null);
        assertThat(changelogDTO1).isNotEqualTo(changelogDTO2);
    }
}

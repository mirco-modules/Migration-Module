package org.khasanof.migration.service.mapper;

import static org.khasanof.migration.domain.ChangelogAsserts.*;
import static org.khasanof.migration.domain.ChangelogTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangelogMapperTest {

    private ChangelogMapper changelogMapper;

    @BeforeEach
    void setUp() {
        changelogMapper = new ChangelogMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getChangelogSample1();
        var actual = changelogMapper.toEntity(changelogMapper.toDto(expected));
        assertChangelogAllPropertiesEquals(expected, actual);
    }
}

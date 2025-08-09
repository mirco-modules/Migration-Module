package org.khasanof.migration.service.mapper;

import static org.khasanof.migration.domain.MicroserviceAsserts.*;
import static org.khasanof.migration.domain.MicroserviceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MicroserviceMapperTest {

    private MicroserviceMapper microserviceMapper;

    @BeforeEach
    void setUp() {
        microserviceMapper = new MicroserviceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMicroserviceSample1();
        var actual = microserviceMapper.toEntity(microserviceMapper.toDto(expected));
        assertMicroserviceAllPropertiesEquals(expected, actual);
    }
}

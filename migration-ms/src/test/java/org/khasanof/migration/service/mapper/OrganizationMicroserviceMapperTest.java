package org.khasanof.migration.service.mapper;

import static org.khasanof.migration.domain.OrganizationMicroserviceAsserts.*;
import static org.khasanof.migration.domain.OrganizationMicroserviceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationMicroserviceMapperTest {

    private OrganizationMicroserviceMapper organizationMicroserviceMapper;

    @BeforeEach
    void setUp() {
        organizationMicroserviceMapper = new OrganizationMicroserviceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrganizationMicroserviceSample1();
        var actual = organizationMicroserviceMapper.toEntity(organizationMicroserviceMapper.toDto(expected));
        assertOrganizationMicroserviceAllPropertiesEquals(expected, actual);
    }
}

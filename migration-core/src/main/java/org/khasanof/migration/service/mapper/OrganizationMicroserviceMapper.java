package org.khasanof.migration.service.mapper;

import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.domain.common.Organization;
import org.khasanof.migration.domain.common.OrganizationMicroservice;
import org.khasanof.migration.service.dto.MicroserviceDTO;
import org.khasanof.migration.service.dto.OrganizationDTO;
import org.khasanof.migration.service.dto.OrganizationMicroserviceDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link OrganizationMicroservice} and its DTO {@link OrganizationMicroserviceDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMicroserviceMapper extends IGeneralMapper<OrganizationMicroservice, OrganizationMicroserviceDTO> {

    @Mapping(target = "microservice", source = "microservice", qualifiedByName = "microserviceId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    OrganizationMicroserviceDTO toDto(OrganizationMicroservice s);

    @Named("microserviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    MicroserviceDTO toDtoMicroserviceId(Microservice microservice);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}

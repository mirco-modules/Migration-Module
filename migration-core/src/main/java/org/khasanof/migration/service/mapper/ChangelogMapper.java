package org.khasanof.migration.service.mapper;

import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.migration.domain.common.Changelog;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.service.dto.ChangelogDTO;
import org.khasanof.migration.service.dto.MicroserviceDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Changelog} and its DTO {@link ChangelogDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChangelogMapper extends IGeneralMapper<Changelog, ChangelogDTO> {

    @Mapping(target = "microservice", source = "microservice", qualifiedByName = "microserviceId")
    ChangelogDTO toDto(Changelog s);

    @Named("microserviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MicroserviceDTO toDtoMicroserviceId(Microservice microservice);
}

package org.khasanof.migration.service.mapper;

import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.service.dto.MicroserviceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Microservice} and its DTO {@link MicroserviceDTO}.
 */
@Mapper(componentModel = "spring")
public interface MicroserviceMapper extends IGeneralMapper<Microservice, MicroserviceDTO> {}

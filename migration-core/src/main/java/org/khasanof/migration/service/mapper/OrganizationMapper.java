package org.khasanof.migration.service.mapper;

import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.migration.domain.common.Organization;
import org.khasanof.migration.service.dto.OrganizationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends IGeneralMapper<Organization, OrganizationDTO> {}

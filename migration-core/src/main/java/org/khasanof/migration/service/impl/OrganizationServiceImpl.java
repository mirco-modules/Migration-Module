package org.khasanof.migration.service.impl;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.core.service.base.impl.GeneralValidateService;
import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.core.service.validator.manager.IGeneralValidatorManager;
import org.khasanof.migration.domain.common.Organization;
import org.khasanof.migration.service.OrganizationService;
import org.khasanof.migration.service.dto.OrganizationDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.khasanof.migration.domain.common.Organization}.
 */
@Service
@Transactional
public class OrganizationServiceImpl extends GeneralValidateService<Organization, OrganizationDTO> implements OrganizationService {

    public OrganizationServiceImpl(IGeneralMapper<Organization, OrganizationDTO> generalMapper,
                                   IGeneralRepository<Organization> generalRepository,
                                   IGeneralValidatorManager generalValidatorManager
    ) {
        super(generalMapper, generalRepository, generalValidatorManager);
    }
}

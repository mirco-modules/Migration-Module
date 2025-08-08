package org.khasanof.migration.service.impl;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.core.service.base.impl.GeneralValidateService;
import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.core.service.validator.manager.IGeneralValidatorManager;
import org.khasanof.migration.domain.common.OrganizationMicroservice;
import org.khasanof.migration.service.OrganizationMicroserviceService;
import org.khasanof.migration.service.dto.OrganizationMicroserviceDTO;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link org.khasanof.migration.domain.common.OrganizationMicroservice}.
 */
@Service
public class OrganizationMicroserviceServiceImpl extends GeneralValidateService<OrganizationMicroservice, OrganizationMicroserviceDTO> implements OrganizationMicroserviceService {

    public OrganizationMicroserviceServiceImpl(IGeneralMapper<OrganizationMicroservice, OrganizationMicroserviceDTO> generalMapper,
                                               IGeneralRepository<OrganizationMicroservice> generalRepository,
                                               IGeneralValidatorManager generalValidatorManager
    ) {
        super(generalMapper, generalRepository, generalValidatorManager);
    }
}

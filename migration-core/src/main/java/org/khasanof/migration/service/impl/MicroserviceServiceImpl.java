package org.khasanof.migration.service.impl;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.core.service.base.impl.GeneralValidateService;
import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.core.service.validator.manager.IGeneralValidatorManager;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.service.MicroserviceService;
import org.khasanof.migration.service.dto.MicroserviceDTO;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link org.khasanof.migration.domain.common.Microservice}.
 */
@Service
public class MicroserviceServiceImpl extends GeneralValidateService<Microservice, MicroserviceDTO> implements MicroserviceService {

    public MicroserviceServiceImpl(IGeneralMapper<Microservice, MicroserviceDTO> generalMapper,
                                   IGeneralRepository<Microservice> generalRepository,
                                   IGeneralValidatorManager generalValidatorManager
    ) {
        super(generalMapper, generalRepository, generalValidatorManager);
    }
}

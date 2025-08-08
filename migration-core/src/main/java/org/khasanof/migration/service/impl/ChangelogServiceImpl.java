package org.khasanof.migration.service.impl;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.core.service.base.impl.GeneralValidateService;
import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.core.service.validator.manager.IGeneralValidatorManager;
import org.khasanof.migration.domain.common.Changelog;
import org.khasanof.migration.service.ChangelogService;
import org.khasanof.migration.service.dto.ChangelogDTO;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link org.khasanof.migration.domain.common.Changelog}.
 */
@Service
public class ChangelogServiceImpl extends GeneralValidateService<Changelog, ChangelogDTO> implements ChangelogService {

    public ChangelogServiceImpl(IGeneralMapper<Changelog, ChangelogDTO> generalMapper,
                                IGeneralRepository<Changelog> generalRepository,
                                IGeneralValidatorManager generalValidatorManager
    ) {
        super(generalMapper, generalRepository, generalValidatorManager);
    }
}

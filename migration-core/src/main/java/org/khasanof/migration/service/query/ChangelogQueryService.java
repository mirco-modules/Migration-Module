package org.khasanof.migration.service.query;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.core.service.query.specification.DynamicSpecificationQueryService;
import org.khasanof.core.service.query.specification.core.helper.CriteriaFieldResolver;
import org.khasanof.core.service.query.specification.core.manager.DynamicSpecificationBuilderManager;
import org.khasanof.migration.domain.common.Changelog;
import org.khasanof.migration.service.criteria.ChangelogCriteria;
import org.khasanof.migration.service.dto.ChangelogDTO;
import org.springframework.stereotype.Service;

/**
 * @author Nurislom
 * @see org.khasanof.migration.service.query
 * @since 8/9/2025 1:29 AM
 */
@Service
public class ChangelogQueryService extends DynamicSpecificationQueryService<Changelog, ChangelogDTO, ChangelogCriteria> {

    public ChangelogQueryService(IGeneralMapper<Changelog, ChangelogDTO> generalMapper,
                                 IGeneralRepository<Changelog> generalRepository,
                                 CriteriaFieldResolver criteriaFieldResolver,
                                 DynamicSpecificationBuilderManager dynamicSpecificationBuilderManager
    ) {
        super(generalMapper, generalRepository, criteriaFieldResolver, dynamicSpecificationBuilderManager);
    }
}

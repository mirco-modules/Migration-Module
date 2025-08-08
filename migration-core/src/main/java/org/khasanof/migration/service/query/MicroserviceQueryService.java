package org.khasanof.migration.service.query;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.core.service.query.specification.DynamicSpecificationQueryService;
import org.khasanof.core.service.query.specification.core.helper.CriteriaFieldResolver;
import org.khasanof.core.service.query.specification.core.manager.DynamicSpecificationBuilderManager;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.service.criteria.MicroserviceCriteria;
import org.khasanof.migration.service.dto.MicroserviceDTO;
import org.springframework.stereotype.Service;

/**
 * @author Nurislom
 * @see org.khasanof.migration.service.query
 * @since 8/9/2025 1:30 AM
 */
@Service
public class MicroserviceQueryService extends DynamicSpecificationQueryService<Microservice, MicroserviceDTO, MicroserviceCriteria> {

    public MicroserviceQueryService(IGeneralMapper<Microservice, MicroserviceDTO> generalMapper,
                                    IGeneralRepository<Microservice> generalRepository,
                                    CriteriaFieldResolver criteriaFieldResolver,
                                    DynamicSpecificationBuilderManager dynamicSpecificationBuilderManager
    ) {
        super(generalMapper, generalRepository, criteriaFieldResolver, dynamicSpecificationBuilderManager);
    }
}

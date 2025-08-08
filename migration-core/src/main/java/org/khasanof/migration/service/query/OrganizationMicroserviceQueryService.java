package org.khasanof.migration.service.query;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.core.service.query.specification.DynamicSpecificationQueryService;
import org.khasanof.core.service.query.specification.core.helper.CriteriaFieldResolver;
import org.khasanof.core.service.query.specification.core.manager.DynamicSpecificationBuilderManager;
import org.khasanof.migration.domain.common.OrganizationMicroservice;
import org.khasanof.migration.service.criteria.OrganizationMicroserviceCriteria;
import org.khasanof.migration.service.dto.OrganizationMicroserviceDTO;
import org.springframework.stereotype.Service;

/**
 * @author Nurislom
 * @see org.khasanof.migration.service.query
 * @since 8/9/2025 1:31 AM
 */
@Service
public class OrganizationMicroserviceQueryService extends DynamicSpecificationQueryService<OrganizationMicroservice, OrganizationMicroserviceDTO, OrganizationMicroserviceCriteria> {

    public OrganizationMicroserviceQueryService(IGeneralMapper<OrganizationMicroservice, OrganizationMicroserviceDTO> generalMapper,
                                                IGeneralRepository<OrganizationMicroservice> generalRepository,
                                                CriteriaFieldResolver criteriaFieldResolver,
                                                DynamicSpecificationBuilderManager dynamicSpecificationBuilderManager
    ) {
        super(generalMapper, generalRepository, criteriaFieldResolver, dynamicSpecificationBuilderManager);
    }
}

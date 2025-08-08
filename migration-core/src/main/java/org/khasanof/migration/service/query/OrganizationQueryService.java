package org.khasanof.migration.service.query;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.core.service.mapper.base.IGeneralMapper;
import org.khasanof.core.service.query.specification.DynamicSpecificationQueryService;
import org.khasanof.core.service.query.specification.core.helper.CriteriaFieldResolver;
import org.khasanof.core.service.query.specification.core.manager.DynamicSpecificationBuilderManager;
import org.khasanof.migration.domain.common.Organization;
import org.khasanof.migration.service.criteria.OrganizationCriteria;
import org.khasanof.migration.service.dto.OrganizationDTO;
import org.springframework.stereotype.Service;

/**
 * @author Nurislom
 * @see org.khasanof.migration.service.query
 * @since 8/9/2025 1:31 AM
 */
@Service
public class OrganizationQueryService extends DynamicSpecificationQueryService<Organization, OrganizationDTO, OrganizationCriteria> {

    public OrganizationQueryService(IGeneralMapper<Organization, OrganizationDTO> generalMapper,
                                    IGeneralRepository<Organization> generalRepository,
                                    CriteriaFieldResolver criteriaFieldResolver,
                                    DynamicSpecificationBuilderManager dynamicSpecificationBuilderManager
    ) {
        super(generalMapper, generalRepository, criteriaFieldResolver, dynamicSpecificationBuilderManager);
    }
}

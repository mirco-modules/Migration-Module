package org.khasanof.migration.web.rest;

import org.khasanof.core.service.base.IGeneralService;
import org.khasanof.core.service.query.base.IGeneralQueryService;
import org.khasanof.core.web.rest.base.GeneralQueryResource;
import org.khasanof.migration.domain.common.OrganizationMicroservice;
import org.khasanof.migration.service.criteria.OrganizationMicroserviceCriteria;
import org.khasanof.migration.service.dto.OrganizationMicroserviceDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link org.khasanof.migration.domain.common.OrganizationMicroservice}.
 */
@RestController
@RequestMapping("/api/organization-microservices")
public class OrganizationMicroserviceResource extends GeneralQueryResource<OrganizationMicroservice, OrganizationMicroserviceDTO, OrganizationMicroserviceCriteria> {

    public OrganizationMicroserviceResource(IGeneralService<OrganizationMicroservice, OrganizationMicroserviceDTO> generalService,
                                            IGeneralQueryService<OrganizationMicroservice, OrganizationMicroserviceDTO, OrganizationMicroserviceCriteria> generalQueryService
    ) {
        super(generalService, generalQueryService);
    }
}

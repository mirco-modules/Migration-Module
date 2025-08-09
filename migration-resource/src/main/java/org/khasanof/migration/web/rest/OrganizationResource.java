package org.khasanof.migration.web.rest;

import org.khasanof.core.service.base.IGeneralService;
import org.khasanof.core.service.query.base.IGeneralQueryService;
import org.khasanof.core.web.rest.base.GeneralQueryResource;
import org.khasanof.migration.domain.common.Organization;
import org.khasanof.migration.service.criteria.OrganizationCriteria;
import org.khasanof.migration.service.dto.OrganizationDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link org.khasanof.migration.domain.common.Organization}.
 */
@RestController
@RequestMapping("/api/organizations")
public class OrganizationResource extends GeneralQueryResource<Organization, OrganizationDTO, OrganizationCriteria> {

    public OrganizationResource(IGeneralService<Organization, OrganizationDTO> generalService,
                                IGeneralQueryService<Organization, OrganizationDTO, OrganizationCriteria> generalQueryService
    ) {
        super(generalService, generalQueryService);
    }
}

package org.khasanof.migration.web.rest;

import org.khasanof.core.service.base.IGeneralService;
import org.khasanof.core.service.query.base.IGeneralQueryService;
import org.khasanof.core.web.rest.base.GeneralQueryResource;
import org.khasanof.migration.domain.common.Changelog;
import org.khasanof.migration.service.criteria.ChangelogCriteria;
import org.khasanof.migration.service.dto.ChangelogDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link org.khasanof.migration.domain.common.Changelog}.
 */
@RestController
@RequestMapping("/api/changelogs")
public class ChangelogResource extends GeneralQueryResource<Changelog, ChangelogDTO, ChangelogCriteria> {
    
    public ChangelogResource(IGeneralService<Changelog, ChangelogDTO> generalService, 
                             IGeneralQueryService<Changelog, ChangelogDTO, ChangelogCriteria> generalQueryService
    ) {
        super(generalService, generalQueryService);
    }
}

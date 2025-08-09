package org.khasanof.migration.web.rest;

import org.khasanof.core.service.base.IGeneralService;
import org.khasanof.core.service.query.base.IGeneralQueryService;
import org.khasanof.core.web.rest.base.GeneralQueryResource;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.service.criteria.MicroserviceCriteria;
import org.khasanof.migration.service.dto.MicroserviceDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link org.khasanof.migration.domain.common.Microservice}.
 */
@RestController
@RequestMapping("/api/microservices")
public class MicroserviceResource extends GeneralQueryResource<Microservice, MicroserviceDTO, MicroserviceCriteria> {

    public MicroserviceResource(IGeneralService<Microservice, MicroserviceDTO> generalService,
                                IGeneralQueryService<Microservice, MicroserviceDTO, MicroserviceCriteria> generalQueryService
    ) {
        super(generalService, generalQueryService);
    }
}

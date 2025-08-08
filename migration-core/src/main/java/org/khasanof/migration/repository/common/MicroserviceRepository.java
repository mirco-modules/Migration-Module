package org.khasanof.migration.repository.common;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.migration.domain.common.Microservice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Microservice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MicroserviceRepository extends IGeneralRepository<Microservice> {}

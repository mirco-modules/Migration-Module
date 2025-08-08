package org.khasanof.migration.repository.common;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.migration.domain.common.OrganizationMicroservice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationMicroservice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationMicroserviceRepository extends IGeneralRepository<OrganizationMicroservice> {}

package org.khasanof.migration.repository.common;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.migration.domain.common.Organization;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Organization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationRepository extends IGeneralRepository<Organization> {}

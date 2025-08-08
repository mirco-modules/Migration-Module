package org.khasanof.migration.repository.common;

import org.khasanof.core.repository.base.IGeneralRepository;
import org.khasanof.migration.domain.common.Changelog;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Changelog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChangelogRepository extends IGeneralRepository<Changelog> {}

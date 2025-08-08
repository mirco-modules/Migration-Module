package org.khasanof.migration.service;

import org.khasanof.core.service.base.IGeneralService;
import org.khasanof.migration.domain.common.Changelog;
import org.khasanof.migration.service.dto.ChangelogDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.khasanof.migration.domain.common.Changelog}.
 */
public interface ChangelogService extends IGeneralService<Changelog, ChangelogDTO> {
}

package org.khasanof.migration.config;

import org.khasanof.core.domain.enumeration.TenancyResolverType;
import org.khasanof.core.domain.enumeration.TenancyType;
import org.khasanof.core.tenancy.TenancyConfigurer;
import org.khasanof.core.tenancy.core.constants.TenancyConstants;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nurislom
 * @see org.khasanof.user.config
 * @since 4/27/2025 9:51 PM
 */
@Configuration(TenancyConstants.TENANCY_CONFIGURER)
public class MultiTenancyConfiguration implements TenancyConfigurer {

    @Override
    public TenancyType getType() {
        return TenancyType.MULTI;
    }

    @Override
    public TenancyResolverType getResolverType() {
        return TenancyResolverType.HTTP_HEADER;
    }
}

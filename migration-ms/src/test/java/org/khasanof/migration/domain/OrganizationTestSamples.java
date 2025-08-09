package org.khasanof.migration.domain;

import org.khasanof.migration.domain.common.Organization;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Organization getOrganizationSample1() {
        return new Organization().id(1L).tenantId(1L);
    }

    public static Organization getOrganizationSample2() {
        return new Organization().id(2L).tenantId(2L);
    }

    public static Organization getOrganizationRandomSampleGenerator() {
        return new Organization().id(longCount.incrementAndGet()).tenantId(longCount.incrementAndGet());
    }
}

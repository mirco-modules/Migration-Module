package org.khasanof.migration.domain;

import org.khasanof.migration.domain.common.OrganizationMicroservice;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationMicroserviceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OrganizationMicroservice getOrganizationMicroserviceSample1() {
        return new OrganizationMicroservice().id(1L).version(1);
    }

    public static OrganizationMicroservice getOrganizationMicroserviceSample2() {
        return new OrganizationMicroservice().id(2L).version(2);
    }

    public static OrganizationMicroservice getOrganizationMicroserviceRandomSampleGenerator() {
        return new OrganizationMicroservice().id(longCount.incrementAndGet()).version(intCount.incrementAndGet());
    }
}

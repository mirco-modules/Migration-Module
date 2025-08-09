package org.khasanof.migration.domain;

import org.khasanof.migration.domain.common.Microservice;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MicroserviceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Microservice getMicroserviceSample1() {
        return new Microservice().id(1L).name("name1").version(1);
    }

    public static Microservice getMicroserviceSample2() {
        return new Microservice().id(2L).name("name2").version(2);
    }

    public static Microservice getMicroserviceRandomSampleGenerator() {
        return new Microservice().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).version(intCount.incrementAndGet());
    }
}

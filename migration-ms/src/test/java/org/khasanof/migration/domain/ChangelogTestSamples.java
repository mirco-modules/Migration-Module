package org.khasanof.migration.domain;

import org.khasanof.migration.domain.common.Changelog;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ChangelogTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Changelog getChangelogSample1() {
        return new Changelog().id(1L).name("name1").originalName("originalName1").path("path1").filesize(1L);
    }

    public static Changelog getChangelogSample2() {
        return new Changelog().id(2L).name("name2").originalName("originalName2").path("path2").filesize(2L);
    }

    public static Changelog getChangelogRandomSampleGenerator() {
        return new Changelog()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .originalName(UUID.randomUUID().toString())
            .path(UUID.randomUUID().toString())
            .filesize(longCount.incrementAndGet());
    }
}

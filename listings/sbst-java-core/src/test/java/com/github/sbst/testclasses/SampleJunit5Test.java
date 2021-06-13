package com.github.sbst.testclasses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SampleJunit5Test {
    @Test
    void test() {
        assertThat(1).isEqualTo(1);
    }
}

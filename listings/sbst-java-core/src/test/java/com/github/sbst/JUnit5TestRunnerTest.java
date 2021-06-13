package com.github.sbst;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.sbst.testclasses.SampleJunit5Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUnit5TestRunnerTest {

    JUnit5TestRunner runner;

    @BeforeEach
    void setUp() {
        runner = new JUnit5TestRunner();
    }

    @Test
    void shouldRunSimpleTest() {
        // When
        runner.runUnitTest(SampleJunit5Test.class, SampleJunit5Test.class.getName());

        // Then
        long failedTestsCount = runner.listener.getSummary().getTestsFailedCount();
        assertThat(failedTestsCount).isEqualTo(0);
        // And
        long succeededTestsCount = runner.listener.getSummary().getTestsSucceededCount();
        assertThat(succeededTestsCount).isEqualTo(1);
    }
}
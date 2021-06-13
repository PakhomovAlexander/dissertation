package com.github.sbst;

import java.util.Objects;

import org.jacoco.core.analysis.ICounter;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class TestRunResult {
    private final TestExecutionSummary testExecutionSummary;
    private final ICounter covCounter;

    public TestRunResult(TestExecutionSummary testExecutionSummary, ICounter covCounter) {
        this.testExecutionSummary = testExecutionSummary;
        this.covCounter = covCounter;
    }

    public TestExecutionSummary getTestExecutionSummary() {
        return testExecutionSummary;
    }

    public ICounter getCovCounter() {
        return covCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestRunResult)) return false;
        TestRunResult that = (TestRunResult) o;
        return Objects.equals(getTestExecutionSummary(), that.getTestExecutionSummary()) && Objects.equals(getCovCounter(), that.getCovCounter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTestExecutionSummary(), getCovCounter());
    }

    @Override
    public String toString() {
        return "TestRunResult{" +
                "testExecutionSummary=" + testExecutionSummary +
                ", covCounter=" + covCounter +
                '}';
    }
}

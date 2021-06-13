package com.github.sbst.core.algorithm;

import java.util.Map;

import com.github.sbst.core.blueprint.materialized.TestSuiteMB;

public class AlgorithmOutput {
    private final Map<String, String> classNameToSourceCode;
    private final Map<String, Integer> classNameToTestMethodCount;
    private final TestSuiteMB testSuiteMB;
    private final double totalCov;

    public AlgorithmOutput(Map<String, String> classNameToSourceCode,
                           Map<String, Integer> classNameToTestMethodCount,
                           TestSuiteMB testSuiteMB, double totalCov) {
        this.classNameToSourceCode = classNameToSourceCode;
        this.classNameToTestMethodCount = classNameToTestMethodCount;
        this.testSuiteMB = testSuiteMB;
        this.totalCov = totalCov;
    }

    public Map<String, String> getClassNameToSourceCode() {
        return classNameToSourceCode;
    }

    public int getNumberOfTestMethodsFor(String testClassName) {
        return classNameToTestMethodCount.get(testClassName);
    }

    public double getTotalCov() {
        return totalCov;
    }

    public TestSuiteMB getTestSuiteMB() {
        return testSuiteMB;
    }
}

package com.github.sbst.core.algorithm;

import java.util.ArrayList;

import com.github.sbst.core.blueprint.materialized.TestMethodMB;
import com.github.sbst.core.blueprint.materialized.TestSuiteMB;
import com.github.sbst.core.info.ClassInfo;

public class TestSuiteSizeMinimizer {

    private final FitnessFunction fitnessFunction;

    public TestSuiteSizeMinimizer(FitnessFunction fitnessFunction) {this.fitnessFunction = fitnessFunction;}

    public TestSuiteMB minimizeTestsSize(AlgorithmOutput algorithmOutput, ClassInfo classInfo) {
        final TestSuiteMB testSuiteMB = algorithmOutput.getTestSuiteMB();

        final var optimizedTestSuiteBuilder = TestSuiteMB.materializedBuilder().from(testSuiteMB);
        final var originalTestMethods = testSuiteMB.getTestMethodBlueprints();
        final double originalFitScore = algorithmOutput.getTotalCov();

        int currentIndexToRemove = 0;
        var currentTestSet = new ArrayList<>(originalTestMethods);
        while (currentTestSet.size() > 1 && currentIndexToRemove != currentTestSet.size()) {
            final var reducedTestSet = copyAndRemoveByIndex(currentTestSet, currentIndexToRemove);
            final TestSuiteMB optimizedTestSuiteCandidate = optimizedTestSuiteBuilder.testMethodBlueprints(reducedTestSet).build();
            final var fitScore = fitnessFunction.apply(optimizedTestSuiteCandidate, classInfo).getFitScore();
            if (fitScore == originalFitScore) {
                currentTestSet = reducedTestSet;
            } else {
                currentIndexToRemove++;
            }
        }

        return optimizedTestSuiteBuilder.testMethodBlueprints(currentTestSet).build();
    }

    private ArrayList<? extends TestMethodMB> copyAndRemoveByIndex(java.util.List<? extends TestMethodMB> originalTestMethods, int currentIndexToRemove) {
        final var reducedTestSet = new ArrayList<>(originalTestMethods);
        reducedTestSet.remove(currentIndexToRemove);
        return reducedTestSet;
    }
}

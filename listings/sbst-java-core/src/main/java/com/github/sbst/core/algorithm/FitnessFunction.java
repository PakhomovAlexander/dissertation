package com.github.sbst.core.algorithm;

import java.io.IOException;
import java.util.function.BiFunction;

import com.github.sbst.ClassCompiler;
import com.github.sbst.JUnit5TestRunner;
import com.github.sbst.TestGenerator;
import com.github.sbst.TestRunResult;
import com.github.sbst.SbstToolConfiguration;
import com.github.sbst.core.blueprint.materialized.TestSuiteMB;
import com.github.sbst.core.info.ClassInfo;

public class FitnessFunction implements BiFunction<TestSuiteMB, ClassInfo, FitnessOutput> {
    private final JUnit5TestRunner testRunner;
    private final ClassCompiler compiler;
    private final TestGenerator testGenerator;
    private final SbstToolConfiguration configuration;

    public FitnessFunction(JUnit5TestRunner testRunner, ClassCompiler compiler, TestGenerator testGenerator, SbstToolConfiguration configuration) {
        this.testRunner = testRunner;
        this.compiler = compiler;
        this.testGenerator = testGenerator;
        this.configuration = configuration;
    }

    public FitnessOutput apply(TestSuiteMB testSuiteMB, ClassInfo classInfo) {
        String sourceCode = testGenerator.generateTestFor(testSuiteMB, classInfo);
        Class testClass = compile(configuration.getGeneratedClassesPackage() + "." + testSuiteMB.getTestSuiteName(), sourceCode);
        TestRunResult testRunResult = testRunner.runUnitTest(testClass, classInfo.getFQN());

        return new FitnessOutput(testRunResult.getCovCounter().getCoveredRatio(), sourceCode);
    }

    private Class compile(String fqn, String sourceCode) {
        try {
            return compiler.compile(fqn, sourceCode);
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}

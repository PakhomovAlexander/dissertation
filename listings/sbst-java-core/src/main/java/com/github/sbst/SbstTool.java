package com.github.sbst;

import java.io.IOException;
import java.nio.file.Path;

import com.github.sbst.core.algorithm.AlgorithmOutput;
import com.github.sbst.core.algorithm.FitnessFunction;
import com.github.sbst.core.algorithm.GeneticAlgorithm;
import com.github.sbst.core.algorithm.GeneticAlgorithmConfig;
import com.github.sbst.core.algorithm.TestSuiteSizeMinimizer;
import com.github.sbst.core.info.ClassInfo;

public class SbstTool {
    private final GeneticAlgorithm geneticAlgorithm;
    private final TestSuiteSizeMinimizer testSuiteSizeMinimizer;
    private final TestGenerator testGenerator;
    private final SbstToolConfiguration sbstToolConfiguration;

    protected SbstTool(GeneticAlgorithm geneticAlgorithm, TestSuiteSizeMinimizer testSuiteSizeMinimizer, TestGenerator testGenerator, SbstToolConfiguration sbstToolConfiguration) {
        this.geneticAlgorithm = geneticAlgorithm;
        this.testSuiteSizeMinimizer = testSuiteSizeMinimizer;
        this.testGenerator = testGenerator;
        this.sbstToolConfiguration = sbstToolConfiguration;
    }

    public static SbstTool fromConfiguration(SbstToolConfiguration configuration) {
        // todo: move SbstTool creation logic to another place
        final var geneticAlgorithmConfig = new GeneticAlgorithmConfig();
        final var compiler = new ClassCompiler(configuration);
        final var testRunner = new JUnit5TestRunnerWithCov(configuration.getCutClassFile(), configuration);
        final var testGenerator = new TestGenerator(configuration);
        final var fitnessFunction = new FitnessFunction(testRunner, compiler, testGenerator, configuration);
        final var geneticAlgorithm = new GeneticAlgorithm(geneticAlgorithmConfig, compiler, testRunner, testGenerator, fitnessFunction);
        final var testSuiteSizeMinimizer = new TestSuiteSizeMinimizer(fitnessFunction);

        return new SbstTool(geneticAlgorithm, testSuiteSizeMinimizer, testGenerator, configuration);
    }

    public void run(Class cut) {
        //todo find java file by class
    }

    public void run(Path classPath) throws IOException {
        ClassInfo classInfo = new ClassInspector().inspect(classPath);

        AlgorithmOutput output = geneticAlgorithm.run(classInfo);

        var classWriter = new ClassWriter(Path.of(sbstToolConfiguration.getRoot(), sbstToolConfiguration.getGeneratedClassesPackage().replace('.', '/')));

        final var minimizeTestSuit = testSuiteSizeMinimizer.minimizeTestsSize(output, classInfo);
        String sourceCode = testGenerator.generateTestFor(minimizeTestSuit, classInfo);
        classWriter.writeToFile(minimizeTestSuit.getTestSuiteName(), sourceCode);
    }
}

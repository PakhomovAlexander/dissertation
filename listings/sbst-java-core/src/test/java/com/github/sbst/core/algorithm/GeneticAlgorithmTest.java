package com.github.sbst.core.algorithm;

import static com.github.sbst.core.info.TestBuildersDsl.classInfo;
import static com.github.sbst.core.info.TestBuildersDsl.given;
import static com.github.sbst.core.info.TestBuildersDsl.methodInfo;
import static com.github.sbst.core.info.TestBuildersDsl.parameterInfo;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.sbst.ClassCompiler;
import com.github.sbst.JUnit5TestRunnerWithCov;
import com.github.sbst.TestGenerator;
import com.github.sbst.SbstToolConfiguration;
import com.github.sbst.core.info.AccessModifier;
import com.github.sbst.core.info.ClassInfo;
import com.github.sbst.core.info.PrimitiveType;
import com.github.sbst.core.info.PrimitiveTypeInfo;
import com.github.sbst.testclasses.SampleClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneticAlgorithmTest {

    GeneticAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        final var configuration = SbstToolConfiguration.fromClassUnderTest(SampleClass.class).setRoot("src/test/java");

        final var geneticAlgorithmConfig = new GeneticAlgorithmConfig();
        final var compiler = new ClassCompiler(configuration);
        final var testRunner = new JUnit5TestRunnerWithCov(SampleClass.class, configuration);
        final var testGenerator = new TestGenerator(configuration);
        final var fitnessFunction = new FitnessFunction(testRunner, compiler, testGenerator, configuration);

        algorithm = new GeneticAlgorithm(geneticAlgorithmConfig, compiler, testRunner, testGenerator, fitnessFunction);
    }

    @Test
    void shouldGenerate2TestsForSampleClassAndReachFullBranchCov() {
        // Given
        ClassInfo givenClassInfo = given(
                classInfo()
                        .className("SampleClass")
                        .pckg("com.github.sbst.testclasses")
                        .method(
                                methodInfo()
                                        .methodName("calculate")
                                        .accessModifier(AccessModifier.PUBLIC)
                                        .returnType(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                        .inputParameters(
                                                parameterInfo()
                                                        .name("a")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT)),
                                                parameterInfo()
                                                        .name("b")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                        ))
        );

        // When
        AlgorithmOutput algorithmOutput = algorithm.run(givenClassInfo);

        // Then 100 % coverage achieved
        assertThat(algorithmOutput.getTotalCov()).isEqualTo(1.0);
    }
}
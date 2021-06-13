package com.github.sbst.core;

import static com.github.sbst.core.info.TestBuildersDsl.assertBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.classInfo;
import static com.github.sbst.core.info.TestBuildersDsl.fieldDeclarationBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.fieldDeclarationWithAssignBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.given;
import static com.github.sbst.core.info.TestBuildersDsl.localVariableDeclarationWithAssignBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.methodCallBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.methodInfo;
import static com.github.sbst.core.info.TestBuildersDsl.parameterInfo;
import static com.github.sbst.core.info.TestBuildersDsl.testMethodBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.testSuiteBlueprint;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.github.sbst.core.blueprint.IntValuePlaceholder;
import com.github.sbst.core.blueprint.TestSuiteBlueprint;
import com.github.sbst.core.info.AccessModifier;
import com.github.sbst.core.info.ClassInfo;
import com.github.sbst.core.info.ClassTypeInfo;
import com.github.sbst.core.info.PrimitiveType;
import com.github.sbst.core.info.PrimitiveTypeInfo;
import com.github.sbst.testclasses.SampleClass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSuiteBlueprintsGeneratorTest {
    TestSuiteBlueprintsGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new TestSuiteBlueprintsGenerator();
    }

    @Test
    void shouldGenerateOneBlueprint() {
        // Given class with default constructor and one public method
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

        // When generate all possible blueprints combinations
        List<TestSuiteBlueprint> testSuiteBlueprints = generator.generateAllPossibleBlueprintsFrom(givenClassInfo);

        // Then expect only one test suite blueprint with one method blueprint generated
        var expectedTestSuitBlueprint = testSuiteBlueprint()
                .testSuiteName("SampleClassTest")
                .fieldDeclarationWithAssignBlueprint(
                        fieldDeclarationWithAssignBlueprint()
                                .fieldDeclarationBlueprint(fieldDeclarationBlueprint()
                                                                   .name("sampleClass")
                                                                   .typeInfo(new ClassTypeInfo("com.github.sbst.testclasses.SampleClass"))))
                .testMethodBlueprint(
                        testMethodBlueprint()
                                .methodName("shouldCalculate")
                                .givenParameter(
                                        localVariableDeclarationWithAssignBlueprint()
                                                .name("a")
                                                .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                .valuePlaceholder(new IntValuePlaceholder()))
                                .givenParameter(
                                        localVariableDeclarationWithAssignBlueprint()
                                                .name("b")
                                                .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                .valuePlaceholder(new IntValuePlaceholder()))
                                .methodCallBlueprint(
                                        methodCallBlueprint()
                                                .methodName("calculate")
                                                .inputParameters("a", "b")
                                                .resultVariableName("calculateResult")
                                                .scope("sampleClass")
                                                .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT)))
                                .expectedResult(
                                        localVariableDeclarationWithAssignBlueprint()
                                                .name("expectedResult")
                                                .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                .valuePlaceholder(new IntValuePlaceholder()))
                                .assertBlueprint(
                                        assertBlueprint()
                                                .actualValueVariableName("calculateResult")
                                                .expectedValueVariableName("expectedResult"))

                ).build();
        // And
        assertThat(testSuiteBlueprints).isEqualTo(List.of(expectedTestSuitBlueprint));
    }
}
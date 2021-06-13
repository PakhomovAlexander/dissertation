package com.github.sbst;

import static com.github.sbst.core.info.TestBuildersDsl.assertBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.fieldDeclarationBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.fieldDeclarationWithAssignMB;
import static com.github.sbst.core.info.TestBuildersDsl.given;
import static com.github.sbst.core.info.TestBuildersDsl.localVariableDeclarationWithAssignMB;
import static com.github.sbst.core.info.TestBuildersDsl.methodCallBlueprint;
import static com.github.sbst.core.info.TestBuildersDsl.testMethodMB;
import static com.github.sbst.core.info.TestBuildersDsl.testSuiteMB;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;

import com.github.sbst.core.blueprint.materialized.IntValueMaterializedPlaceholder;
import com.github.sbst.core.blueprint.materialized.NewClassInstanceMaterializedValuePlaceholder;
import com.github.sbst.core.info.ClassInfo;
import com.github.sbst.core.info.ClassTypeInfo;
import com.github.sbst.core.info.PrimitiveType;
import com.github.sbst.core.info.PrimitiveTypeInfo;
import com.github.sbst.testclasses.SampleClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGeneratorTest {

    TestGenerator testGenerator;

    @BeforeEach
    void setUp() {
        testGenerator = new TestGenerator(SbstToolConfiguration.fromClassUnderTest(SampleClass.class).setRoot("src/test/java"));
    }

    @Test
    void shouldGenerateTestFromGivenClass() {
        // Given
        ClassInfo givenClassInfo = new ClassInspector().inspect(Path.of("src/test/java/com/github/sbst/testclasses/SampleClass.java"));
        // And
        var givenMaterializedTestSuit = given(
                testSuiteMB()
                        .testSuiteName("SampleClassTest")
                        .fieldDeclarationWithAssignBlueprint(
                                fieldDeclarationWithAssignMB()
                                        .fieldDeclarationBlueprint(fieldDeclarationBlueprint()
                                                                           .name("sampleClass")
                                                                           .typeInfo(new ClassTypeInfo("com.github.sbst.testclasses.SampleClass"))
                                                                           .build())
                                        .materializedValuePlaceholder(new NewClassInstanceMaterializedValuePlaceholder(new ClassTypeInfo("com.github.sbst.testclasses.SampleClass")))) // todo: not used, should be fixed: replace with ConstructorMaterializerPlaceholder
                        .testMethodBlueprint(
                                testMethodMB()
                                        .methodName("shouldCalculate1")
                                        .givenParameter(
                                                localVariableDeclarationWithAssignMB()
                                                        .name("a")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                        .materializedValuePlaceholder(new IntValueMaterializedPlaceholder(0)))
                                        .givenParameter(
                                                localVariableDeclarationWithAssignMB()
                                                        .name("b")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                        .materializedValuePlaceholder(new IntValueMaterializedPlaceholder(0)))
                                        .methodCallBlueprint(
                                                methodCallBlueprint()
                                                        .methodName("calculate")
                                                        .inputParameters("a", "b")
                                                        .resultVariableName("calculateResult")
                                                        .scope("sampleClass")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT)))
                                        .expectedResult(
                                                localVariableDeclarationWithAssignMB()
                                                        .name("expectedResult")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                        .materializedValuePlaceholder(new IntValueMaterializedPlaceholder(0)))
                                        .assertBlueprint(
                                                assertBlueprint()
                                                        .actualValueVariableName("calculateResult")
                                                        .expectedValueVariableName("expectedResult")))
                        .testMethodBlueprint(
                                testMethodMB()
                                        .methodName("shouldCalculate2")
                                        .givenParameter(
                                                localVariableDeclarationWithAssignMB()
                                                        .name("a")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                        .materializedValuePlaceholder(new IntValueMaterializedPlaceholder(1)))
                                        .givenParameter(
                                                localVariableDeclarationWithAssignMB()
                                                        .name("b")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                        .materializedValuePlaceholder(new IntValueMaterializedPlaceholder(1)))
                                        .methodCallBlueprint(
                                                methodCallBlueprint()
                                                        .methodName("calculate")
                                                        .inputParameters("a", "b")
                                                        .resultVariableName("calculateResult")
                                                        .scope("sampleClass")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT)))
                                        .expectedResult(
                                                localVariableDeclarationWithAssignMB()
                                                        .name("expectedResult")
                                                        .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                                        .materializedValuePlaceholder(new IntValueMaterializedPlaceholder(0)))
                                        .assertBlueprint(
                                                assertBlueprint()
                                                        .actualValueVariableName("calculateResult")
                                                        .expectedValueVariableName("expectedResult"))

                        )
        );

        // When
        String generatedCode = testGenerator.generateTestFor(givenMaterializedTestSuit, givenClassInfo);

        // Then
        // language=JAVA
        String expectedGeneratedCode =
                "package com.github.sbst.testclasses.generated;\n" +
                        "import org.junit.jupiter.api.Test;\n" +
                        "public class SampleClassTest {\n" +
                        "  com.github.sbst.testclasses.SampleClass sampleClass = new com.github.sbst.testclasses.SampleClass();\n" +
                        "  @Test()\n" +
                        "  void shouldCalculate1() {\n" +
                        "    int a = 0;\n" +
                        "    int b = 0;\n" +
                        "    int calculateResult = sampleClass.calculate(a, b);\n" +
                        "    int expectedResult = 0;\n" +
                        "    org.assertj.core.api.Assertions.assertThat(calculateResult).isEqualTo(expectedResult);\n" +
                        "}\n" +
                        "  @Test()\n" +
                        "  void shouldCalculate2() {\n" +
                        "    int a = 1;\n" +
                        "    int b = 1;\n" +
                        "    int calculateResult = sampleClass.calculate(a, b);\n" +
                        "    int expectedResult = 2;\n" +
                        "    org.assertj.core.api.Assertions.assertThat(calculateResult).isEqualTo(expectedResult);\n" +
                        "}" +
                        "\n}";
        assertThat(generatedCode).isEqualToIgnoringWhitespace(expectedGeneratedCode);
    }
}
package com.github.sbst;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.github.sbst.testclasses.SampleClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassCompilerTest {
    ClassCompiler classCompiler;

    @BeforeEach
    void setUp() {
        classCompiler = new ClassCompiler(SbstToolConfiguration.fromClassUnderTest(SampleClass.class).setRoot("src/test/java"));
    }

    @Test
    void shouldCompileValidCode() {
        // Given
        // language=JAVA
        var validCode = "package com.github.sbst.generated;\n" +
                "public class SampleClassTest {\n" +
                "  com.github.sbst.testclasses.SampleClass sampleClass = new com.github.sbst.testclasses.SampleClass();\n" +
                "  @org.junit.jupiter.api.Test()\n" +
                "  void shouldCalculate() {\n" +
                "    int a = 0;\n" +
                "    int b = 0;\n" +
                "    int calculateResult = sampleClass.calculate(a, b);\n" +
                "    int expectedResult = 0;\n" +
                "    org.assertj.core.api.Assertions.assertThat(calculateResult).isEqualTo(expectedResult);\n" +
                "}" +
                "\n}";

        // Expect
        assertDoesNotThrow(() -> classCompiler.compile("com.github.sbst.generated.SampleClassTest", validCode));
    }
}
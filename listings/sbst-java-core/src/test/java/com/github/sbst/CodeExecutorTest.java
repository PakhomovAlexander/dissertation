package com.github.sbst;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CodeExecutorTest {

    CodeExecutor codeExecutor;

    @BeforeEach
    void setUp() {
        codeExecutor = new CodeExecutor();
    }

    @Test
    void shouldExecuteGivenMethodThatReturnsInt() {
        // Given
        var className = "com.sbst.generated.TestCode";
        // language=JAVA
        var code = "package com.sbst.generated;\n" +
                "class TestCode implements java.util.function.Supplier<Integer> {\n" +
                "    public Integer get() {\n" +
                "        return 9;\n" +
                "    }\n" +
                "}\n";

        // When
        int result = codeExecutor.<Integer>executeCode(className, code);

        // Then
        assertThat(result).isEqualTo(9);
    }

    @Test
    void shouldExecuteGeneratedCode() {
        // Given
        var className = "com.github.sbst.generated.SampleClassSupplyWrapper";
        // language=JAVA
        var code = "package com.github.sbst.generated;" +
                "class SampleClassSupplyWrapper implements java.util.function.Supplier<Integer> {\n" +
                "\n" +
                "    public Integer get() {\n" +
                "        com.github.sbst.testclasses.SampleClass sampleClass = new com.github.sbst.testclasses.SampleClass();\n" +
                "        int a = 0;\n" +
                "        int b = 0;\n" +
                "        int calculateResult = sampleClass.calculate(a, b);\n" +
                "        return calculateResult;\n" +
                "    }\n" +
                "}";

        // When
        int result = codeExecutor.<Integer>executeCode(className, code);

        // Then
        assertThat(result).isEqualTo(0);
    }
}
package com.github.sbst;

import static com.github.sbst.core.info.TestBuildersDsl.classInfo;
import static com.github.sbst.core.info.TestBuildersDsl.methodInfo;
import static com.github.sbst.core.info.TestBuildersDsl.parameterInfo;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;

import com.github.sbst.core.info.AccessModifier;
import com.github.sbst.core.info.ClassInfo;
import com.github.sbst.core.info.PrimitiveType;
import com.github.sbst.core.info.PrimitiveTypeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassInspectorTest {
    ClassInspector classInspector;

    @BeforeEach
    void setUp() {
        classInspector = new ClassInspector();
    }

    @Test
    void shouldInspectClassWithDefaultConstructorAndOnePublicMethod() {
        // Given
        var givenPath = Path.of("src/test/java/com/github/sbst/testclasses/SampleClass.java");

        // When
        ClassInfo actualClassInfo = classInspector.inspect(givenPath);

        // Then
        ClassInfo expectedClassInfo = classInfo()
                .pckg("com.github.sbst.testclasses")
                .className("SampleClass")
                .method(methodInfo().methodName("calculate")
                                    .accessModifier(AccessModifier.PUBLIC)
                                    .returnType(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                    .inputParameters(
                                            parameterInfo().name("a")
                                                           .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT)),
                                            parameterInfo().name("b")
                                                           .typeInfo(PrimitiveTypeInfo.from(PrimitiveType.INT))
                                    )).build();
        // And
        assertThat(actualClassInfo).isEqualTo(expectedClassInfo);
    }
}
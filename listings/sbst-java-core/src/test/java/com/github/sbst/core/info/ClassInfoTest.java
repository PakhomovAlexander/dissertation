package com.github.sbst.core.info;

import static com.github.sbst.core.info.TestBuildersDsl.classInfo;
import static com.github.sbst.core.info.TestBuildersDsl.given;
import static com.github.sbst.core.info.TestBuildersDsl.methodInfo;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class ClassInfoTest {

    @Test
    void shouldReturnAllPublicMethods() {
        // Given
        var publicMethod1 = given(
                methodInfo().methodName("method1").accessModifier(AccessModifier.PUBLIC)
        );
        var publicMethod2 = given(
                methodInfo().methodName("method2").accessModifier(AccessModifier.PUBLIC)
        );
        var privateMethod = given(
                methodInfo().methodName("method3").accessModifier(AccessModifier.PRIVATE)
        );

        var givenClassInfo = given(
                classInfo().className("sampleClass").methods(publicMethod1, publicMethod2, privateMethod)
        );

        // When
        List<MethodInfo> allPublicMethods = givenClassInfo.getAllPublicMethods();

        // Then
        assertThat(allPublicMethods).isEqualTo(List.of(publicMethod1, publicMethod2));
    }
}
package com.github.sbst.core.blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.LocalVariableDeclarationWithAssignBlueprint.LocalVariableDeclarationWithAssignBlueprintBuilder;

public class TestMethodBlueprint {
    private final String methodName;
    private final List<? extends LocalVariableDeclarationWithAssignBlueprint> givenParameters;
    private final MethodCallBlueprint methodCallBlueprint;
    private final LocalVariableDeclarationWithAssignBlueprint expectedResult;
    private final AssertBlueprint assertBlueprint;

    protected TestMethodBlueprint(String methodName,
                                  List<? extends LocalVariableDeclarationWithAssignBlueprint> givenParameters,
                                  MethodCallBlueprint methodCallBlueprint,
                                  LocalVariableDeclarationWithAssignBlueprint expectedResult,
                                  AssertBlueprint assertBlueprint) {
        this.methodName = methodName;
        this.givenParameters = givenParameters;
        this.methodCallBlueprint = methodCallBlueprint;
        this.expectedResult = expectedResult;
        this.assertBlueprint = assertBlueprint;
    }

    public static TestMethodBlueprintBuilder<TestMethodBlueprint> builder() {
        return new TestMethodBlueprintBuilder<>();
    }

    public String getMethodName() {
        return methodName;
    }

    public List<? extends LocalVariableDeclarationWithAssignBlueprint> getGivenParameters() {
        return givenParameters;
    }

    public MethodCallBlueprint getMethodCallBlueprint() {
        return methodCallBlueprint;
    }

    public LocalVariableDeclarationWithAssignBlueprint getExpectedResult() {
        return expectedResult;
    }

    public AssertBlueprint getAssertBlueprint() {
        return assertBlueprint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestMethodBlueprint)) return false;
        TestMethodBlueprint that = (TestMethodBlueprint) o;
        return Objects.equals(getMethodName(), that.getMethodName()) && Objects.equals(getGivenParameters(), that.getGivenParameters()) && Objects.equals(getMethodCallBlueprint(), that.getMethodCallBlueprint()) && Objects.equals(getExpectedResult(), that.getExpectedResult()) && Objects.equals(getAssertBlueprint(), that.getAssertBlueprint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethodName(), getGivenParameters(), getMethodCallBlueprint(), getExpectedResult(), getAssertBlueprint());
    }

    @Override
    public String toString() {
        return "TestMethodBlueprint{" +
                "methodName='" + methodName + '\'' +
                ", givenParameters=" + givenParameters +
                ", methodCallBlueprint=" + methodCallBlueprint +
                ", expectedResult=" + expectedResult +
                ", assertBlueprint=" + assertBlueprint +
                '}';
    }

    public static class TestMethodBlueprintBuilder<T extends TestMethodBlueprint>
            implements GenericBuilder<T, TestMethodBlueprintBuilder<T>> {

        private String methodName;
        private List<LocalVariableDeclarationWithAssignBlueprint> givenParameters = new ArrayList<>();
        private MethodCallBlueprint methodCallBlueprint;
        private LocalVariableDeclarationWithAssignBlueprint expectedResult;
        private AssertBlueprint assertBlueprint;

        public TestMethodBlueprintBuilder<T> methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public TestMethodBlueprintBuilder<T> givenParameters(List<? extends LocalVariableDeclarationWithAssignBlueprint> givenParameters) {
            this.givenParameters = (List<LocalVariableDeclarationWithAssignBlueprint>) givenParameters;
            return this;
        }

        public TestMethodBlueprintBuilder<T> givenParameter(
                LocalVariableDeclarationWithAssignBlueprintBuilder<? extends LocalVariableDeclarationWithAssignBlueprint> givenParameter) {
            this.givenParameters.add(givenParameter.build());
            return this;
        }

        public TestMethodBlueprintBuilder<T> methodCallBlueprint(MethodCallBlueprint methodCallBlueprint) {
            this.methodCallBlueprint = methodCallBlueprint;
            return this;
        }

        public TestMethodBlueprintBuilder<T> methodCallBlueprint(MethodCallBlueprint.MethodCallBlueprintBuilder methodCallBlueprintBuilder) {
            this.methodCallBlueprint = methodCallBlueprintBuilder.build();
            return this;
        }

        public TestMethodBlueprintBuilder<T> expectedResult(LocalVariableDeclarationWithAssignBlueprint expectedResult) {
            this.expectedResult = expectedResult;
            return this;
        }

        public TestMethodBlueprintBuilder<T> expectedResult(LocalVariableDeclarationWithAssignBlueprintBuilder expectedResultBuilder) {
            this.expectedResult = expectedResultBuilder.build();
            return this;
        }

        public TestMethodBlueprintBuilder<T> assertBlueprint(AssertBlueprint assertBlueprint) {
            this.assertBlueprint = assertBlueprint;
            return this;
        }

        public TestMethodBlueprintBuilder<T> assertBlueprint(AssertBlueprint.AssertBlueprintBuilder assertBlueprintBuilder) {
            this.assertBlueprint = assertBlueprintBuilder.build();
            return this;
        }

        public TestMethodBlueprintBuilder<? super T> from(T original) {
            return TestMethodBlueprint.builder()
                                      .methodName(original.getMethodName())
                                      .givenParameters(original.getGivenParameters())
                                      .methodCallBlueprint(original.getMethodCallBlueprint())
                                      .assertBlueprint(original.getAssertBlueprint());
        }

        @Override
        public T build() {
            Objects.requireNonNull(methodName, "Method name must not be null");
            Objects.requireNonNull(methodCallBlueprint, "Method call blueprint must not be null");

            return (T) new TestMethodBlueprint(methodName, List.copyOf(givenParameters), methodCallBlueprint, expectedResult, assertBlueprint);
        }
    }
}

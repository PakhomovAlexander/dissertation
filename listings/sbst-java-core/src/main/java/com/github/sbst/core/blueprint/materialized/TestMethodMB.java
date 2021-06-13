package com.github.sbst.core.blueprint.materialized;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.AssertBlueprint;
import com.github.sbst.core.blueprint.LocalVariableDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.MethodCallBlueprint;
import com.github.sbst.core.blueprint.TestMethodBlueprint;

public class TestMethodMB extends TestMethodBlueprint {
    protected TestMethodMB(String methodName,
                           List<LocalVariableDeclarationWithAssignMB> givenParameters,
                           MethodCallBlueprint methodCallBlueprint,
                           LocalVariableDeclarationWithAssignMB expectedResult,
                           AssertBlueprint assertBlueprint) {
        super(methodName, givenParameters, methodCallBlueprint, expectedResult, assertBlueprint);
    }


    @Override
    public LocalVariableDeclarationWithAssignMB getExpectedResult() {
        return (LocalVariableDeclarationWithAssignMB) super.getExpectedResult();
    }

    @Override
    public List<LocalVariableDeclarationWithAssignMB> getGivenParameters() {
        return (List<LocalVariableDeclarationWithAssignMB>) super.getGivenParameters();
    }

    public static TestMethodMBB<TestMethodMB> materializedBuilder() {
        return new TestMethodMBB<>();
    }

    public static class TestMethodMBB<T extends TestMethodMB>
            implements GenericBuilder<T, TestMethodMBB<T>> {

        private String methodName;
        private List<LocalVariableDeclarationWithAssignMB> givenParameters = new ArrayList<>();
        private MethodCallBlueprint methodCallBlueprint;
        private LocalVariableDeclarationWithAssignMB expectedResult;
        private AssertBlueprint assertBlueprint;

        public TestMethodMBB<T> methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public TestMethodMBB<T> givenParameters(List<? extends LocalVariableDeclarationWithAssignMB> givenParameters) {
            this.givenParameters = (List<LocalVariableDeclarationWithAssignMB>) givenParameters;
            return this;
        }

        public TestMethodMBB<T> givenParameter(
                LocalVariableDeclarationWithAssignMB.LocalVariableDeclarationWithAssignMBB<? extends LocalVariableDeclarationWithAssignMB> givenParameter) {
            this.givenParameters.add(givenParameter.build());
            return this;
        }

        public TestMethodMBB<T> methodCallBlueprint(MethodCallBlueprint methodCallBlueprint) {
            this.methodCallBlueprint = methodCallBlueprint;
            return this;
        }

        public TestMethodMBB<T> methodCallBlueprint(MethodCallBlueprint.MethodCallBlueprintBuilder methodCallBlueprintBuilder) {
            this.methodCallBlueprint = methodCallBlueprintBuilder.build();
            return this;
        }

        public TestMethodMBB<T> expectedResult(LocalVariableDeclarationWithAssignMB expectedResult) {
            this.expectedResult = expectedResult;
            return this;
        }

        public TestMethodMBB<T> expectedResult(LocalVariableDeclarationWithAssignMB.LocalVariableDeclarationWithAssignMBB expectedResultBuilder) {
            this.expectedResult = expectedResultBuilder.build();
            return this;
        }

        public TestMethodMBB<T> assertBlueprint(AssertBlueprint assertBlueprint) {
            this.assertBlueprint = assertBlueprint;
            return this;
        }

        public TestMethodMBB<T> assertBlueprint(AssertBlueprint.AssertBlueprintBuilder assertBlueprintBuilder) {
            this.assertBlueprint = assertBlueprintBuilder.build();
            return this;
        }

        public TestMethodMBB<? super T> from(T original) {
            return TestMethodMB.materializedBuilder()
                               .methodName(original.getMethodName())
                               .givenParameters((List<LocalVariableDeclarationWithAssignMB>) original.getGivenParameters())
                               .methodCallBlueprint(original.getMethodCallBlueprint())
                               .expectedResult(original.getExpectedResult())
                               .assertBlueprint(original.getAssertBlueprint());
        }

        @Override
        public T build() {
            Objects.requireNonNull(methodName, "Method name must not be null");
            Objects.requireNonNull(methodCallBlueprint, "Method call blueprint must not be null");

            return (T) new TestMethodMB(methodName, List.copyOf(givenParameters), methodCallBlueprint, expectedResult, assertBlueprint);
        }
    }
}

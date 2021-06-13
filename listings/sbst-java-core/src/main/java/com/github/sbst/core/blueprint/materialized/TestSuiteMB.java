package com.github.sbst.core.blueprint.materialized;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.BeforeEachMethodBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.TestMethodBlueprint;
import com.github.sbst.core.blueprint.TestSuiteBlueprint;

public class TestSuiteMB extends TestSuiteBlueprint {
    private TestSuiteMB(String testSuiteName,
                        BeforeEachMethodMB beforeEachMethodMaterializedBlueprintBlueprint,
                        List<FieldDeclarationMB> fieldDeclarationMBS,
                        List<FieldDeclarationWithAssignMB> fieldDeclarationWithAssignMBS,
                        List<TestMethodMB> testMethodMaterializedBlueprints) {

        super(testSuiteName, beforeEachMethodMaterializedBlueprintBlueprint, fieldDeclarationMBS,
              fieldDeclarationWithAssignMBS, testMethodMaterializedBlueprints
        );
    }

    @Override
    public List<? extends FieldDeclarationBlueprint> getFieldDeclarationBlueprints() {
        return super.getFieldDeclarationBlueprints();
    }

    @Override
    public List<FieldDeclarationWithAssignMB> getFieldDeclarationWithAssignBlueprints() {
        return (List<FieldDeclarationWithAssignMB>) super.getFieldDeclarationWithAssignBlueprints();
    }

    @Override
    public BeforeEachMethodMB getBeforeEachMethodBlueprint() {
        return (BeforeEachMethodMB) super.getBeforeEachMethodBlueprint();
    }

    public static TestSuiteMBB<TestSuiteMB> materializedBuilder() {
        return new TestSuiteMBB<>();
    }

    public List<? extends TestMethodMB> getTestMethodBlueprints() {
        return super.getTestMethodBlueprints()
                    .stream()
                    .map(TestMethodMB.class::cast)
                    .collect(Collectors.toList());
    }

    public static class TestSuiteMBB<T extends TestSuiteMB> implements GenericBuilder<T, TestSuiteMBB<T>> {

        protected String testSuiteName;
        protected BeforeEachMethodMB beforeEachMethodBlueprint;
        protected List<FieldDeclarationMB> fieldDeclarationBlueprints = new ArrayList<>();
        protected List<FieldDeclarationWithAssignMB> fieldDeclarationWithAssignBlueprints = new ArrayList<>();
        protected List<TestMethodMB> testMethodBlueprints = new ArrayList<>();

        public TestSuiteMBB<T> testSuiteName(String testSuiteName) {
            this.testSuiteName = testSuiteName;
            return this;
        }

        public TestSuiteMBB<T> beforeEachMethodBlueprint(BeforeEachMethodMB beforeEachMethodBlueprint) {
            this.beforeEachMethodBlueprint = beforeEachMethodBlueprint;
            return this;
        }

        public TestSuiteMBB<T> fieldDeclarationBlueprints(List<? extends FieldDeclarationMB> fieldDeclarationBlueprints) {
            this.fieldDeclarationBlueprints = (List<FieldDeclarationMB>) fieldDeclarationBlueprints;
            return this;
        }

        public TestSuiteMBB<T> fieldDeclarationBlueprint(FieldDeclarationMB.FieldDeclarationMBB<FieldDeclarationMB> fieldDeclarationBlueprintBuilder) {
            this.fieldDeclarationBlueprints.add(fieldDeclarationBlueprintBuilder.build());
            return this;
        }

        public TestSuiteMBB<T> fieldDeclarationWithAssignBlueprints(List<FieldDeclarationWithAssignMB> fieldDeclarationWithAssignBlueprints) {
            this.fieldDeclarationWithAssignBlueprints = fieldDeclarationWithAssignBlueprints;
            return this;
        }

        public TestSuiteMBB<T> fieldDeclarationWithAssignBlueprint(FieldDeclarationWithAssignMB.FieldDeclarationWithAssignMBB<FieldDeclarationWithAssignMB>
                                                                           fieldDeclarationWithAssignBlueprintBuilder) {
            this.fieldDeclarationWithAssignBlueprints.add(fieldDeclarationWithAssignBlueprintBuilder.build());
            return this;
        }

        public TestSuiteMBB<T> fieldDeclarationWithAssignBlueprint(FieldDeclarationWithAssignMB fieldDeclarationWithAssignBlueprint) {
            this.fieldDeclarationWithAssignBlueprints.add(fieldDeclarationWithAssignBlueprint);
            return this;
        }

        public TestSuiteMBB<T> testMethodBlueprints(List<? extends TestMethodMB> testMethodBlueprints) {
            this.testMethodBlueprints = (List<TestMethodMB>) testMethodBlueprints;
            return this;
        }

        public TestSuiteMBB<T> testMethodBlueprint(TestMethodMB.TestMethodMBB<TestMethodMB> testMethodBlueprintBuilder) {
            this.testMethodBlueprints.add(testMethodBlueprintBuilder.build());
            return this;
        }

        public TestSuiteMBB<T> testMethodBlueprint(TestMethodMB testMethodBlueprint) {
            this.testMethodBlueprints.add(testMethodBlueprint);
            return this;
        }


        public TestSuiteMBB<? super T> from(T original) {
            return TestSuiteMB.materializedBuilder()
                              .testSuiteName(original.getTestSuiteName())
                              .fieldDeclarationWithAssignBlueprints(original.getFieldDeclarationWithAssignBlueprints())
                              .beforeEachMethodBlueprint(original.getBeforeEachMethodBlueprint())
                              .testMethodBlueprints(original.getTestMethodBlueprints());
        }

        @Override
        public T build() {
            Objects.requireNonNull(testSuiteName, "Test suite name must not be null");

            return (T) new TestSuiteMB(testSuiteName, beforeEachMethodBlueprint, List.copyOf(fieldDeclarationBlueprints),
                                       fieldDeclarationWithAssignBlueprints, List.copyOf(testMethodBlueprints)
            );
        }
    }
}

package com.github.sbst.core.blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.sbst.core.GenericBuilder;

public class TestSuiteBlueprint {

    private final String testSuiteName;
    private final BeforeEachMethodBlueprint beforeEachMethodBlueprint;
    private final List<? extends FieldDeclarationBlueprint> fieldDeclarationBlueprints;
    private final List< ? extends FieldDeclarationWithAssignBlueprint> fieldDeclarationWithAssignBlueprints; // todo: FieldDeclarationWithAssign extends FieldDeclaration?
    private final List<? extends TestMethodBlueprint> testMethodBlueprints;

    protected TestSuiteBlueprint(String testSuiteName,
                               BeforeEachMethodBlueprint beforeEachMethodBlueprint,
                               List<? extends FieldDeclarationBlueprint> fieldDeclarationBlueprints,
                               List<? extends FieldDeclarationWithAssignBlueprint> fieldDeclarationWithAssignBlueprints,
                                 List<? extends TestMethodBlueprint> testMethodBlueprints) {
        this.testSuiteName = testSuiteName;
        this.beforeEachMethodBlueprint = beforeEachMethodBlueprint;
        this.fieldDeclarationBlueprints = fieldDeclarationBlueprints;
        this.fieldDeclarationWithAssignBlueprints = fieldDeclarationWithAssignBlueprints;
        this.testMethodBlueprints = testMethodBlueprints;
    }

    public static TestSuiteBlueprintBuilder<TestSuiteBlueprint> builder() {
        return new TestSuiteBlueprintBuilder<>();
    }

    public String getTestSuiteName() {
        return testSuiteName;
    }

    public BeforeEachMethodBlueprint getBeforeEachMethodBlueprint() {
        return beforeEachMethodBlueprint;
    }

    public List<? extends TestMethodBlueprint> getTestMethodBlueprints() {
        return testMethodBlueprints;
    }

    public List<? extends FieldDeclarationBlueprint> getFieldDeclarationBlueprints() {
        return fieldDeclarationBlueprints;
    }

    public List<? extends FieldDeclarationWithAssignBlueprint> getFieldDeclarationWithAssignBlueprints() {
        return fieldDeclarationWithAssignBlueprints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestSuiteBlueprint)) return false;
        TestSuiteBlueprint that = (TestSuiteBlueprint) o;
        return Objects.equals(getTestSuiteName(), that.getTestSuiteName()) && Objects.equals(getBeforeEachMethodBlueprint(), that.getBeforeEachMethodBlueprint()) &&
                Objects.equals(getFieldDeclarationBlueprints(), that.getFieldDeclarationBlueprints()) && Objects.equals(getTestMethodBlueprints(), that.getTestMethodBlueprints()) &&
                Objects.equals(getFieldDeclarationWithAssignBlueprints(), that.getFieldDeclarationWithAssignBlueprints()
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTestSuiteName(), getBeforeEachMethodBlueprint(), getFieldDeclarationBlueprints(), getTestMethodBlueprints());
    }

    @Override
    public String toString() {
        return "TestSuiteBlueprint{" +
                "testSuiteName='" + testSuiteName + '\'' +
                ", beforeEachMethodBlueprint=" + beforeEachMethodBlueprint +
                ", fieldDeclarationBlueprints=" + fieldDeclarationBlueprints +
                ", fieldDeclarationWithAssignBlueprints=" + fieldDeclarationWithAssignBlueprints +
                ", testMethodBlueprints=" + testMethodBlueprints +
                '}';
    }

    public static class TestSuiteBlueprintBuilder<T extends TestSuiteBlueprint>
            implements GenericBuilder<T, TestSuiteBlueprintBuilder<T>> {

        private String testSuiteName;
        private BeforeEachMethodBlueprint beforeEachMethodBlueprint; //todo implement
        private List<FieldDeclarationBlueprint> fieldDeclarationBlueprints = new ArrayList<>();
        private List<FieldDeclarationWithAssignBlueprint> fieldDeclarationWithAssignBlueprints = new ArrayList<>();
        private List<TestMethodBlueprint> testMethodBlueprints = new ArrayList<>();

        public TestSuiteBlueprintBuilder<T> testSuiteName(String testSuiteName) {
            this.testSuiteName = testSuiteName;
            return this;
        }

        public TestSuiteBlueprintBuilder<T> beforeEachMethodBlueprint(BeforeEachMethodBlueprint beforeEachMethodBlueprint) {
            this.beforeEachMethodBlueprint = beforeEachMethodBlueprint;
            return this;
        }

        public TestSuiteBlueprintBuilder<T> fieldDeclarationBlueprints(List<? extends FieldDeclarationBlueprint> fieldDeclarationBlueprints) {
            this.fieldDeclarationBlueprints = (List<FieldDeclarationBlueprint>) fieldDeclarationBlueprints;
            return this;
        }

        public TestSuiteBlueprintBuilder<T> fieldDeclarationBlueprint(FieldDeclarationBlueprint.FieldDeclarationBlueprintBuilder<FieldDeclarationBlueprint> fieldDeclarationBlueprintBuilder) {
            this.fieldDeclarationBlueprints.add(fieldDeclarationBlueprintBuilder.build());
            return this;
        }

        public TestSuiteBlueprintBuilder<T> fieldDeclarationWithAssignBlueprints(List<FieldDeclarationWithAssignBlueprint> fieldDeclarationWithAssignBlueprints) {
            this.fieldDeclarationWithAssignBlueprints = fieldDeclarationWithAssignBlueprints;
            return this;
        }

        public TestSuiteBlueprintBuilder<T> fieldDeclarationWithAssignBlueprint(FieldDeclarationWithAssignBlueprint.FieldDeclarationWithAssignBlueprintBuilder<FieldDeclarationWithAssignBlueprint> fieldDeclarationWithAssignBlueprintBuilder) {
            this.fieldDeclarationWithAssignBlueprints.add(fieldDeclarationWithAssignBlueprintBuilder.build());
            return this;
        }

        public TestSuiteBlueprintBuilder<T> fieldDeclarationWithAssignBlueprint(FieldDeclarationWithAssignBlueprint fieldDeclarationWithAssignBlueprint) {
            this.fieldDeclarationWithAssignBlueprints.add(fieldDeclarationWithAssignBlueprint);
            return this;
        }

        public TestSuiteBlueprintBuilder<T> testMethodBlueprints(List<? extends TestMethodBlueprint> testMethodBlueprints) {
            this.testMethodBlueprints = (List<TestMethodBlueprint>) testMethodBlueprints;
            return this;
        }

        public TestSuiteBlueprintBuilder<T> testMethodBlueprint(TestMethodBlueprint.TestMethodBlueprintBuilder<TestMethodBlueprint> testMethodBlueprintBuilder) {
            this.testMethodBlueprints.add(testMethodBlueprintBuilder.build());
            return this;
        }
        public TestSuiteBlueprintBuilder<T> testMethodBlueprint(TestMethodBlueprint testMethodBlueprint) {
            this.testMethodBlueprints.add(testMethodBlueprint);
            return this;
        }


        public TestSuiteBlueprintBuilder<? super T> from(T original) {
            return TestSuiteBlueprint.builder()
                                     .testSuiteName(original.getTestSuiteName())
                                     .beforeEachMethodBlueprint(original.getBeforeEachMethodBlueprint())
                                     .testMethodBlueprints(original.getTestMethodBlueprints());
        }

        @Override
        public T build() {
            Objects.requireNonNull(testSuiteName, "Test suite name must not be null");

            return (T) new TestSuiteBlueprint(testSuiteName, beforeEachMethodBlueprint, List.copyOf(fieldDeclarationBlueprints), fieldDeclarationWithAssignBlueprints, List.copyOf(testMethodBlueprints));
        }
    }
}

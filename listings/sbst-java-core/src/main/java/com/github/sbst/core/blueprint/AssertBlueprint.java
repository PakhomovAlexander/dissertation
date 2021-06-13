package com.github.sbst.core.blueprint;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;

public class AssertBlueprint {
    private final String actualValueVariableName;
    private final String expectedValueVariableName;

    private AssertBlueprint(String actualValueVariableName, String expectedValueVariableName) {
        this.actualValueVariableName = actualValueVariableName;
        this.expectedValueVariableName = expectedValueVariableName;
    }

    public static AssertBlueprintBuilder<AssertBlueprint> builder() {
        return new AssertBlueprintBuilder<>();
    }

    public String getActualValueVariableName() {
        return actualValueVariableName;
    }

    public String getExpectedValueVariableName() {
        return expectedValueVariableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssertBlueprint)) return false;
        AssertBlueprint that = (AssertBlueprint) o;
        return Objects.equals(getActualValueVariableName(), that.getActualValueVariableName()) && Objects.equals(getExpectedValueVariableName(), that.getExpectedValueVariableName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActualValueVariableName(), getExpectedValueVariableName());
    }

    @Override
    public String toString() {
        return "AssertBlueprint{" +
                "actualValueVariableName='" + actualValueVariableName + '\'' +
                ", expectedValueVariableName='" + expectedValueVariableName + '\'' +
                '}';
    }

    public static class AssertBlueprintBuilder<T extends AssertBlueprint>
            implements GenericBuilder<T, AssertBlueprintBuilder<T>> {

        private String actualValueVariableName;
        private String expectedValueVariableName;

        public AssertBlueprintBuilder<T> actualValueVariableName(String actualValueVariableName) {
            this.actualValueVariableName = actualValueVariableName;
            return this;
        }

        public AssertBlueprintBuilder<T> expectedValueVariableName(String expectedValueVariableName) {
            this.expectedValueVariableName = expectedValueVariableName;
            return this;
        }

        public AssertBlueprintBuilder<? super T> from(T original) {
            return AssertBlueprint.builder()
                                  .actualValueVariableName(original.getActualValueVariableName())
                                  .expectedValueVariableName(original.getExpectedValueVariableName());
        }

        @Override
        public T build() {
            Objects.requireNonNull(actualValueVariableName, "Actual variable name must not be null");
            Objects.requireNonNull(expectedValueVariableName, "Expected variable name must not be null");

            return (T) new AssertBlueprint(actualValueVariableName, expectedValueVariableName);
        }
    }
}

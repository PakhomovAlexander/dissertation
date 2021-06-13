package com.github.sbst.core.blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.sbst.core.GenericBuilder;

public class BeforeEachMethodBlueprint {
    private final List<? extends FieldAssignmentBlueprint> fieldAssignmentBlueprints;

    protected BeforeEachMethodBlueprint(List<? extends FieldAssignmentBlueprint> fieldAssignmentBlueprints) {this.fieldAssignmentBlueprints = fieldAssignmentBlueprints;}

    public static BeforeEachMethodBlueprintBuilder<BeforeEachMethodBlueprint> builder() {
        return new BeforeEachMethodBlueprintBuilder<>();
    }

    public List<? extends FieldAssignmentBlueprint> getFieldAssignmentBlueprints() {
        return fieldAssignmentBlueprints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeforeEachMethodBlueprint)) return false;
        BeforeEachMethodBlueprint that = (BeforeEachMethodBlueprint) o;
        return Objects.equals(getFieldAssignmentBlueprints(), that.getFieldAssignmentBlueprints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFieldAssignmentBlueprints());
    }

    @Override
    public String toString() {
        return "BeforeEachMethodBlueprint{" +
                "fieldAssignmentBlueprints=" + fieldAssignmentBlueprints +
                '}';
    }

    public static class BeforeEachMethodBlueprintBuilder<T extends BeforeEachMethodBlueprint>
            implements GenericBuilder<T, BeforeEachMethodBlueprintBuilder<T>> {

        private List<FieldAssignmentBlueprint> fieldAssignmentBlueprints = new ArrayList<>();

        public BeforeEachMethodBlueprintBuilder<T> fieldAssignmentBlueprints(List<? extends FieldAssignmentBlueprint> fieldAssignmentBlueprints) {
            this.fieldAssignmentBlueprints = (List<FieldAssignmentBlueprint>) fieldAssignmentBlueprints;
            return this;
        }

        public BeforeEachMethodBlueprintBuilder<? super T> from(T original) {
            return BeforeEachMethodBlueprint.builder()
                                            .fieldAssignmentBlueprints(original.getFieldAssignmentBlueprints());
        }

        @Override
        public T build() {
            return (T) new BeforeEachMethodBlueprint(List.copyOf(fieldAssignmentBlueprints));
        }
    }
}

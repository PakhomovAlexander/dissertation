package com.github.sbst.core.blueprint;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.FieldAssignmentBlueprint.FieldAssignmentBlueprintBuilder;
import com.github.sbst.core.blueprint.FieldDeclarationBlueprint.FieldDeclarationBlueprintBuilder;

public class FieldDeclarationWithAssignBlueprint {
    private final FieldDeclarationBlueprint fieldDeclarationBlueprint;
    private final FieldAssignmentBlueprint fieldAssignmentBlueprint;


    protected FieldDeclarationWithAssignBlueprint(FieldDeclarationBlueprint fieldDeclarationBlueprint, FieldAssignmentBlueprint fieldAssignmentBlueprint) {
        this.fieldDeclarationBlueprint = fieldDeclarationBlueprint;
        this.fieldAssignmentBlueprint = fieldAssignmentBlueprint;
    }

    public FieldDeclarationBlueprint getFieldDeclarationBlueprint() {
        return fieldDeclarationBlueprint;
    }

    public FieldAssignmentBlueprint getFieldAssignmentBlueprint() {
        return fieldAssignmentBlueprint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldDeclarationWithAssignBlueprint)) return false;
        FieldDeclarationWithAssignBlueprint that = (FieldDeclarationWithAssignBlueprint) o;
        return Objects.equals(getFieldDeclarationBlueprint(), that.getFieldDeclarationBlueprint()) && Objects.equals(getFieldAssignmentBlueprint(), that.getFieldAssignmentBlueprint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFieldDeclarationBlueprint(), getFieldAssignmentBlueprint());
    }

    @Override
    public String toString() {
        return "FieldDeclarationWithAssignBlueprint{" +
                "fieldDeclarationBlueprint=" + fieldDeclarationBlueprint +
                ", fieldAssignmentBlueprint=" + fieldAssignmentBlueprint +
                '}';
    }

    public static FieldDeclarationWithAssignBlueprintBuilder<FieldDeclarationWithAssignBlueprint> builder() {
        return new FieldDeclarationWithAssignBlueprintBuilder<>();
    }

    public static class FieldDeclarationWithAssignBlueprintBuilder<T extends FieldDeclarationWithAssignBlueprint>
            implements GenericBuilder<T, FieldDeclarationWithAssignBlueprintBuilder<T>> {

        private FieldDeclarationBlueprintBuilder<? extends FieldDeclarationBlueprint> fieldDeclarationBlueprintBuilder;
        private FieldAssignmentBlueprintBuilder<? extends FieldAssignmentBlueprint> fieldAssignmentBlueprintBuilder;

        public FieldDeclarationWithAssignBlueprintBuilder<T> fieldDeclarationBlueprint(FieldDeclarationBlueprint fieldDeclarationBlueprint) {
            this.fieldDeclarationBlueprintBuilder = FieldDeclarationBlueprint.builder().from(fieldDeclarationBlueprint);
            return this;
        }

        public FieldDeclarationWithAssignBlueprintBuilder<T> fieldDeclarationBlueprint(FieldDeclarationBlueprintBuilder fieldDeclarationBlueprintBuilder) {
            this.fieldDeclarationBlueprintBuilder = FieldDeclarationBlueprint.builder().from(fieldDeclarationBlueprintBuilder.build());
            return this;
        }


        public FieldDeclarationWithAssignBlueprintBuilder<? super T> from(T original) {
            return FieldDeclarationWithAssignBlueprint.builder()
                                                      .fieldDeclarationBlueprint(original.getFieldDeclarationBlueprint());
        }

        @Override
        public T build() {
            Objects.requireNonNull(fieldDeclarationBlueprintBuilder, "Field declaration blueprint must not be null");

            FieldDeclarationBlueprint fieldDeclarationBlueprint = fieldDeclarationBlueprintBuilder.build();
            fieldAssignmentBlueprintBuilder = FieldAssignmentBlueprint.builder()
                                                                      .targetFieldDeclarationBlueprint(fieldDeclarationBlueprint)
                                                                      .valuePlaceholder(new IntValuePlaceholder()); //fixme

            return (T) new FieldDeclarationWithAssignBlueprint(fieldDeclarationBlueprint, fieldAssignmentBlueprintBuilder.build());
        }
    }
}

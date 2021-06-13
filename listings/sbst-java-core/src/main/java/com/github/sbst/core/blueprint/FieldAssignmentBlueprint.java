package com.github.sbst.core.blueprint;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;

public class FieldAssignmentBlueprint {
    private final FieldDeclarationBlueprint targetFieldDeclarationBlueprint;
    private final ValuePlaceholder valuePlaceholder;

    protected FieldAssignmentBlueprint(FieldDeclarationBlueprint targetFieldDeclarationBlueprint, ValuePlaceholder valuePlaceholder) {
        this.targetFieldDeclarationBlueprint = targetFieldDeclarationBlueprint;
        this.valuePlaceholder = valuePlaceholder;
    }

    public static FieldAssignmentBlueprintBuilder<FieldAssignmentBlueprint> builder() {
        return new FieldAssignmentBlueprintBuilder<>();
    }

    public FieldDeclarationBlueprint getTargetFieldDeclarationBlueprint() {
        return targetFieldDeclarationBlueprint;
    }

    public ValuePlaceholder getValuePlaceholder() {
        return valuePlaceholder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldAssignmentBlueprint)) return false;
        FieldAssignmentBlueprint that = (FieldAssignmentBlueprint) o;
        return Objects.equals(getTargetFieldDeclarationBlueprint(), that.getTargetFieldDeclarationBlueprint()) && Objects.equals(getValuePlaceholder(), that.getValuePlaceholder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTargetFieldDeclarationBlueprint(), getValuePlaceholder());
    }

    @Override
    public String toString() {
        return "FieldAssignmentBlueprint{" +
                "targetFieldDeclarationBlueprint=" + targetFieldDeclarationBlueprint +
                ", valuePlaceholder=" + valuePlaceholder +
                '}';
    }

    public static class FieldAssignmentBlueprintBuilder<T extends FieldAssignmentBlueprint>
            implements GenericBuilder<T, FieldAssignmentBlueprintBuilder<T>> {


        private FieldDeclarationBlueprint targetFieldDeclarationBlueprint;
        private ValuePlaceholder valuePlaceholder;

        public FieldAssignmentBlueprintBuilder<T> targetFieldDeclarationBlueprint(FieldDeclarationBlueprint targetFieldDeclarationBlueprint) {
            this.targetFieldDeclarationBlueprint = targetFieldDeclarationBlueprint;
            return this;
        }

        public FieldAssignmentBlueprintBuilder<T> valuePlaceholder(ValuePlaceholder valuePlaceholder) {
            this.valuePlaceholder = valuePlaceholder;
            return this;
        }

        public FieldAssignmentBlueprintBuilder<? super T> from(T original) {
            return FieldAssignmentBlueprint.builder()
                                           .targetFieldDeclarationBlueprint(original.getTargetFieldDeclarationBlueprint())
                                           .valuePlaceholder(original.getValuePlaceholder());
        }

        protected void checkNotNulls() {
            Objects.requireNonNull(targetFieldDeclarationBlueprint, "Target field declaration must not be null");
            Objects.requireNonNull(valuePlaceholder, "Value placeholder must not be null");
        }

        @Override
        public T build() {
            checkNotNulls();
            return (T) new FieldAssignmentBlueprint(targetFieldDeclarationBlueprint, valuePlaceholder);
        }
    }
}

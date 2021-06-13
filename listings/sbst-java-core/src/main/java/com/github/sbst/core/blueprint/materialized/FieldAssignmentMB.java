package com.github.sbst.core.blueprint.materialized;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.FieldAssignmentBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationBlueprint;

public class FieldAssignmentMB extends FieldAssignmentBlueprint {
    protected FieldAssignmentMB(FieldDeclarationBlueprint targetFieldDeclarationBlueprint,
                                MaterializedValuePlaceholder materializedValuePlaceholder) {
        super(targetFieldDeclarationBlueprint, materializedValuePlaceholder);
    }

    public static FieldAssignmentMBB<FieldAssignmentMB> materializedBuilder() {
        return new FieldAssignmentMBB<>();
    }

    public static class FieldAssignmentMBB<T extends FieldAssignmentMB>
            implements GenericBuilder<T, FieldAssignmentMBB<T>> {


        private FieldDeclarationBlueprint targetFieldDeclarationBlueprint;
        private MaterializedValuePlaceholder materializedValuePlaceholder;

        public FieldAssignmentMBB<T> targetFieldDeclarationBlueprint(FieldDeclarationBlueprint targetFieldDeclarationBlueprint) {
            this.targetFieldDeclarationBlueprint = targetFieldDeclarationBlueprint;
            return this;
        }

        public FieldAssignmentMBB<T> materializedValuePlaceholder(MaterializedValuePlaceholder materializedValuePlaceholder) {
            this.materializedValuePlaceholder = materializedValuePlaceholder;
            return this;
        }

        public FieldAssignmentMBB<? super T> from(T original) {
            return FieldAssignmentMB.materializedBuilder()
                                    .targetFieldDeclarationBlueprint(original.getTargetFieldDeclarationBlueprint())
                                    .materializedValuePlaceholder((MaterializedValuePlaceholder) original.getValuePlaceholder());
        }

        public FieldAssignmentMBB<? super T> from(FieldAssignmentBlueprint original) {
            return FieldAssignmentMB.materializedBuilder()
                                    .targetFieldDeclarationBlueprint(original.getTargetFieldDeclarationBlueprint());
        }

        @Override
        public T build() {
            checkNotNulls();
            return (T) new FieldAssignmentMB(targetFieldDeclarationBlueprint, materializedValuePlaceholder);
        }

        protected void checkNotNulls() {
            Objects.requireNonNull(targetFieldDeclarationBlueprint, "Target field declaration must not be null");
            Objects.requireNonNull(materializedValuePlaceholder, "Value placeholder must not be null");
        }
    }
}

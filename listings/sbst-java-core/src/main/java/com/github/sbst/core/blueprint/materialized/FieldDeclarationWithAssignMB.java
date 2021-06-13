package com.github.sbst.core.blueprint.materialized;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.FieldAssignmentBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationWithAssignBlueprint;

public class FieldDeclarationWithAssignMB extends FieldDeclarationWithAssignBlueprint {
    protected FieldDeclarationWithAssignMB(FieldDeclarationBlueprint fieldDeclarationBlueprint,
                                           FieldAssignmentMB fieldAssignmentMB) {
        super(fieldDeclarationBlueprint, fieldAssignmentMB);
    }

    public static FieldDeclarationWithAssignMBB<FieldDeclarationWithAssignMB> materializedBuilder() {
        return new FieldDeclarationWithAssignMBB<>();
    }

    public static class FieldDeclarationWithAssignMBB<T extends FieldDeclarationWithAssignMB>
            implements GenericBuilder<T, FieldDeclarationWithAssignMBB<T>> {

        private FieldDeclarationBlueprint.FieldDeclarationBlueprintBuilder<? extends FieldDeclarationBlueprint> fieldDeclarationBlueprintBuilder;
        private FieldAssignmentMB.FieldAssignmentMBB<? extends FieldAssignmentBlueprint> fieldAssignmentMBB;
        private MaterializedValuePlaceholder materializedValuePlaceholder;

        public FieldDeclarationWithAssignMBB<T> fieldDeclarationBlueprint(FieldDeclarationBlueprint fieldDeclarationBlueprint) {
            this.fieldDeclarationBlueprintBuilder = FieldDeclarationBlueprint.builder().from(fieldDeclarationBlueprint);
            return this;
        }

        public FieldDeclarationWithAssignMBB<T> materializedValuePlaceholder(MaterializedValuePlaceholder materializedValuePlaceholder) {
            this.materializedValuePlaceholder = materializedValuePlaceholder;
            return this;
        }

        public FieldDeclarationWithAssignMBB<? super T> from(T original) {
            return FieldDeclarationWithAssignMB.materializedBuilder()
                                               .fieldDeclarationBlueprint(original.getFieldDeclarationBlueprint());
        }

        @Override
        public T build() {
            Objects.requireNonNull(fieldDeclarationBlueprintBuilder, "Field declaration blueprint must not be null");

            FieldDeclarationBlueprint fieldDeclarationBlueprint = fieldDeclarationBlueprintBuilder.build();
            fieldAssignmentMBB = FieldAssignmentMB.materializedBuilder()
                                                  .targetFieldDeclarationBlueprint(fieldDeclarationBlueprint)
                                                  .materializedValuePlaceholder(materializedValuePlaceholder);

            return (T) new FieldDeclarationWithAssignMB(fieldDeclarationBlueprint, fieldAssignmentMBB.build());
        }
    }
}

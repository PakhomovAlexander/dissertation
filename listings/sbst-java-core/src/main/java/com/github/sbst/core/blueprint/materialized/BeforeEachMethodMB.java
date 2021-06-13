package com.github.sbst.core.blueprint.materialized;

import java.util.List;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.BeforeEachMethodBlueprint;
import com.github.sbst.core.blueprint.FieldAssignmentBlueprint;

public class BeforeEachMethodMB extends BeforeEachMethodBlueprint {
    protected BeforeEachMethodMB(List<FieldAssignmentMB> fieldAssignmentBlueprints) {
        super(fieldAssignmentBlueprints);
    }

    public static BeforeEachMethodMBB<BeforeEachMethodMB> materializedBlueprintBuilder() {
        return new BeforeEachMethodMBB<>();
    }

    public static class BeforeEachMethodMBB<T extends BeforeEachMethodMB>
            implements GenericBuilder<T, BeforeEachMethodMBB<T>> {

        private List<FieldAssignmentMB> fieldAssignmentMBS;

        public BeforeEachMethodMBB<T> fieldAssignmentMaterializedBlueprints(List<? extends FieldAssignmentBlueprint> fieldAssignmentMaterializedBlueprints) {
            this.fieldAssignmentMBS = (List<FieldAssignmentMB>) fieldAssignmentMaterializedBlueprints;
            return this;
        }

        public BeforeEachMethodMBB<? super T> from(T original) {
            return BeforeEachMethodMB.materializedBlueprintBuilder()
                                     .fieldAssignmentMaterializedBlueprints(original.getFieldAssignmentBlueprints());
        }

        @Override
        public T build() {
            return (T) new BeforeEachMethodMB(fieldAssignmentMBS);
        }
    }
}

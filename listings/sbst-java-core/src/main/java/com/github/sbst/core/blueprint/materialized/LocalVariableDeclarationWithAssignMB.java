package com.github.sbst.core.blueprint.materialized;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.LocalVariableDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.ValuePlaceholder;
import com.github.sbst.core.info.TypeInfo;

public class LocalVariableDeclarationWithAssignMB extends LocalVariableDeclarationWithAssignBlueprint {
    protected LocalVariableDeclarationWithAssignMB(TypeInfo typeInfo,
                                                   String name,
                                                   MaterializedValuePlaceholder materializedValuePlaceholder) {
        super(typeInfo, name, materializedValuePlaceholder);
    }

    public MaterializedValuePlaceholder getValuePlaceholder() {
        return (MaterializedValuePlaceholder) super.getValuePlaceholder();
    }

    public static LocalVariableDeclarationWithAssignMBB<LocalVariableDeclarationWithAssignMB> materializedBuilder() {
        return new LocalVariableDeclarationWithAssignMBB<>();
    }

    
    public static class LocalVariableDeclarationWithAssignMBB<T extends LocalVariableDeclarationWithAssignMB>
            implements GenericBuilder<T, LocalVariableDeclarationWithAssignMBB<T>> {

        private TypeInfo typeInfo;
        private String name;
        private MaterializedValuePlaceholder materializedValuePlaceholder;

        public LocalVariableDeclarationWithAssignMBB<T> typeInfo(TypeInfo typeInfo) {
            this.typeInfo = typeInfo;
            return this;
        }


        public LocalVariableDeclarationWithAssignMBB<T> name(String name) {
            this.name = name;
            return this;
        }

        public LocalVariableDeclarationWithAssignMBB<T> materializedValuePlaceholder(MaterializedValuePlaceholder materializedValuePlaceholder) {
            this.materializedValuePlaceholder = materializedValuePlaceholder;
            return this;
        }


        public LocalVariableDeclarationWithAssignMBB<? super T> from(T original) {
            return LocalVariableDeclarationWithAssignMB.materializedBuilder()
                                                              .typeInfo(original.getTypeInfo())
                                                              .materializedValuePlaceholder((MaterializedValuePlaceholder) original.getValuePlaceholder())
                                                              .name(original.getName());
        }

        @Override
        public T build() {
            Objects.requireNonNull(typeInfo, "Type info must not be null");
            Objects.requireNonNull(name, "Field name must not be null");
            Objects.requireNonNull(materializedValuePlaceholder, "Value placeholder must not be null");

            return (T) new LocalVariableDeclarationWithAssignMB(typeInfo, name, materializedValuePlaceholder);
        }
    }
}

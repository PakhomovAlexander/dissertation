package com.github.sbst.core.blueprint.materialized;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.FieldDeclarationBlueprint;
import com.github.sbst.core.info.AccessModifier;
import com.github.sbst.core.info.TypeInfo;

public class FieldDeclarationMB extends FieldDeclarationBlueprint {
    protected FieldDeclarationMB(TypeInfo typeInfo, AccessModifier accessModifier, String name) {
        super(typeInfo, accessModifier, name);
    }

    public static FieldDeclarationMBB<FieldDeclarationMB> materializedBuilder() {
        return new FieldDeclarationMBB<>();
    }

    public static class FieldDeclarationMBB<T extends FieldDeclarationMB>
            implements GenericBuilder<T, FieldDeclarationMBB<T>> {

        protected TypeInfo typeInfo;
        protected AccessModifier accessModifier = AccessModifier.PACKAGE_PRIVATE;
        protected String name;

        public FieldDeclarationMBB<T> typeInfo(TypeInfo typeInfo) {
            this.typeInfo = typeInfo;
            return this;
        }

        public FieldDeclarationMBB<T> accessModifier(AccessModifier accessModifier) {
            this.accessModifier = accessModifier;
            return this;
        }

        public FieldDeclarationMBB<T> name(String name) {
            this.name = name;
            return this;
        }


        public FieldDeclarationMBB<? super T> from(T original) {
            return FieldDeclarationMB.materializedBuilder()
                                     .typeInfo(original.getTypeInfo())
                                     .accessModifier(original.getAccessModifier())
                                     .name(original.getName());
        }

        @Override
        public T build() {
            return (T) new FieldDeclarationMB(typeInfo, accessModifier, name);
        }

        protected void checkNulls() {
            Objects.requireNonNull(typeInfo, "Type info must not be null");
            Objects.requireNonNull(name, "Field name must not be null");
        }
    }

}

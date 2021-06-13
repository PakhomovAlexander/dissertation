package com.github.sbst.core.blueprint;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.info.AccessModifier;
import com.github.sbst.core.info.TypeInfo;

public class FieldDeclarationBlueprint {
    private final TypeInfo typeInfo;
    private final AccessModifier accessModifier;
    private final String name;

    protected FieldDeclarationBlueprint(TypeInfo typeInfo, AccessModifier accessModifier, String name) {
        this.typeInfo = typeInfo;
        this.accessModifier = accessModifier;
        this.name = name;
    }

    public static FieldDeclarationBlueprintBuilder<FieldDeclarationBlueprint> builder() {
        return new FieldDeclarationBlueprintBuilder<>();
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldDeclarationBlueprint)) return false;
        FieldDeclarationBlueprint that = (FieldDeclarationBlueprint) o;
        return Objects.equals(getTypeInfo(), that.getTypeInfo()) && getAccessModifier() == that.getAccessModifier() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeInfo(), getAccessModifier(), getName());
    }

    @Override
    public String toString() {
        return "FieldDeclarationBlueprint{" +
                "typeInfo=" + typeInfo +
                ", accessModifier=" + accessModifier +
                ", name='" + name + '\'' +
                '}';
    }

    public static class FieldDeclarationBlueprintBuilder<T extends FieldDeclarationBlueprint>
            implements GenericBuilder<T, FieldDeclarationBlueprintBuilder<T>> {

        private TypeInfo typeInfo;
        private AccessModifier accessModifier = AccessModifier.PACKAGE_PRIVATE;
        private String name;

        public FieldDeclarationBlueprintBuilder<T> typeInfo(TypeInfo typeInfo) {
            this.typeInfo = typeInfo;
            return this;
        }

        public FieldDeclarationBlueprintBuilder<T> accessModifier(AccessModifier accessModifier) {
            this.accessModifier = accessModifier;
            return this;
        }

        public FieldDeclarationBlueprintBuilder<T> name(String name) {
            this.name = name;
            return this;
        }


        public FieldDeclarationBlueprintBuilder<? super T> from(T original) {
            return FieldDeclarationBlueprint.builder()
                                            .typeInfo(original.getTypeInfo())
                                            .accessModifier(original.getAccessModifier())
                                            .name(original.getName());
        }

        protected void checkNulls() {
            Objects.requireNonNull(typeInfo, "Type info must not be null");
            Objects.requireNonNull(name, "Field name must not be null");
        }

        @Override
        public T build() {
            return (T) new FieldDeclarationBlueprint(typeInfo, accessModifier, name);
        }
    }
}

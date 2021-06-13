package com.github.sbst.core.info;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;

public class FieldInfo {
    private final AccessModifier accessModifier;
    private final TypeInfo typeInfo;
    private final String name;

    private FieldInfo(AccessModifier accessModifier, TypeInfo typeInfo, String name) {
        this.accessModifier = accessModifier;
        this.typeInfo = typeInfo;
        this.name = name;
    }

    public static FieldInfoBuilder<FieldInfo> builder() {
        return new FieldInfoBuilder<>();
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldInfo)) return false;
        FieldInfo fieldInfo = (FieldInfo) o;
        return getAccessModifier() == fieldInfo.getAccessModifier() && Objects.equals(getTypeInfo(), fieldInfo.getTypeInfo()) && Objects.equals(getName(), fieldInfo.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccessModifier(), getTypeInfo(), getName());
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "accessModifierInfo=" + accessModifier +
                ", typeInfo=" + typeInfo +
                ", name='" + name + '\'' +
                '}';
    }

    public static class FieldInfoBuilder<T extends FieldInfo>
            implements GenericBuilder<T, FieldInfoBuilder<T>> {

        private AccessModifier accessModifier = AccessModifier.PACKAGE_PRIVATE;
        private TypeInfo typeInfo;
        private String name;

        public FieldInfoBuilder<T> accessModifier(AccessModifier accessModifier) {
            this.accessModifier = accessModifier;
            return this;
        }

        public FieldInfoBuilder<T> typeInfo(TypeInfo typeInfo) {
            this.typeInfo = typeInfo;
            return this;
        }

        public FieldInfoBuilder<T> name(String name) {
            this.name = name;
            return this;
        }

        public FieldInfoBuilder<? super T> from(T original) {
            return FieldInfo.builder()
                            .accessModifier(original.getAccessModifier())
                            .typeInfo(original.getTypeInfo());
        }

        @Override
        public T build() {
            Objects.requireNonNull(typeInfo, "Field type cannot be null");
            Objects.requireNonNull(name, "Field name cannot be null");

            return (T) new FieldInfo(accessModifier, typeInfo, name);
        }
    }
}

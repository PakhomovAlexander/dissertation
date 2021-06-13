package com.github.sbst.core.info;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;

public class ParameterInfo {
    private final TypeInfo typeInfo;
    private final String name;

    private ParameterInfo(TypeInfo typeInfo, String name) {
        this.typeInfo = typeInfo;
        this.name = name;
    }

    public static ParameterInfoBuilder<ParameterInfo> builder() {
        return new ParameterInfoBuilder<>();
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
        if (!(o instanceof ParameterInfo)) return false;
        ParameterInfo that = (ParameterInfo) o;
        return Objects.equals(getTypeInfo(), that.getTypeInfo()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeInfo(), getName());
    }

    @Override
    public String toString() {
        return "ParameterInfo{" +
                "typeInfo=" + typeInfo +
                ", name='" + name + '\'' +
                '}';
    }

    public static class ParameterInfoBuilder<T extends ParameterInfo>
            implements GenericBuilder<T, ParameterInfoBuilder<T>> {

        private TypeInfo typeInfo;
        private String name;

        public ParameterInfoBuilder<T> typeInfo(TypeInfo typeInfo) {
            this.typeInfo = typeInfo;
            return this;
        }

        public ParameterInfoBuilder<T> name(String name) {
            this.name = name;
            return this;
        }

        public ParameterInfoBuilder<? super T> from(T original) {
            return ParameterInfo.builder()
                                    .typeInfo(original.getTypeInfo())
                                    .name(original.getName());
        }

        @Override
        public T build() {
            Objects.requireNonNull(typeInfo, "Parameter type cannot be null");
            Objects.requireNonNull(name, "Parameter name cannot be null");

            return (T) new ParameterInfo(typeInfo, name);
        }
    }
}

package com.github.sbst.core.blueprint;

import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.info.TypeInfo;

public class LocalVariableDeclarationWithAssignBlueprint {
    private final TypeInfo typeInfo;
    private final String name;
    private final ValuePlaceholder valuePlaceholder;

    protected LocalVariableDeclarationWithAssignBlueprint(TypeInfo typeInfo, String name, ValuePlaceholder valuePlaceholder) {
        this.typeInfo = typeInfo;
        this.name = name;
        this.valuePlaceholder = valuePlaceholder;
    }

    public static LocalVariableDeclarationWithAssignBlueprintBuilder<LocalVariableDeclarationWithAssignBlueprint> builder() {
        return new LocalVariableDeclarationWithAssignBlueprintBuilder<>();
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public String getName() {
        return name;
    }

    public ValuePlaceholder getValuePlaceholder() {
        return valuePlaceholder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalVariableDeclarationWithAssignBlueprint)) return false;
        LocalVariableDeclarationWithAssignBlueprint that = (LocalVariableDeclarationWithAssignBlueprint) o;
        return Objects.equals(getTypeInfo(), that.getTypeInfo()) && Objects.equals(getName(), that.getName()) && Objects.equals(getValuePlaceholder(), that.getValuePlaceholder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeInfo(), getName(), getValuePlaceholder());
    }

    @Override
    public String toString() {
        return "LocalVariableDeclarationWithAssignBlueprint{" +
                "typeInfo=" + typeInfo +
                ", name='" + name + '\'' +
                ", valuePlaceholder=" + valuePlaceholder +
                '}';
    }

    public static class LocalVariableDeclarationWithAssignBlueprintBuilder<T extends LocalVariableDeclarationWithAssignBlueprint>
            implements GenericBuilder<T, LocalVariableDeclarationWithAssignBlueprintBuilder<T>> {

        private TypeInfo typeInfo;
        private String name;
        private ValuePlaceholder valuePlaceholder;

        public LocalVariableDeclarationWithAssignBlueprintBuilder<T> typeInfo(TypeInfo typeInfo) {
            this.typeInfo = typeInfo;
            return this;
        }


        public LocalVariableDeclarationWithAssignBlueprintBuilder<T> name(String name) {
            this.name = name;
            return this;
        }

        public LocalVariableDeclarationWithAssignBlueprintBuilder<T> valuePlaceholder(ValuePlaceholder valuePlaceholder) {
            this.valuePlaceholder = valuePlaceholder;
            return this;
        }


        public LocalVariableDeclarationWithAssignBlueprintBuilder<? super T> from(T original) {
            return LocalVariableDeclarationWithAssignBlueprint.builder()
                                                              .typeInfo(original.getTypeInfo())
                                                              .valuePlaceholder(original.getValuePlaceholder())
                                                              .name(original.getName());
        }

        @Override
        public T build() {
            Objects.requireNonNull(typeInfo, "Type info must not be null");
            Objects.requireNonNull(name, "Field name must not be null");
            Objects.requireNonNull(valuePlaceholder, "Value placeholder must not be null");

            return (T) new LocalVariableDeclarationWithAssignBlueprint(typeInfo, name, valuePlaceholder);
        }
    }
}

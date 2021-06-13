package com.github.sbst.core.info;

import java.util.Objects;

public class PrimitiveTypeInfo implements TypeInfo {
    private final PrimitiveType type;

    private PrimitiveTypeInfo(PrimitiveType type) {this.type = type;}

    public static PrimitiveTypeInfo from(PrimitiveType type) {
        return new PrimitiveTypeInfo(type);
    }

    public PrimitiveType getPrimitiveType() {
        return type;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public String getFqn() {
        return type.getBoxedType().getCanonicalName();
    }

    public Class getBoxesClass() {
        return type.getBoxedType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrimitiveTypeInfo)) return false;
        PrimitiveTypeInfo that = (PrimitiveTypeInfo) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "PrimitiveTypeInfo{" +
                "type=" + type +
                '}';
    }
}

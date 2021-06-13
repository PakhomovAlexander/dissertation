package com.github.sbst.core.blueprint;

import java.util.Objects;

import com.github.sbst.core.info.PrimitiveType;
import com.github.sbst.core.info.PrimitiveTypeInfo;
import com.github.sbst.core.info.TypeInfo;

public class IntValuePlaceholder implements ValuePlaceholder {

    private final PrimitiveTypeInfo typeInfo;

    public IntValuePlaceholder() {
        this.typeInfo = PrimitiveTypeInfo.from(PrimitiveType.INT);
    }

    @Override
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValuePlaceholder)) return false;
        IntValuePlaceholder that = (IntValuePlaceholder) o;
        return Objects.equals(getTypeInfo(), that.getTypeInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeInfo());
    }

    @Override
    public String toString() {
        return "IntValuePlaceholder{" +
                "typeInfo=" + typeInfo +
                '}';
    }
}

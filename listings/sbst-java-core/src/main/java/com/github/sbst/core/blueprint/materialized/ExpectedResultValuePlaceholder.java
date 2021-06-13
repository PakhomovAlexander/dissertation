package com.github.sbst.core.blueprint.materialized;

import com.github.sbst.core.info.TypeInfo;

public class ExpectedResultValuePlaceholder implements MaterializedValuePlaceholder{
    private final TypeInfo typeInfo;
    private Object mutableValue;

    public ExpectedResultValuePlaceholder(TypeInfo typeInfo) {this.typeInfo = typeInfo;}

    @Override
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    @Override
    public String getValue() {
        return mutableValue != null ? mutableValue.toString() : null;
    }

    public void setValue(Object value) {
        this.mutableValue = value;
    }
}

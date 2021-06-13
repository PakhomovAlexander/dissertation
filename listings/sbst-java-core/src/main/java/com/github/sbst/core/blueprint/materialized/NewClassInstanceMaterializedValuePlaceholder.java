package com.github.sbst.core.blueprint.materialized;

import com.github.sbst.core.info.TypeInfo;

public class NewClassInstanceMaterializedValuePlaceholder implements MaterializedValuePlaceholder{
    private final TypeInfo typeInfo;

    public NewClassInstanceMaterializedValuePlaceholder(TypeInfo typeInfo) {this.typeInfo = typeInfo;}

    @Override
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    @Override
    public String getValue() {
        throw new RuntimeException("Not implemented"); //fixme
    }
}

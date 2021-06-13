package com.github.sbst.core.info;

public class VoidTypeInfo implements TypeInfo {
    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public String getFqn() {
        return Void.class.getCanonicalName();
    }

    @Override
    public String toString() {
        return "VoidTypeInfo";
    }
}

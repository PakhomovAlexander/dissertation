package com.github.sbst.core.info;

import java.util.Objects;

public class ClassTypeInfo implements TypeInfo {
    private final String fqn;

    public ClassTypeInfo(String  fqn) {
        this.fqn = fqn;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public String getFqn() {
        return fqn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTypeInfo)) return false;
        ClassTypeInfo that = (ClassTypeInfo) o;
        return Objects.equals(getFqn(), that.getFqn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFqn());
    }

    @Override
    public String toString() {
        return "ClassTypeInfo{" +
                "fqn=" + fqn +
                '}';
    }
}

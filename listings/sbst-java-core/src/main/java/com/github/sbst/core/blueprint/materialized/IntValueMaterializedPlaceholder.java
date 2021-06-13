package com.github.sbst.core.blueprint.materialized;

import java.util.Objects;

import com.github.sbst.core.blueprint.IntValuePlaceholder;

public class IntValueMaterializedPlaceholder extends IntValuePlaceholder implements MaterializedValuePlaceholder {
    private final int value;

    public IntValueMaterializedPlaceholder(int value) {
        super();
        this.value = value;
    }

    @Override
    public String getValue() {
        return "" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValueMaterializedPlaceholder)) return false;
        if (!super.equals(o)) return false;
        IntValueMaterializedPlaceholder that = (IntValueMaterializedPlaceholder) o;
        return getValue() == that.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }

    @Override
    public String toString() {
        return "IntValueMaterializedPlaceholder{" +
                "value=" + value +
                '}';
    }
}

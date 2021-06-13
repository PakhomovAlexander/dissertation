package com.github.sbst.core;

public class ValueGenerationConfig {
    private boolean onlyDefaultValues = false;


    public ValueGenerationConfig setOnlyDefaultValues(boolean onlyDefaultValues) {
        this.onlyDefaultValues = onlyDefaultValues;
        return this;
    }

    public boolean isOnlyDefaultValues() {
        return onlyDefaultValues;
    }
}

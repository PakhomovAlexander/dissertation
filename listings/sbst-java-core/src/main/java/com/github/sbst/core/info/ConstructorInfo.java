package com.github.sbst.core.info;

import java.util.List;
import java.util.Objects;

import com.github.sbst.core.GenericBuilder;

public class ConstructorInfo {
    private final AccessModifier accessModifier;
    private final List<ParameterInfo> inputParameters;

    private ConstructorInfo(AccessModifier accessModifier, List<ParameterInfo> inputParameters) {
        this.accessModifier = accessModifier;
        this.inputParameters = inputParameters;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public List<ParameterInfo> getInputParameters() {
        return inputParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstructorInfo)) return false;
        ConstructorInfo that = (ConstructorInfo) o;
        return getAccessModifier() == that.getAccessModifier() && Objects.equals(getInputParameters(), that.getInputParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccessModifier(), getInputParameters());
    }

    @Override
    public String toString() {
        return "ConstructorInfo{" +
                "accessModifierInfo=" + accessModifier +
                ", inputParameters=" + inputParameters +
                '}';
    }

    public static ConstructorInfoBuilder<ConstructorInfo> builder() {
        return new ConstructorInfoBuilder<>();
    }

    public static class ConstructorInfoBuilder<T extends ConstructorInfo>
            implements GenericBuilder<T, ConstructorInfoBuilder<T>> {

        private AccessModifier accessModifier = AccessModifier.PACKAGE_PRIVATE;
        private List<ParameterInfo> inputParameters = List.of();

        public ConstructorInfoBuilder<T> accessModifier(AccessModifier accessModifier) {
            this.accessModifier = accessModifier;
            return this;
        }

        public ConstructorInfoBuilder<T> inputParameters(List<ParameterInfo> inputParameters) {
            this.inputParameters = inputParameters;
            return this;
        }

        public ConstructorInfoBuilder<? super T> from(T original) {
            return ConstructorInfo.builder()
                           .accessModifier(original.getAccessModifier())
                           .inputParameters(List.copyOf(original.getInputParameters()));
        }

        @Override
        public T build() {
            return (T) new ConstructorInfo(accessModifier, inputParameters);
        }
    }
}

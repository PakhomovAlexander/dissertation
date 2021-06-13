package com.github.sbst.core.info;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.sbst.core.GenericBuilder;

public class MethodInfo {
    private final TypeInfo returnType;
    private final String methodName;
    private final List<ParameterInfo> inputParameters;
    private final AccessModifier accessModifier;

    private MethodInfo(AccessModifier accessModifier, TypeInfo returnType, String methodName, List<ParameterInfo> inputParameters) {
        this.accessModifier = accessModifier;
        this.returnType = returnType;
        this.methodName = methodName;
        this.inputParameters = inputParameters;
    }

    public static MethodInfoBuilder<MethodInfo> builder() {
        return new MethodInfoBuilder<>();
    }

    public TypeInfo getReturnType() {
        return returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<ParameterInfo> getInputParameters() {
        return inputParameters;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodInfo)) return false;
        MethodInfo that = (MethodInfo) o;
        return Objects.equals(getReturnType(), that.getReturnType()) && Objects.equals(getMethodName(), that.getMethodName()) && Objects.equals(getInputParameters(), that.getInputParameters()) && getAccessModifier() == that.getAccessModifier();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReturnType(), getMethodName(), getInputParameters(), getAccessModifier());
    }

    @Override
    public String toString() {
        return "MethodInfo{" +
                "returnType=" + returnType +
                ", methodName='" + methodName + '\'' +
                ", inputParameters=" + inputParameters +
                ", accessModifierInfo=" + accessModifier +
                '}';
    }

    public static class MethodInfoBuilder<T extends MethodInfo>
            implements GenericBuilder<T, MethodInfoBuilder<T>> {

        private AccessModifier accessModifier = AccessModifier.PACKAGE_PRIVATE;
        private TypeInfo returnType = new VoidTypeInfo();
        private String methodName;
        private List<ParameterInfo> inputParameters = List.of();

        public MethodInfoBuilder<T> accessModifier(AccessModifier accessModifier) {
            this.accessModifier = accessModifier;
            return this;
        }

        public MethodInfoBuilder<T> returnType(TypeInfo returnType) {
            this.returnType = returnType;
            return this;
        }

        public MethodInfoBuilder<T> methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public MethodInfoBuilder<T> inputParameters(List<ParameterInfo> inputParameters) {
            this.inputParameters = inputParameters;
            return this;
        }

        public MethodInfoBuilder<T> inputParameters(ParameterInfo... inputParameters) {
            this.inputParameters = List.of(inputParameters);
            return this;
        }

        public MethodInfoBuilder<T> inputParameters(ParameterInfo.ParameterInfoBuilder<ParameterInfo>... inputParameterBulders) {
            this.inputParameters = List.of(inputParameterBulders).stream()
                                       .map(ParameterInfo.ParameterInfoBuilder::build)
                                       .collect(Collectors.toList());
            return this;
        }

        public MethodInfoBuilder<? super T> from(T original) {
            return MethodInfo.builder()
                                 .accessModifier(original.getAccessModifier())
                                 .returnType(original.getReturnType())
                                 .methodName(original.getMethodName())
                                 .inputParameters(original.getInputParameters());
        }

        @Override
        public T build() {
            Objects.requireNonNull(returnType, "Method return type cannot be null");
            Objects.requireNonNull(methodName, "Method name cannot be null");

            return (T) new MethodInfo(accessModifier, returnType, methodName, inputParameters);
        }
    }
}

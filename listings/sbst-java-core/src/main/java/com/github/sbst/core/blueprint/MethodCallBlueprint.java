package com.github.sbst.core.blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.info.TypeInfo;
import com.github.sbst.core.info.VoidTypeInfo;

public class MethodCallBlueprint {
    private final TypeInfo typeInfo;
    private final String scope;
    private final String methodName;
    private final List<String> inputParameters;
    private final String resultVariableName;

    private MethodCallBlueprint(TypeInfo typeInfo, String scope, String methodName, List<String> inputParameters, String resultVariableName) {
        this.typeInfo = typeInfo;
        this.scope = scope;
        this.methodName = methodName;
        this.inputParameters = inputParameters;
        this.resultVariableName = resultVariableName;
    }

    public static MethodCallBlueprintBuilder<MethodCallBlueprint> builder() {
        return new MethodCallBlueprintBuilder<>();
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public String getScope() {
        return scope;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getInputParameters() {
        return inputParameters;
    }

    public String getResultVariableName() {
        return resultVariableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodCallBlueprint)) return false;
        MethodCallBlueprint that = (MethodCallBlueprint) o;
        return Objects.equals(getTypeInfo(), that.getTypeInfo()) && Objects.equals(getScope(), that.getScope()) && Objects.equals(getMethodName(), that.getMethodName()) && Objects.equals(getInputParameters(), that.getInputParameters()) && Objects.equals(getResultVariableName(), that.getResultVariableName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeInfo(), getScope(), getMethodName(), getInputParameters(), getResultVariableName());
    }

    @Override
    public String toString() {
        return "MethodCallBlueprint{" +
                "typeInfo=" + typeInfo +
                ", scope='" + scope + '\'' +
                ", methodName='" + methodName + '\'' +
                ", inputParameters=" + inputParameters +
                ", resultVariableName='" + resultVariableName + '\'' +
                '}';
    }

    public static class MethodCallBlueprintBuilder<T extends MethodCallBlueprint>
            implements GenericBuilder<T, MethodCallBlueprintBuilder<T>> {

        private TypeInfo typeInfo = new VoidTypeInfo();
        private String scope = "";
        private String methodName;
        private List<String> inputParameters = new ArrayList<>();
        private String resultVariableName;

        public MethodCallBlueprintBuilder<T> typeInfo(TypeInfo typeInfo) {
            this.typeInfo = typeInfo;
            return this;
        }

        public MethodCallBlueprintBuilder<T> scope(String scope) {
            this.scope = scope;
            return this;
        }

        public MethodCallBlueprintBuilder<T> methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public MethodCallBlueprintBuilder<T> inputParameters(List<String> inputParameters) {
            this.inputParameters = inputParameters;
            return this;
        }

        public MethodCallBlueprintBuilder<T> inputParameters(String... inputParameters) {
            this.inputParameters = List.of(inputParameters);
            return this;
        }

        public MethodCallBlueprintBuilder<T> resultVariableName(String resultVariableName) {
            this.resultVariableName = resultVariableName;
            return this;
        }


        public MethodCallBlueprintBuilder<? super T> from(T original) {
            return MethodCallBlueprint.builder()
                                      .typeInfo(original.getTypeInfo())
                                      .scope(original.getScope())
                                      .methodName(original.getMethodName())
                                      .inputParameters(List.copyOf(original.getInputParameters()))
                                      .resultVariableName(original.getResultVariableName());
        }

        @Override
        public T build() {
            Objects.requireNonNull(methodName, "Method name must not be null");

            return (T) new MethodCallBlueprint(typeInfo, scope, methodName, List.copyOf(inputParameters), resultVariableName);
        }
    }
}

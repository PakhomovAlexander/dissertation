package com.github.sbst.core.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.sbst.core.GenericBuilder;

public class ClassInfo {
    private final String pckg;
    private final String className;
    private final List<ConstructorInfo> constructors;
    private final List<MethodInfo> methods;
    private final List<FieldInfo> fields;

    private ClassInfo(String pckg, String className, List<ConstructorInfo> constructors, List<MethodInfo> methods, List<FieldInfo> fields) {
        this.pckg = pckg;
        this.className = className;
        this.constructors = constructors;
        this.methods = methods;
        this.fields = fields;
    }

    public static ClassInfoBuilder builder() {
        return new ClassInfoBuilder<>();
    }

    public String getPckg() {
        return pckg;
    }

    public String getClassName() {
        return className;
    }

    public List<ConstructorInfo> getAllConstructors() {
        return constructors;
    }

    public List<MethodInfo> getAllMethods() {
        return methods;
    }

    public List<FieldInfo> getAllFields() {
        return fields;
    }

    public List<MethodInfo> getAllPublicMethods() {
        return getAllMethods().stream()
                              .filter(m -> AccessModifier.PUBLIC.equals(m.getAccessModifier()))
                              .collect(Collectors.toList());
    }

    public String getFQN() {
        return pckg + '.' + className; //todo: get from ClassDeclaration?
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassInfo)) return false;
        ClassInfo classInfo = (ClassInfo) o;
        return Objects.equals(getPckg(), classInfo.getPckg()) && Objects.equals(getClassName(), classInfo.getClassName()) && Objects.equals(getAllConstructors(), classInfo.getAllConstructors()) && Objects.equals(getAllMethods(), classInfo.getAllMethods()) && Objects.equals(getAllFields(), classInfo.getAllFields());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPckg(), getClassName(), getAllConstructors(), getAllMethods(), getAllFields());
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "pckg='" + pckg + '\'' +
                ", className='" + className + '\'' +
                ", constructors=" + constructors +
                ", methods=" + methods +
                ", fields=" + fields +
                '}';
    }

    public static class ClassInfoBuilder<T extends ClassInfo>
            implements GenericBuilder<T, ClassInfoBuilder<T>> {

        private String pckg;
        private String className;
        private List<ConstructorInfo> constructors = new ArrayList<>();
        private List<MethodInfo> methods = new ArrayList<>();
        private List<FieldInfo> fields = new ArrayList<>();

        public ClassInfoBuilder<T> pckg(String pckg) {
            this.pckg = pckg;
            return this;
        }

        public ClassInfoBuilder<T> className(String className) {
            this.className = className;
            return this;
        }

        public ClassInfoBuilder<T> constructors(List<ConstructorInfo> constructors) {
            this.constructors = constructors;
            return this;
        }

        public ClassInfoBuilder<T> methods(List<MethodInfo> methods) {
            this.methods = methods;
            return this;
        }

        public ClassInfoBuilder<T> methods(MethodInfo... methods) {
            this.methods = List.of(methods);
            return this;
        }

        public ClassInfoBuilder<T> method(MethodInfo method) {
            this.methods.add(method);
            return this;
        }

        public ClassInfoBuilder<T> method(MethodInfo.MethodInfoBuilder methodInfoBuilder) {
            this.methods.add(methodInfoBuilder.build());
            return this;
        }

        public ClassInfoBuilder<T> fields(List<FieldInfo> fields) {
            this.fields = fields;
            return this;
        }

        public ClassInfoBuilder<T> from(T original) {
            return ClassInfo.builder()
                            .pckg(original.getPckg())
                            .className(original.getClassName())
                            .constructors(List.copyOf(original.getAllConstructors()))
                            .methods(List.copyOf(original.getAllMethods()))
                            .fields(List.copyOf(original.getAllFields()));
        }

        @Override
        public T build() {
            Objects.requireNonNull(className, "Class name cannot be null");

            return (T) new ClassInfo(pckg, className, List.copyOf(constructors), List.copyOf(methods), List.copyOf(fields));
        }
    }
}

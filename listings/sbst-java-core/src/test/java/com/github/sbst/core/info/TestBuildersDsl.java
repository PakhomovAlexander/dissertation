package com.github.sbst.core.info;

import com.github.sbst.core.GenericBuilder;
import com.github.sbst.core.blueprint.AssertBlueprint;
import com.github.sbst.core.blueprint.AssertBlueprint.AssertBlueprintBuilder;
import com.github.sbst.core.blueprint.FieldDeclarationBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationBlueprint.FieldDeclarationBlueprintBuilder;
import com.github.sbst.core.blueprint.FieldDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationWithAssignBlueprint.FieldDeclarationWithAssignBlueprintBuilder;
import com.github.sbst.core.blueprint.LocalVariableDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.LocalVariableDeclarationWithAssignBlueprint.LocalVariableDeclarationWithAssignBlueprintBuilder;
import com.github.sbst.core.blueprint.MethodCallBlueprint;
import com.github.sbst.core.blueprint.MethodCallBlueprint.MethodCallBlueprintBuilder;
import com.github.sbst.core.blueprint.TestMethodBlueprint;
import com.github.sbst.core.blueprint.TestMethodBlueprint.TestMethodBlueprintBuilder;
import com.github.sbst.core.blueprint.TestSuiteBlueprint;
import com.github.sbst.core.blueprint.TestSuiteBlueprint.TestSuiteBlueprintBuilder;
import com.github.sbst.core.blueprint.materialized.FieldDeclarationWithAssignMB;
import com.github.sbst.core.blueprint.materialized.LocalVariableDeclarationWithAssignMB;
import com.github.sbst.core.blueprint.materialized.TestMethodMB;
import com.github.sbst.core.blueprint.materialized.TestSuiteMB;
import com.github.sbst.core.info.ClassInfo.ClassInfoBuilder;
import com.github.sbst.core.info.MethodInfo.MethodInfoBuilder;
import com.github.sbst.core.info.ParameterInfo.ParameterInfoBuilder;

public class TestBuildersDsl {

    public static <T, B extends GenericBuilder<T, B>> T given(GenericBuilder<T, B> builder) {
        return builder.build();
    }

    public static ClassInfoBuilder<ClassInfo> classInfo() {
        return ClassInfo.builder();
    }

    public static ParameterInfoBuilder<ParameterInfo> parameterInfo() {
        return ParameterInfo.builder();
    }

    public static MethodInfoBuilder<MethodInfo> methodInfo() {
        return MethodInfo.builder();
    }

    public static TestSuiteBlueprintBuilder<TestSuiteBlueprint> testSuiteBlueprint() {
        return TestSuiteBlueprint.builder();
    }

    public static TestMethodBlueprintBuilder<TestMethodBlueprint> testMethodBlueprint() {
        return TestMethodBlueprint.builder();
    }

    public static LocalVariableDeclarationWithAssignBlueprintBuilder<LocalVariableDeclarationWithAssignBlueprint> localVariableDeclarationWithAssignBlueprint() {
        return LocalVariableDeclarationWithAssignBlueprint.builder();
    }

    public static MethodCallBlueprintBuilder<MethodCallBlueprint> methodCallBlueprint() {
        return MethodCallBlueprint.builder();
    }

    public static FieldDeclarationBlueprintBuilder<FieldDeclarationBlueprint> fieldDeclarationBlueprint() {
        return new FieldDeclarationBlueprintBuilder<>();
    }

    public static FieldDeclarationWithAssignBlueprintBuilder<FieldDeclarationWithAssignBlueprint> fieldDeclarationWithAssignBlueprint() {
        return FieldDeclarationWithAssignBlueprint.builder();
    }

    public static AssertBlueprintBuilder<AssertBlueprint> assertBlueprint() {
        return AssertBlueprint.builder();
    }

    public static TestSuiteMB.TestSuiteMBB<TestSuiteMB> testSuiteMB() {
        return TestSuiteMB.materializedBuilder();
    }


    public static FieldDeclarationWithAssignMB.FieldDeclarationWithAssignMBB<FieldDeclarationWithAssignMB> fieldDeclarationWithAssignMB() {
        return FieldDeclarationWithAssignMB.materializedBuilder();
    }

    public static TestMethodMB.TestMethodMBB<TestMethodMB> testMethodMB() {
        return TestMethodMB.materializedBuilder();
    }

    public static LocalVariableDeclarationWithAssignMB.LocalVariableDeclarationWithAssignMBB<LocalVariableDeclarationWithAssignMB> localVariableDeclarationWithAssignMB() {
        return LocalVariableDeclarationWithAssignMB.materializedBuilder();
    }
}

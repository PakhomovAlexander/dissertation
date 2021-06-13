package com.github.sbst.core;

import java.util.List;
import java.util.stream.Collectors;

import com.github.sbst.core.blueprint.AssertBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.IntValuePlaceholder;
import com.github.sbst.core.blueprint.LocalVariableDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.MethodCallBlueprint;
import com.github.sbst.core.blueprint.TestMethodBlueprint;
import com.github.sbst.core.blueprint.TestSuiteBlueprint;
import com.github.sbst.core.info.ClassInfo;
import com.github.sbst.core.info.ClassTypeInfo;
import com.github.sbst.core.info.MethodInfo;
import com.github.sbst.core.info.ParameterInfo;
import com.github.sbst.core.info.VoidTypeInfo;

public class TestSuiteBlueprintsGenerator {

    public List<TestSuiteBlueprint> generateAllPossibleBlueprintsFrom(ClassInfo classInfo) {
        // todo: generate ALL POSSIBLE, not one random suite blueprint
        return List.of(
                TestSuiteBlueprint.builder()
                                  .testSuiteName(classInfo.getClassName() + "Test")
                                  .fieldDeclarationWithAssignBlueprint(createFieldDeclarationWithAssignBlueprintFrom(classInfo))
                                  .testMethodBlueprint(createMethodBlueprintFrom(classInfo))
                                  .build()
        );
    }

    private TestMethodBlueprint createMethodBlueprintFrom(ClassInfo classInfo) {
        // todo: generate test suites for all public methods
        MethodInfo methodInfo = classInfo.getAllPublicMethods().get(0);

        var testMethodBlueprintBuilder = TestMethodBlueprint.builder()
                                                            .methodName("should" + capitalizeFistLetter(methodInfo.getMethodName()))
                                                            .givenParameters(methodInfo.getInputParameters().stream()
                                                                                       .map(this::parameterInfo2LocalVariableDeclarationWithAssignBlueprint)
                                                                                       .collect(Collectors.toList()))
                                                            .methodCallBlueprint(createMethodCallBlueprintFrom(classInfo, methodInfo));

        if (methodInfo.getReturnType() instanceof VoidTypeInfo)
            return testMethodBlueprintBuilder.build();

        return testMethodBlueprintBuilder
                .expectedResult(
                        LocalVariableDeclarationWithAssignBlueprint.builder()
                                                                   .name("expectedResult")
                                                                   .typeInfo(methodInfo.getReturnType())
                                                                   .valuePlaceholder(new IntValuePlaceholder())) //fixme
                .assertBlueprint(
                        AssertBlueprint.builder()
                                       .expectedValueVariableName("expectedResult")
                                       .actualValueVariableName(methodInfo.getMethodName() + "Result"))
                .build();
    }

    private MethodCallBlueprint createMethodCallBlueprintFrom(ClassInfo classInfo, MethodInfo methodInfo) {
        var methodCallBlueprintBuilder = MethodCallBlueprint.builder()
                                                            .scope(firstLetterToLowerCase(classInfo.getClassName()))
                                                            .methodName(methodInfo.getMethodName())
                                                            .inputParameters(methodInfo.getInputParameters().stream()
                                                                                       .map(ParameterInfo::getName)
                                                                                       .collect(Collectors.toList()));

        if (methodInfo.getReturnType() instanceof VoidTypeInfo)
            return methodCallBlueprintBuilder.build();

        return methodCallBlueprintBuilder
                .typeInfo(methodInfo.getReturnType())
                .resultVariableName(methodInfo.getMethodName() + "Result")
                .build();
    }

    private FieldDeclarationWithAssignBlueprint createFieldDeclarationWithAssignBlueprintFrom(ClassInfo classInfo) {
        return FieldDeclarationWithAssignBlueprint.builder()
                                                  .fieldDeclarationBlueprint(
                                                          FieldDeclarationBlueprint.builder()
                                                                                   .typeInfo(new ClassTypeInfo(classInfo.getFQN()))
                                                                                   .name(firstLetterToLowerCase(classInfo.getClassName())))
                                                  .build();
    }

    private LocalVariableDeclarationWithAssignBlueprint parameterInfo2LocalVariableDeclarationWithAssignBlueprint(ParameterInfo parameterInfo) {
        return LocalVariableDeclarationWithAssignBlueprint.builder()
                                                          .typeInfo(parameterInfo.getTypeInfo())
                                                          .name(parameterInfo.getName())
                                                          .valuePlaceholder(new IntValuePlaceholder()) //fixme
                                                          .build();
    }

    private String capitalizeFistLetter(String s) {
        if (s.isEmpty()) {
            return s;
        }

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private String firstLetterToLowerCase(String s) {
        if (s.isEmpty()) {
            return s;
        }

        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }
}

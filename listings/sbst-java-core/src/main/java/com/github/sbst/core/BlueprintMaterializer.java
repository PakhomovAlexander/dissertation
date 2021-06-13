package com.github.sbst.core;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.github.sbst.core.blueprint.BeforeEachMethodBlueprint;
import com.github.sbst.core.blueprint.FieldDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.LocalVariableDeclarationWithAssignBlueprint;
import com.github.sbst.core.blueprint.TestMethodBlueprint;
import com.github.sbst.core.blueprint.TestSuiteBlueprint;
import com.github.sbst.core.blueprint.materialized.BeforeEachMethodMB;
import com.github.sbst.core.blueprint.materialized.ExpectedResultValuePlaceholder;
import com.github.sbst.core.blueprint.materialized.FieldDeclarationWithAssignMB;
import com.github.sbst.core.blueprint.materialized.LocalVariableDeclarationWithAssignMB;
import com.github.sbst.core.blueprint.materialized.MaterializedValuePlaceholder;
import com.github.sbst.core.blueprint.materialized.NewClassInstanceMaterializedValuePlaceholder;
import com.github.sbst.core.blueprint.materialized.TestMethodMB;
import com.github.sbst.core.blueprint.materialized.TestSuiteMB;

public class BlueprintMaterializer {

    private final ValuePlaceholderMaterializer valuePlaceholderMaterializer;
    private final int numberOfTests;

    public BlueprintMaterializer(ValuePlaceholderMaterializer valuePlaceholderMaterializer, int numberOfTests) {this.valuePlaceholderMaterializer = valuePlaceholderMaterializer;
        this.numberOfTests = numberOfTests;
    }

    public TestSuiteMB materialize(TestSuiteBlueprint testSuiteBlueprint) {
        return TestSuiteMB.materializedBuilder()
                          .testSuiteName(testSuiteBlueprint.getTestSuiteName())
                          .fieldDeclarationWithAssignBlueprints(materialize1(testSuiteBlueprint.getFieldDeclarationWithAssignBlueprints()))
                          .beforeEachMethodBlueprint(materialize(testSuiteBlueprint.getBeforeEachMethodBlueprint()))
                          .testMethodBlueprints(materialize(testSuiteBlueprint.getTestMethodBlueprints()))
                          .build();
    }

    // fixme
    private List<FieldDeclarationWithAssignMB> materialize1(List<? extends FieldDeclarationWithAssignBlueprint> fieldDeclarationWithAssignBlueprints) {
        return fieldDeclarationWithAssignBlueprints.stream()
                                                   .map(this::materialize)
                                                   .collect(Collectors.toList());
    }

    private FieldDeclarationWithAssignMB materialize(FieldDeclarationWithAssignBlueprint fieldDeclarationWithAssignBlueprint) {
        return FieldDeclarationWithAssignMB.materializedBuilder()
                                           .fieldDeclarationBlueprint(fieldDeclarationWithAssignBlueprint.getFieldDeclarationBlueprint())
                                           .materializedValuePlaceholder(new NewClassInstanceMaterializedValuePlaceholder(
                                                   fieldDeclarationWithAssignBlueprint.getFieldDeclarationBlueprint().getTypeInfo()))
                                           .build();
    }

    private List<? extends TestMethodMB> materialize(List<? extends TestMethodBlueprint> testMethodBlueprints) {
        return testMethodBlueprints.stream()
                                   .flatMap(this::materialize)
                                   .collect(Collectors.toList());
    }

    private Stream<TestMethodMB> materialize(TestMethodBlueprint testMethodBlueprint) {
        final var testMethodMBTestMethodMBB = TestMethodMB.materializedBuilder()
                                                          .methodCallBlueprint(testMethodBlueprint.getMethodCallBlueprint())
                                                          .expectedResult(getExpectedResult(testMethodBlueprint.getExpectedResult()))
                                                          .assertBlueprint(testMethodBlueprint.getAssertBlueprint());
        // todo: get from config
        return IntStream.range(0, numberOfTests).boxed()
                        .map(i -> testMethodMBTestMethodMBB
                                .methodName(testMethodBlueprint.getMethodName() + i)
                                .givenParameters(
                                        testMethodBlueprint.getGivenParameters().stream()
                                                           .map(this::materialize)
                                                           .collect(Collectors.toList()))
                                .build()
                        );
    }

    private LocalVariableDeclarationWithAssignMB getExpectedResult(LocalVariableDeclarationWithAssignBlueprint localVariableDeclarationWithAssignBlueprint) {
        ExpectedResultValuePlaceholder materializedValuePlaceholder = new ExpectedResultValuePlaceholder(localVariableDeclarationWithAssignBlueprint.getTypeInfo());
        return LocalVariableDeclarationWithAssignMB.materializedBuilder()
                                                   .typeInfo(localVariableDeclarationWithAssignBlueprint.getTypeInfo())
                                                   .materializedValuePlaceholder(materializedValuePlaceholder)
                                                   .name(localVariableDeclarationWithAssignBlueprint.getName())
                                                   .build();
    }

    private LocalVariableDeclarationWithAssignMB materialize(LocalVariableDeclarationWithAssignBlueprint localVariableDeclarationWithAssignBlueprint) {
        MaterializedValuePlaceholder materializedValuePlaceholder = valuePlaceholderMaterializer.materialize(
                localVariableDeclarationWithAssignBlueprint.getValuePlaceholder()
        );

        return LocalVariableDeclarationWithAssignMB.materializedBuilder()
                                                   .typeInfo(localVariableDeclarationWithAssignBlueprint.getTypeInfo())
                                                   .materializedValuePlaceholder(materializedValuePlaceholder)
                                                   .name(localVariableDeclarationWithAssignBlueprint.getName())
                                                   .build();
    }

    private BeforeEachMethodMB materialize(BeforeEachMethodBlueprint beforeEachMethodBlueprint) {
        return BeforeEachMethodMB.materializedBlueprintBuilder().build(); // todo
    }
}

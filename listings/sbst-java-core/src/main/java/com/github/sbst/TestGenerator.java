package com.github.sbst;

import static com.github.javaparser.StaticJavaParser.parseExpression;

import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.sbst.core.blueprint.MethodCallBlueprint;
import com.github.sbst.core.blueprint.materialized.LocalVariableDeclarationWithAssignMB;
import com.github.sbst.core.blueprint.materialized.TestMethodMB;
import com.github.sbst.core.blueprint.materialized.TestSuiteMB;
import com.github.sbst.core.info.ClassInfo;
import com.github.sbst.core.info.PrimitiveTypeInfo;
import com.github.sbst.core.info.TypeInfo;
import com.github.sbst.core.info.VoidTypeInfo;

public class TestGenerator {

    private final SbstToolConfiguration configuration;

    public TestGenerator(SbstToolConfiguration configuration) {
        this.configuration = configuration;
    }

    public String generateTestFor(TestSuiteMB testSuiteMB, ClassInfo classInfo) { //todo: classInfo should not be here
        var cu = new CompilationUnit();
        cu.setPackageDeclaration(configuration.getGeneratedClassesPackage());

        addImports(cu);

        ClassOrInterfaceDeclaration testClassDeclaration = cu.addClass(testSuiteMB.getTestSuiteName());

        addCutFieldWithInitialization(testClassDeclaration, testSuiteMB);

        testSuiteMB.getTestMethodBlueprints()
                   .forEach(methodMB -> addTestMethod(testClassDeclaration, methodMB, classInfo));

        return cu.toString();
    }

    private void addImports(CompilationUnit cu) {
        cu.addImport("org.junit.jupiter.api.Test");
    }

    private void addTestMethod(ClassOrInterfaceDeclaration declaration, TestMethodMB testMethodMB, ClassInfo classInfo) {
        var body = new BlockStmt();

        MethodDeclaration methodDeclaration = declaration.addMethod(testMethodMB.getMethodName());

        addAnnotations(methodDeclaration);

        List<AssignExpr> variableDeclarations = createInputParametersInitializers(testMethodMB.getGivenParameters());
        variableDeclarations.forEach(body::addStatement);

        Expression methodCallExpression = createMethodCallExpression(testMethodMB.getMethodCallBlueprint(), variableDeclarations);
        body.addStatement(methodCallExpression);

        AssignExpr expectedValueExpression = createExpectedValueExpression(testMethodMB, classInfo, variableDeclarations);
        body.addStatement(expectedValueExpression);

        methodCallExpression.ifAssignExpr(
                (mcExpr) -> {
                    Expression assertExpression = createAssertExpression(mcExpr.asAssignExpr(), expectedValueExpression);
                    body.addStatement(assertExpression);
                }
        );

        methodDeclaration.setBody(body);
    }

    private Expression createAssertExpression(AssignExpr methodCallExpression, AssignExpr expectedValueExpression) {
        MethodCallExpr assertThat = new MethodCallExpr()
                .setScope(parseExpression("org.assertj.core.api.Assertions"))
                .setName("assertThat")
                .addArgument(getVariableNameFrom(methodCallExpression));

        return new MethodCallExpr()
                .setScope(assertThat)
                .setName("isEqualTo")
                .addArgument(getVariableNameFrom(expectedValueExpression));
    }

    private AssignExpr createExpectedValueExpression(TestMethodMB testMethodMB, ClassInfo cut, List<AssignExpr> variableDeclarations) {
        CompilationUnit cu = new CompilationUnit();
        cu.setPackageDeclaration("com.github.sbst.generated");
        ClassOrInterfaceDeclaration declaration = cu.addClass(cut.getClassName() + "SupplyWrapper");

        declaration.setImplementedTypes(NodeList.nodeList(StaticJavaParser.parseClassOrInterfaceType("java.util.function.Supplier<Integer>")));

        MethodDeclaration methodDeclaration = declaration.addMethod("get");
        methodDeclaration.setModifiers(Modifier.Keyword.PUBLIC);
        methodDeclaration.setType(Integer.class);

        var body = new BlockStmt();
        AssignExpr cutInitialisation = new AssignExpr()
                .setTarget(
                        new VariableDeclarationExpr()
                                .addVariable(
                                        new VariableDeclarator()
                                                .setName(firstLetterToLowerCase(cut.getClassName()))
                                                .setType(cut.getFQN())
                                )
                )
                .setValue(new ObjectCreationExpr().setType(cut.getFQN()));

        body.addStatement(cutInitialisation);
        variableDeclarations.forEach(body::addStatement);

        Expression methodCallExpression = createMethodCallExpression(testMethodMB.getMethodCallBlueprint(), variableDeclarations);
        body.addStatement(methodCallExpression);
        body.addStatement(new ReturnStmt().setExpression(parseExpression(testMethodMB.getMethodCallBlueprint().getMethodName() + "Result")));

        methodDeclaration.setBody(body);

        var codeToExecute = cu.toString();

        var codeExecutor = new CodeExecutor();
        Integer executionResult = codeExecutor.<Integer>executeCode("com.github.sbst.generated." + cut.getClassName() + "SupplyWrapper", codeToExecute);// todo fix Integer

        return new AssignExpr()
                .setOperator(AssignExpr.Operator.ASSIGN)
                .setTarget(
                        new VariableDeclarationExpr().addVariable(
                                new VariableDeclarator().setName(testMethodMB.getExpectedResult().getName())
                                                        .setType(typeInfo2type(testMethodMB.getMethodCallBlueprint().getTypeInfo()))))
                .setValue(new IntegerLiteralExpr().setValue(executionResult.toString())); //todo integer is hardcoded
    }

    private Expression createMethodCallExpression(MethodCallBlueprint callBlueprint, List<AssignExpr> variableDeclarations) {
        MethodCallExpr methodCallExpr = new MethodCallExpr().setName(callBlueprint.getMethodName())
                                                            .setScope(parseExpression(callBlueprint.getScope()));
        variableDeclarations.stream()
                            .map(this::getVariableNameFrom)
                            .forEach(methodCallExpr::addArgument);

        TypeInfo methodCallResultType = callBlueprint.getTypeInfo();
        if (methodCallResultType instanceof VoidTypeInfo) {
            return methodCallExpr;
        }

        return new AssignExpr()
                .setOperator(AssignExpr.Operator.ASSIGN)
                .setTarget(new VariableDeclarationExpr()
                                   .addVariable(new VariableDeclarator().setName(callBlueprint.getMethodName() + "Result").setType(typeInfo2type(methodCallResultType))))
                .setValue(methodCallExpr);
    }

    private String getVariableNameFrom(AssignExpr assignExpr) {
        return assignExpr.getTarget().asVariableDeclarationExpr().getVariables().get(0).getNameAsString();
    }

    private List<AssignExpr> createInputParametersInitializers(List<? extends LocalVariableDeclarationWithAssignMB> mut) {
        return mut.stream()
                  .map(this::parameterToAssignExpr)
                  .collect(Collectors.toList());
    }

    private void addAnnotations(MethodDeclaration methodDeclaration) {
        methodDeclaration.addAnnotation("Test");
    }

    private void addCutFieldWithInitialization(ClassOrInterfaceDeclaration declaration, TestSuiteMB testSuiteMB) {
        testSuiteMB.getFieldDeclarationWithAssignBlueprints()
                   .forEach(fdb -> declaration.addFieldWithInitializer(
                           fdb.getFieldDeclarationBlueprint().getTypeInfo().getFqn(),
                           fdb.getFieldDeclarationBlueprint().getName(),
                           new ObjectCreationExpr().setType(fdb.getFieldAssignmentBlueprint().getValuePlaceholder().getTypeInfo().getFqn())
                   ));
    }

    private void setupTestClassName(ClassOrInterfaceDeclaration declaration, ClassInfo cut) {
        declaration.setName(generateTestNameFrom(cut));
    }

    private String generateTestMethodName(String methodName) {
        return "should" + capitalizeFistLetter(methodName);
    }

    private String generateTestNameFrom(ClassInfo classInfo) {
        return classInfo.getClassName() + "Test";
    }

    private AssignExpr parameterToAssignExpr(LocalVariableDeclarationWithAssignMB p) {
        return new AssignExpr()
                .setOperator(AssignExpr.Operator.ASSIGN)
                .setTarget(new VariableDeclarationExpr()
                                   .addVariable(new VariableDeclarator()
                                                        .setName(p.getName())
                                                        .setType(typeInfo2type(p.getTypeInfo()))))
                .setValue(new IntegerLiteralExpr().setValue(p.getValuePlaceholder().getValue())); //todo integer is hardcoded
    }

    private Type typeInfo2type(TypeInfo typeInfo) {
        if (typeInfo.isPrimitive()) {
            PrimitiveTypeInfo pt = (PrimitiveTypeInfo) typeInfo; // fixme
            switch (pt.getPrimitiveType()) {
                case CHAR:
                    return PrimitiveType.charType();
                case BOOLEAN:
                    return PrimitiveType.byteType();
                case BYTE:
                    return PrimitiveType.booleanType();
                case SHORT:
                    return PrimitiveType.shortType();
                case INT:
                    return PrimitiveType.intType();
                case LONG:
                    return PrimitiveType.longType();
                case FLOAT:
                    return PrimitiveType.floatType();
                default:
                    return PrimitiveType.doubleType();
            }
        }

        return new ClassOrInterfaceType(typeInfo.getFqn()); // fixme
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

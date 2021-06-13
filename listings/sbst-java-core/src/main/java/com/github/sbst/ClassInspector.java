package com.github.sbst;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.sbst.core.info.AccessModifier;
import com.github.sbst.core.info.ClassInfo;
import com.github.sbst.core.info.MethodInfo;
import com.github.sbst.core.info.ParameterInfo;
import com.github.sbst.core.info.PrimitiveTypeInfo;
import com.github.sbst.core.info.TypeInfo;
import com.github.sbst.core.info.VoidTypeInfo;

public class ClassInspector {
    public ClassInfo inspect(Path classPath) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(classPath);
            return buildClassInfoFrom(cu);
        } catch (IOException e) {
            throw new RuntimeException(e); //todo: own exception
        }
    }

    private ClassInfo buildClassInfoFrom(CompilationUnit cu) {
        List<ClassOrInterfaceDeclaration> allClassDeclarations = findAllClassDeclarations(cu);
        if (allClassDeclarations.size() > 1) {
            throw new RuntimeException(
                    "Cannot inspect files with more than one class defined (will be supported later)"
            ); // todo
        }

        ClassOrInterfaceDeclaration declaration = allClassDeclarations.get(0);
        return ClassInfo.builder()
                        .pckg(cu.getPackageDeclaration().get().getNameAsString()) //fixme
                        .className(declaration.getNameAsString())
                        .methods(
                                cu.findAll(MethodDeclaration.class)
                                  .stream()
                                  .map(this::buildMethodInfo)
                                  .collect(Collectors.toList()))
                        .build();
    }

    private List<ClassOrInterfaceDeclaration> findAllClassDeclarations(CompilationUnit cu) {
        return cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                 .filter(cd -> !cd.isInterface())
                 .collect(Collectors.toList());
    }

    private MethodInfo buildMethodInfo(MethodDeclaration declaration) {
        return MethodInfo.builder()
                         .accessModifier(getAccessModifierFrom(declaration.getAccessSpecifier()))
                         .methodName(declaration.getNameAsString())
                         .inputParameters(getInputParametersInfoFrom(declaration))
                         .returnType(getReturnTypeInfoFrom(declaration))
                         .build();
    }

    private TypeInfo getReturnTypeInfoFrom(MethodDeclaration declaration) {
        return getTypeInfoFrom(declaration.getType());
    }

    private AccessModifier getAccessModifierFrom(AccessSpecifier specifier) {
        return AccessModifier.fromString(specifier.asString());
    }

    private List<ParameterInfo> getInputParametersInfoFrom(MethodDeclaration declaration) {
        return declaration.getParameters().stream()
                          .map(p -> ParameterInfo.builder()
                                                 .name(p.getNameAsString())
                                                 .typeInfo(getTypeInfoFrom(p.getType()))
                                                 .build()
                          ).collect(Collectors.toList());
    }

    private TypeInfo getTypeInfoFrom(Type type) {
        if (type.isPrimitiveType()) {
            PrimitiveType.Primitive primitive = type.asPrimitiveType().getType();
            return convertPrimitiveToPrimitiveTypeInfo(primitive);
        }

        // fixme
        return new VoidTypeInfo();
    }

    private TypeInfo convertPrimitiveToPrimitiveTypeInfo(PrimitiveType.Primitive primitive) {
        switch (primitive) {
            case BOOLEAN:
                return PrimitiveTypeInfo.from(com.github.sbst.core.info.PrimitiveType.BOOLEAN);
            case CHAR:
                return PrimitiveTypeInfo.from(com.github.sbst.core.info.PrimitiveType.CHAR);
            case BYTE:
                return PrimitiveTypeInfo.from(com.github.sbst.core.info.PrimitiveType.BYTE);
            case SHORT:
                return PrimitiveTypeInfo.from(com.github.sbst.core.info.PrimitiveType.SHORT);
            case INT:
                return PrimitiveTypeInfo.from(com.github.sbst.core.info.PrimitiveType.INT);
            case LONG:
                return PrimitiveTypeInfo.from(com.github.sbst.core.info.PrimitiveType.LONG);
            case FLOAT:
                return PrimitiveTypeInfo.from(com.github.sbst.core.info.PrimitiveType.FLOAT);
            default:
                return PrimitiveTypeInfo.from(com.github.sbst.core.info.PrimitiveType.DOUBLE);
        }
    }
}

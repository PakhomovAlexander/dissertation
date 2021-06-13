package com.github.sbst;

public class SbstToolConfiguration {
    private String generatedClassesPackage = "com.github.sbst.java.example";
    // fixme
    private Class cutClass;
    private String root;
    private String tmpDirPrefix = "/tmp";

    private SbstToolConfiguration() {}


    public static SbstToolConfiguration fromClassUnderTest(Class cutClass) {
        return new SbstToolConfiguration()
                .setCutClass(cutClass)
                .setGeneratedClassesPackage(cutClass.getPackageName()+ ".generated");
    }

    public String getGeneratedClassesPackage() {
        return generatedClassesPackage;
    }

    public Class getCutClassFile() {
        return cutClass;
    }

    private SbstToolConfiguration setGeneratedClassesPackage(String generatedClassesPackage) {
        this.generatedClassesPackage = generatedClassesPackage;
        return this;
    }

    private SbstToolConfiguration setCutClass(Class cutClass) {
        this.cutClass = cutClass;
        return this;
    }

    public String getRoot() {
        return root;
    }

    public SbstToolConfiguration setRoot(String root) {
        this.root = root;
        return this;
    }

    public String getTmpDirPrefix() {
        return tmpDirPrefix;
    }
}

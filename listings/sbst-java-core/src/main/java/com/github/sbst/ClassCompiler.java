package com.github.sbst;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ClassCompiler {
    private final SbstToolConfiguration configuration;

    public ClassCompiler(SbstToolConfiguration configuration) {this.configuration = configuration;}

    public Class compile(String className, String sourceCode) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // Save source in .java file.
        File root = new File(configuration.getTmpDirPrefix() + "/");
        File sourceFile = new File(root,  className.replace(".", "/") + ".java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), sourceCode.getBytes(StandardCharsets.UTF_8));

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

        // Load and instantiate compiled class.
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
        return Class.forName(className, true, classLoader);
    }
}

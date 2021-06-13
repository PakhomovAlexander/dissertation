package com.github.sbst;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClassWriter {

    private final Path basePath;

    public ClassWriter(Path basePath) {this.basePath = basePath;}

    public void writeToFile(String className, String sourceCode) throws IOException {
        // Save source in .java file.
        File root = new File(basePath.toUri().getPath());
        File sourceFile = new File(root, className + ".java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), sourceCode.getBytes(StandardCharsets.UTF_8));
    }
}

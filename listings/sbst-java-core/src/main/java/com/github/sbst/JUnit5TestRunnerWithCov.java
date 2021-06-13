package com.github.sbst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;

public class JUnit5TestRunnerWithCov extends JUnit5TestRunner {

    private final Class cutClass;
    private final SbstToolConfiguration configuration;

    public JUnit5TestRunnerWithCov(Class cutClass, SbstToolConfiguration configuration) {
        this.cutClass = cutClass;
        this.configuration = configuration;
    }

    @Override
    public TestRunResult runUnitTest(Class<?> clazz, String cutFql) {
        try {
            final String targetName = cutFql;

            final IRuntime runtime = new LoggerRuntime();

            final Instrumenter instr = new Instrumenter(runtime);
            InputStream original = getTargetClass(targetName);
            final byte[] instrumented = instr.instrument(original, targetName);
            original.close();

            final RuntimeData data = new RuntimeData();
            runtime.startup(data);

            final MemoryClassLoader memoryClassLoader = new MemoryClassLoader();
            memoryClassLoader.addDefinition(clazz.getName(), getTestSuite(clazz.getName()).readAllBytes());
            memoryClassLoader.addDefinition(targetName, instrumented);

            final Class<?> targetClass = memoryClassLoader.loadClass(targetName);
            final Class<?> testSuite = memoryClassLoader.loadClass(clazz.getName());

            super.runUnitTest(testSuite, cutFql);

            final ExecutionDataStore executionData = new ExecutionDataStore();
            final SessionInfoStore sessionInfos = new SessionInfoStore();
            data.collect(executionData, sessionInfos, false);
            runtime.shutdown();

            final CoverageBuilder coverageBuilder = new CoverageBuilder();
            final Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
            original = getTargetClass(targetName);
            analyzer.analyzeClass(original, targetName);
            original.close();

//            for (final IClassCoverage cc : coverageBuilder.getClasses()) {
//                System.out.printf("Coverage of class %s%n", cc.getName());
//
//                printCounter("instructions", cc.getInstructionCounter());
//                printCounter("branches", cc.getBranchCounter());
//                printCounter("lines", cc.getLineCounter());
//                printCounter("methods", cc.getMethodCounter());
//                printCounter("complexity", cc.getComplexityCounter());
//
//                for (int i = cc.getFirstLine(); i <= cc.getLastLine(); i++) {
//                    System.out.printf("Line %s: %s%n", Integer.valueOf(i),
//                                      getColor(cc.getLine(i).getStatus())
//                    );
//                }
//            }

            // fixme
            return new TestRunResult(listener.getSummary(), coverageBuilder.getClasses().stream().findFirst().get().getBranchCounter());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getTargetClass(final String name) throws FileNotFoundException {
        final String root = cutClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new FileInputStream(root + cutClass.getName().replace(".", "/") + ".class");
    }

    private InputStream getTestSuite(final String name) throws FileNotFoundException {
        final String[] split = name.split("\\.");
        final String path = configuration.getTmpDirPrefix() + "/" + configuration.getGeneratedClassesPackage().replace('.', '/');
        return new FileInputStream("/" + path + "/" + split[split.length - 1] + ".class");
    }

    private void printCounter(final String unit, final ICounter counter) {
        final Integer missed = Integer.valueOf(counter.getMissedCount());
        final Integer total = Integer.valueOf(counter.getTotalCount());
        System.out.printf("%s of %s %s missed%n", missed, total, unit);
    }

    private String getColor(final int status) {
        switch (status) {
            case ICounter.NOT_COVERED:
                return "red";
            case ICounter.PARTLY_COVERED:
                return "yellow";
            case ICounter.FULLY_COVERED:
                return "green";
        }
        return "";
    }

    /**
     * A class loader that loads classes from in-memory data.
     */
    public static class MemoryClassLoader extends ClassLoader {

        private final Map<String, byte[]> definitions = new HashMap<>();

        /**
         * Add a in-memory representation of a class.
         *
         * @param name  name of the class
         * @param bytes class definition
         */
        public void addDefinition(final String name, final byte[] bytes) {
            definitions.put(name, bytes);
        }

        @Override
        protected Class<?> loadClass(final String name, final boolean resolve)
                throws ClassNotFoundException {
            final byte[] bytes = definitions.get(name);
            if (bytes != null) {
                return defineClass(name, bytes, 0, bytes.length);
            }
            return super.loadClass(name, resolve);
        }

    }
}

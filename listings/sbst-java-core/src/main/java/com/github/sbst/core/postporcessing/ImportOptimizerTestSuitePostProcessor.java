package com.github.sbst.core.postporcessing;

import java.util.HashSet;
import java.util.Set;

import com.github.sbst.core.blueprint.materialized.TestSuiteMB;

public class ImportOptimizerTestSuitePostProcessor implements TestSuitePostProcessor {
    @Override
    public TestSuiteMB process(TestSuiteMB testSuiteMB) {
        final var testSuiteWithImportsBuilder = TestSuiteMB.materializedBuilder().from(testSuiteMB);
        Set<String> imports = new HashSet<>();
        // todo: define set of imports and reduce fqns to class names
        return testSuiteWithImportsBuilder.build();
    }
}

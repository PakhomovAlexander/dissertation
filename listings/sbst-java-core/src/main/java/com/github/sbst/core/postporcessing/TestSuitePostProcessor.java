package com.github.sbst.core.postporcessing;

import com.github.sbst.core.blueprint.materialized.TestSuiteMB;

public interface TestSuitePostProcessor {
    TestSuiteMB process(TestSuiteMB testSuiteMB);
}

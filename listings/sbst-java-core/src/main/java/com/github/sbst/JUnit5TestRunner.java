package com.github.sbst;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class JUnit5TestRunner {
    SummaryGeneratingListener listener = new SummaryGeneratingListener();

    public TestRunResult runUnitTest(Class<?> clazz, String cutFql) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                                                                          .selectors(selectClass(clazz))
                                                                          .build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        return new TestRunResult(listener.getSummary(), null);
    }
}

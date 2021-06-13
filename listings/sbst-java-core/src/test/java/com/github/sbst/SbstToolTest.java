package com.github.sbst;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.github.sbst.testclasses.SampleClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SbstToolTest {

    File generatedTestFile;

    SbstTool sbstTool;

    @BeforeEach
    void setUp() {
        sbstTool = SbstTool.fromConfiguration(
                SbstToolConfiguration.fromClassUnderTest(SampleClass.class).setRoot("src/test/java")
        );

        generatedTestFile = Path.of("src/test/java/com/github/sbst/testclasses/generated/SampleClassTest.java").toFile();
    }

    @AfterEach
    void tearDown() {
        generatedTestFile.deleteOnExit();
    }

    @Test
    void shouldGenerateTestsForGivenClass_ReturnsIntAcceptsTwoInts() throws IOException {
        // Given
        var pathToCut = Path.of("src/test/java/com/github/sbst/testclasses/SampleClass.java");
        // And
        assumeThat(generatedTestFile).doesNotExist();

        // When
        sbstTool.run(pathToCut);

        // Then

        // fixme
//        TestExecutionSummary report = sbstTool.getSbstReport();
//        assertThat(report.getTestsSucceededCount()).isEqualTo(1);
        // And
        assertThat(generatedTestFile).exists();
    }
}
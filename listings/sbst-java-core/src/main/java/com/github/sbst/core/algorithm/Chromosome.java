package com.github.sbst.core.algorithm;

import java.util.Objects;

import com.github.sbst.core.blueprint.materialized.TestSuiteMB;
import com.github.sbst.core.info.ClassInfo;

public class Chromosome {
    private final long id;
    private final TestSuiteMB testSuiteMB;
    private final ClassInfo classInfo;

    public Chromosome(long id, TestSuiteMB testSuiteMB, ClassInfo classInfo) {
        this.id = id;
        this.testSuiteMB = testSuiteMB;
        this.classInfo = classInfo;
    }

    public TestSuiteMB getTestSuiteMB() {
        return testSuiteMB;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chromosome)) return false;
        Chromosome that = (Chromosome) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "id=" + id +
                ", testSuiteMB=" + testSuiteMB +
                ", classInfo=" + classInfo +
                '}';
    }
}

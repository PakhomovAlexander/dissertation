package com.github.sbst.core.algorithm;

public class FitnessOutput implements Comparable<FitnessOutput>{
    private final Double fitScore;
    private final String sourceCode;

    public FitnessOutput(Double fitScore, String sourceCode) {
        this.fitScore = fitScore;
        this.sourceCode = sourceCode;
    }

    public Double getFitScore() {
        return fitScore;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    @Override
    public int compareTo(FitnessOutput o) {
        return this.fitScore.compareTo(o.fitScore);
    }
}

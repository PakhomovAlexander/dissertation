package com.github.sbst.testclasses;

public class SampleClass {
    public int calculate(int a, int b) {
        if (a > 10000) {
            // just to check coverage
            return -1;
        } else if ( b > 10000) {
            return 0;
        }
        return a + b;
    }
}

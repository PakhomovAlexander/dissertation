package com.github.sbst.java.example;

public class ClassUnderTest {

    public int calculateSumOrZero(int a, int b) {
        if (a > 100  && b > 100) {
            return 100;
        }
        if (a > 100 && b > 50) {
            return 50;
        }
        if (b < 0 && a < 0) {
            return -1;
        }
        if (b < 0) {
            return -10;
        }
        if (b > 110) {
            return 110;
        }
        return a + b;
    }
}


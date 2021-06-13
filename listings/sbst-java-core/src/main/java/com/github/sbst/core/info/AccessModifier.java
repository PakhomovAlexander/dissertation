package com.github.sbst.core.info;

import java.util.Locale;

public enum AccessModifier {
    PUBLIC("public"),
    PROTECTED("protected"),
    PACKAGE_PRIVATE(""),
    PRIVATE("private");

    private final String value;

    AccessModifier(String value) {this.value = value;}

    public static AccessModifier fromString(String value) {
        return AccessModifier.valueOf(value.toUpperCase());
    }
}

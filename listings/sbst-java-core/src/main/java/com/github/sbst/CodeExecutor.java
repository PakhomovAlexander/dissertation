package com.github.sbst;

import java.util.function.Supplier;

import org.joor.Reflect;

public class CodeExecutor {
    public <T> T executeCode(String name, String code) {
        Supplier<T> supplier = Reflect.compile(name, code).create().get();

        return supplier.get();
    }
}

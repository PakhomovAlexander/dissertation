package com.github.sbst.core.info;

public enum PrimitiveType {
    BYTE(Byte.class),
    SHORT(Short.class),
    INT(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    BOOLEAN(Boolean.class),
    CHAR(Character.class);

    private final Class boxedType;

    PrimitiveType(Class boxedType) {this.boxedType = boxedType;}

    public Class getBoxedType() {
        return boxedType;
    }
}

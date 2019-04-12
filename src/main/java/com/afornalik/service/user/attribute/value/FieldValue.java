package com.afornalik.service.user.attribute.value;

public class FieldValue<T> {

    private final T typeValue;

    public FieldValue(T typeValue) {
        this.typeValue = typeValue;
    }

    public T getTypeValue() {
        return typeValue;
    }

}

package com.afornalik.service.user.attribute.value;

public class FieldValue<T> {

    private final T t;

    public FieldValue(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

}

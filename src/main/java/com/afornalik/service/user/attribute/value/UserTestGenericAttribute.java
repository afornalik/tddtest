package com.afornalik.service.user.attribute.value;

public class UserTestGenericAttribute<T> {

    private final T t;

    public UserTestGenericAttribute(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

}

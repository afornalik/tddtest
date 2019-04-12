package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.FieldValue;


public abstract class EditField<T>{

    private EditField nextAttributeClass;


    public void setNextAttributeClass(EditField nextAttributeClass) {
        this.nextAttributeClass = nextAttributeClass;
    }

    public EditField getNextAttributeClass() {
        return nextAttributeClass;
    }

    public abstract User changeAttribute(EditField editField);

    public abstract FieldValue getCurrentFieldValue();
}

package ru.practicum.shareit.util.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.practicum.shareit.util.exeptions.ErrorMessage;
import ru.practicum.shareit.util.exeptions.ShareitException;
import ru.practicum.shareit.util.validation.annotation.CustomValidEmail;

public class CustomValidEmailValidator implements ConstraintValidator<CustomValidEmail, String> {

    private ErrorMessage error;

    @Override
    public void initialize(CustomValidEmail annotation) {
        this.error = annotation.error();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) throw new ShareitException(error);
        if (!value.contains("@") || !value.contains(".")) throw new ShareitException(error);
        if (value.length() < 6) throw new ShareitException(error);
        return true;
    }
}
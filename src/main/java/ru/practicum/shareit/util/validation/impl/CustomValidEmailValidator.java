package ru.practicum.shareit.util.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.practicum.shareit.util.exeptions.CustomException;
import ru.practicum.shareit.util.exeptions.ErrorMessage;
import ru.practicum.shareit.util.validation.annotation.Email;

public class CustomValidEmailValidator implements ConstraintValidator<Email, String> {

    private ErrorMessage error;

    @Override
    public void initialize(Email annotation) {
        this.error = annotation.error();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) throw new CustomException(error);
        if (!value.contains("@") || !value.contains(".")) throw new CustomException(error);
        if (value.length() < 6) throw new CustomException(error);
        return true;
    }
}
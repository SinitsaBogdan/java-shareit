package ru.practicum.shareit.util.validation.impl;

import ru.practicum.shareit.util.exeptions.CustomException;
import ru.practicum.shareit.util.exeptions.ErrorMessage;
import ru.practicum.shareit.util.validation.annotation.CustomValidNotBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomValidNotBlankValidator implements ConstraintValidator<CustomValidNotBlank, String> {

    private ErrorMessage error;

    @Override
    public void initialize(CustomValidNotBlank annotation) {
        this.error = annotation.error();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) throw new CustomException(error);
        return true;
    }
}
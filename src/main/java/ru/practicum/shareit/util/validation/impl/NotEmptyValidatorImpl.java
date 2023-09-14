package ru.practicum.shareit.util.validation.impl;

import ru.practicum.shareit.util.exeptions.ShareitException;
import ru.practicum.shareit.util.validation.FieldValidator;

import java.lang.reflect.Field;
import java.util.Collection;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.VALID_ERROR;

public class NotEmptyValidatorImpl implements FieldValidator {

    @Override
    public void validate(Object entity, Field field) {
        try {
            if (Collection.class.isAssignableFrom(field.getType())) {
                Collection<?> fieldValue = (Collection<?>) field.get(entity);
                if (fieldValue == null || fieldValue.isEmpty()) {
                    throw new ShareitException(VALID_ERROR);
                }
            } else if (String.class.isAssignableFrom(field.getType())) {
                String fieldValue = (String) field.get(entity);
                if (fieldValue == null || fieldValue.isEmpty()) {
                    throw new ShareitException(VALID_ERROR);
                }
            } else {
                if (field.get(entity) == null) {
                    throw new ShareitException(VALID_ERROR);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ShareitException(VALID_ERROR);
        }
    }
}
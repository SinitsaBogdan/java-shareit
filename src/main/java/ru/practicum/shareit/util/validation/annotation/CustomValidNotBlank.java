package ru.practicum.shareit.util.validation.annotation;

import ru.practicum.shareit.util.exeptions.ErrorMessage;
import ru.practicum.shareit.util.validation.impl.CustomValidNotBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CustomValidNotBlankValidator.class)
@Documented
public @interface CustomValidNotBlank {

    String message() default "{CapitalLetter.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};

    ErrorMessage error();
}
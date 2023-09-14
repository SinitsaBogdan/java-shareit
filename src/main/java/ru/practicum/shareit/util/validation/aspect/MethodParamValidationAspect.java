package ru.practicum.shareit.util.validation.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.util.validation.ParamValidator;

import java.util.stream.Stream;

@Aspect
@Component
public class MethodParamValidationAspect {

    private final ParamValidator validator;

    public MethodParamValidationAspect(ParamValidator validator) {
        this.validator = validator;
    }

    @Before(value = "@annotation(ru.practicum.shareit.util.validation.annotation.ValidParams)")
    public void validateParameters(JoinPoint joinPoint) {
        Stream.of(joinPoint.getArgs()).forEach(validator::validate);
    }
}